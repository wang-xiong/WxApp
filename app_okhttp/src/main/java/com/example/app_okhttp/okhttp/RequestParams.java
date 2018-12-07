package com.example.app_okhttp.okhttp;

public class RequestParams {

    private String key;
    private String value;

    public RequestParams(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
