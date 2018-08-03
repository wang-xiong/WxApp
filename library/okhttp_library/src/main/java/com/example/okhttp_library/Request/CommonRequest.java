package com.example.okhttp_library.Request;

import com.example.okhttp_library.Request.RequestParams;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by wangxiong on 2018/7/19.
 */

public class CommonRequest {

    /**
     * 创建get请求
     * @param url 请求地址url
     * @param params 请求参数RequestParams
     * @return
     */
    public static Request createGetRequest(String url, RequestParams params) {
        StringBuilder stringBuilder = new StringBuilder(url);
        stringBuilder.append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.mUrlParams.entrySet()) {
                stringBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
        }
        String realUrl = stringBuilder.substring(0, stringBuilder.length());
        return  new Request.Builder().url(realUrl).get().build();
    }

    /**
     * 创建post请求
     * @param url 请求地址url
     * @param params 请求参数RequestParams
     * @return
     */
    public static Request createPostRequest(String url, RequestParams params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.mUrlParams.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody formBody = builder.build();
        return  new Request.Builder().url(url).post(formBody).build();
    }

}
