package com.example.app_okhttp.okhttptest;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.example.app_okhttp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FileDownloadActivity extends AppCompatActivity {

    private OkHttpClient okHttpClient;
    private MyHandler handler;
    private ProgressBar progressBar;
    String url = "http://112.124.22.34:8080/source/css/music.apk";

    String fileName = "music.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        progressBar = findViewById(R.id.progress_bar);
        initHttpClient();
    }

    private void initHttpClient() {
        //okHttpClient = new OkHttpManager();

        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        return response.newBuilder().body(new ProgressResponseBody(response.body(), new Pro())).build();
                    }
                })
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .readTimeout(1000, TimeUnit.MILLISECONDS)
                .build();
        handler = new MyHandler(this);
    }

    class Pro implements ProgressListener {

        @Override
        public void onProgress(final int progress) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showProgress(progress);
                }
            });
        }

        @Override
        public void onDone(long totalSize) {

        }
    }

    private void downloadFile() {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                saveFile(response);
                response.close();
            }
        });

    }

    private static final int MSG = 10;

    static class MyHandler extends Handler {

        private FileDownloadActivity activity;

        public MyHandler(FileDownloadActivity activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG:
                    activity.showProgress(msg.arg1);
                    break;
            }
        }
    }

    private void showProgress(int progress) {
        progressBar.setProgress(progress);
    }


    private void saveFile(Response response) {
        InputStream in;
        FileOutputStream fos = null;
        in = response.body().byteStream();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path, fileName);
        try {
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            long total = response.body().contentLength();
            long sum = 0;
            while ((len = in.read(buffer)) != -1) {
                fos.write(buffer);

                //fos.write(buffer, 0, len);

                sum += len;
                int progress = (int) (sum * 1.0f / total * 100);

                Message message = handler.obtainMessage(MSG);
                message.arg1 = progress;
                message.sendToTarget();
            }
            //fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
