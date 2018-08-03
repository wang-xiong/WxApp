package com.example.okhttp_library.OkHttp;

import com.example.okhttp_library.Callback.CommonJsonCallback;
import com.example.okhttp_library.DisposeDataHandle;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by wangxiong on 2018/7/19.
 */

public class CommonOkHttpClient {

    /**
     * 1.创建OkHttpClient对象
     * 2.
     */

    private static final int TIME_OUT = 30;

    private static final OkHttpClient mOkHttpClient;

    static {
        mOkHttpClient = new OkHttpClient.Builder()
                //设置超时时间
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                //允许重定向
                .followRedirects(true)
                //添加https的支持
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                //.sslSocketFactory(HttpUtils)
                .build();
    }

    /**
     * 发送具体的Http以及Https请求
     *
     * @param request
     * @param callback
     * @return
     */
    public static Call sendRequest(Request request, CommonJsonCallback callback) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
        return call;
    }


    /**
     * get请求
     * @param request
     * @param handle
     * @return
     */
    public static Call get(Request request, DisposeDataHandle handle) {
        return sendRequest(request, new CommonJsonCallback(handle));
    }

    /**
     * post请求
     * @param request
     * @param handle
     * @return
     */
    public static Call post(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }
}
