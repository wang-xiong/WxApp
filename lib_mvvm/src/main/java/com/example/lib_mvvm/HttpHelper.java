package com.example.lib_mvvm;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpHelper {

    private static volatile HttpHelper mHttpHelper;
    private Retrofit mRetrofit;

    private HttpHelper(Context context, String baseUrl) {
        Cache cache = new Cache(new File(context.getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cache(cache)
                .build();
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }

    public static HttpHelper getInstance() {
        return mHttpHelper;
    }

    public static void init(Application application, String baseUrl) {
        if (mHttpHelper == null) {
            synchronized (HttpHelper.class) {
                if (mHttpHelper == null) {
                    new HttpHelper(application, baseUrl);
                }
            }
        }
    }

    public <T> T create(Class<T> clz) {
        return mRetrofit.create(clz);
    }

}
