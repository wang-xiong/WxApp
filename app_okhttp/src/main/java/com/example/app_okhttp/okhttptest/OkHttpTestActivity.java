package com.example.app_okhttp.okhttptest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.app_okhttp.R;
import com.example.app_okhttp.okhttp.BaseCallBack;
import com.example.app_okhttp.okhttp.SimpleHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpTestActivity extends AppCompatActivity {
    private static final String TAG = "OkDownActivity";

    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        initHttpClient();
    }

    private void initHttpClient() {
        //okHttpClient = new OkHttpManager();

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .readTimeout(1000, TimeUnit.MILLISECONDS)
                .build();
    }

    private static final int REQUEST_CODE = 1000;

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请的权限被用户拒绝
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请到设置中打开权限", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                        , REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "权限申请失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void request() {
        String url = Config.Api.BAS_URL + "login";
        SimpleHttpClient.newBuilder()
                .url(url)
                .post()
                .addRequestParams("username", "wx")
                .addRequestParams("password", "123456")
                .build()
                .enqueue(new BaseCallBack<User>() {
                    @Override
                    public void onSuccess(User user) {

                    }
                });
    }

    private void requsetGet() {
        String url = Config.Api.BAS_URL + "login?username=wx&password=123456";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure" + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e(TAG, "onResponse" + result);
                }
            }
        });
    }

    private void requsetPostFrom() {
        String url = Config.Api.BAS_URL + "login";

        RequestBody requestBody = new FormBody.Builder()
                .add("username", "wx")
                .add("password", "123456")
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                Log.e(TAG, "onFailure" + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String msg = jsonObject.optString("message");
                        int success = jsonObject.optInt("success");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e(TAG, "onResponse" + result);
                }
            }
        });
    }

    private void requsetPostJson() {
        String url = Config.Api.BAS_URL + "login/json";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", "wx");
            jsonObject.put("password", "123456");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonParams = jsonObject.toString();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                Log.e(TAG, "onFailure" + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    //{message : "登录成功", success : 1}
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String msg = jsonObject.optString("message");
                        int success = jsonObject.optInt("success");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e(TAG, "onResponse" + result);
                }
            }
        });
    }

}
