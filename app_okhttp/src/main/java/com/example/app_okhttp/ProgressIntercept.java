package com.example.app_okhttp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ProgressIntercept implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
