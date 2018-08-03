package com.example.okhttp_library.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangxiong on 2018/7/19.
 */

public class RequestParams {

    /**
     * 封装Request参数到mUrlParams一个Map集合
     */
    public ConcurrentHashMap<String, String> mUrlParams = new ConcurrentHashMap<>();

    public RequestParams() {
        this(null);
    }

    public RequestParams(final String key, final String value) {
        if (key != null && value != null) {
            mUrlParams.put(key, value);
        }

    }

    public RequestParams(Map<String, String> source) {
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    mUrlParams.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }
}
