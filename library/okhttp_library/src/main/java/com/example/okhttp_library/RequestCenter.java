package com.example.okhttp_library;

import com.example.okhttp_library.OkHttp.CommonOkHttpClient;
import com.example.okhttp_library.Request.CommonRequest;
import com.example.okhttp_library.Request.RequestParams;
import com.example.okhttp_library.Test.TestModel;

/**
 * Created by wangxiong on 2018/7/19.
 */

public class RequestCenter {

    private static void getRequest(String url, RequestParams params, DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url, params), new DisposeDataHandle(listener, clazz));
    }

    public static void requestTestData(DisposeDataListener listener) {
        getRequest(HttpConstant.URL_TEST, null, listener, TestModel.class);
    }

    /**
     * 加载福利图片数据
     * @param url
     * @param listener
     * @param clazz
     */
    public static void requestWelfareData(String url, DisposeDataListener listener, Class<?> clazz) {
        getRequest(url, null, listener, clazz);
    }

    /**
     * 下载福利图片
     * @param url
     * @param listener
     */
    public static void requestPhotoData(String url, DisposeDataListener listener) {
        getRequest(url, null, listener, null);
    }

    public static void requestArticleListData(String url, DisposeDataListener listener, Class<?> clazz) {
        getRequest(url, null, listener, clazz);
    }
}
