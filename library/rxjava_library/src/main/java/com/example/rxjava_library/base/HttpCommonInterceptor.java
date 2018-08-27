package com.example.rxjava_library.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangxiong on 2018/8/13.
 */

public class HttpCommonInterceptor implements Interceptor {
    private Map<String, String> mHeaderParamsMap = new HashMap<>();

    private HttpCommonInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oleRequest = chain.request();
        Request.Builder builder = oleRequest.newBuilder();
        builder.method(oleRequest.method(), oleRequest.body());
        if (mHeaderParamsMap.size() > 0) {
            for (Map.Entry<String, String> entry : mHeaderParamsMap.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }

    public static class Builder {
        HttpCommonInterceptor interceptor;

        public Builder() {
            this.interceptor = new HttpCommonInterceptor();
        }

        public Builder addHeadParams(String key, String value) {
            interceptor.mHeaderParamsMap.put(key, value);
            return this;
        }

        public Builder addHeadParams(String key, int value) {
            return addHeadParams(key, String.valueOf(value));
        }

        public Builder addHeadParams(String key, float value) {
            return addHeadParams(key, String.valueOf(value));
        }

        public Builder addHeadParams(String key, long value) {
            return addHeadParams(key, String.valueOf(value));
        }

        public Builder addHeadParams(String key, double value) {
            return addHeadParams(key, String.valueOf(value));
        }

        public HttpCommonInterceptor build() {
            return interceptor;
        }
    }
}