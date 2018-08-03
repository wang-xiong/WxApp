package com.example.okhttp_library.Test;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wangxiong on 2018/7/18.
 */

public class HttpUtil {
    /**
     * 1.第一步创建OkHttpClient对象
     * 2.创建request对象
     * a.request包含两种请求：get和post
     * b.默认不写是get
     * c.post需要传入RequestBody对象，RequestBody需要MediaType标识类型
     * 4.创建call对象，执行
     *
     * 建议：
     * 1.OkHttpClient单例，因为每一个 OkHttpClient实例都拥有自己的连接池和线程池，避免资源浪费；
     * 2.每一个call只能执行一次，否则报错
     */


    private String mUrl = "";
    private OkHttpClient mOkHttpClient;

    private void init() {
        //mOkHttpClient = new OkHttpClient();
        mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new AllRequestInterceptor()).build();


    }

    private void doGetSync() {
        final Request request = new Request.Builder().url("").get().build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private void doGetAsync() {
        final Request request = new Request.Builder().url("").get().build();
        final Call call = mOkHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void doPostSync() {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String requestStr = "I am body";
        RequestBody requestBody = RequestBody.create(mediaType, requestStr);
        Request request = new Request.Builder().url("").post(requestBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
