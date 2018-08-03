package com.example.okhttp_library.Test;

import android.util.Log;

import java.io.IOException;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangxiong on 2018/7/18.
 *
 * 1.全局拦截器，通过OkHttpClient.Builder#addInterceptor(Interceptor)
 * 2.另外一类是非网页请求的 interceptor ，这类拦截器只会在非网页请求中被调用，
 * 并且是在组装完请求之后，真正发起网络请求前被调用，所有的 interceptor
 * 被保存在 List<Interceptor> interceptors 集合中，按照添加顺序来逐个调用，
 * 通过 OkHttpClient.Builder#addNetworkInterceptor(Interceptor) 传入
 */

public class AllRequestInterceptor implements Interceptor {
    private static final String TAG = AllRequestInterceptor.class.getSimpleName();
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        long startTime = System.nanoTime();
        HttpUrl url = request.url();
        Headers headers = request.headers();
        Connection connection = chain.connection();

        Log.e(TAG, String.format("Sending request %s on %s%n%s", url, connection, headers));

        Response response = chain.proceed(request);
        long endTime = System.nanoTime();
        HttpUrl responseUrl = response.request().url();
        response.headers();
        double time = (endTime - startTime) / 1e6d;
        Log.d(TAG, String.format("Received response for %s in %.1fms%n%s", responseUrl, time, headers));

        return response;
    }
}
