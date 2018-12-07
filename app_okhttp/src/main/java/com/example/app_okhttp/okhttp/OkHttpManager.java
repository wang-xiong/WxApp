package com.example.app_okhttp.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class OkHttpManager {
    private static OkHttpManager instance;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private Gson mGson;

    private OkHttpManager() {
        initOkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    private void initOkHttpClient() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .readTimeout(1000, TimeUnit.MILLISECONDS)
                .build();
    }

    public static OkHttpManager getInstance() {
        if (instance == null) {
            instance = new OkHttpManager();
        }
        return instance;
    }

    public void request(SimpleHttpClient simpleHttpClient, final BaseCallBack callBack) {
        if (callBack == null) {
            throw new NullPointerException("callBack is null");
        }

        mOkHttpClient.newCall(simpleHttpClient.getRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendOnFailureMessage(callBack, call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    if (callBack.mType == null || callBack.mType == String.class) {
                        sendOnSuccessMessage(callBack, result);
                    } else {
                        sendOnSuccessMessage(callBack, mGson.fromJson(result, callBack.mType));
                    }
                    response.close();
                } else {
                    sendOnErrorMessage(callBack, response.code());
                }

            }
        });
    }

    private void sendOnFailureMessage(final BaseCallBack callBack, final Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onFailure(call, e);
            }
        });

    }

    private void sendOnSuccessMessage(final BaseCallBack callback, final Object object) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(object);
            }
        });

    }

    private void sendOnErrorMessage(final BaseCallBack callBack, final int code) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onError(code);
            }
        });

    }
}
