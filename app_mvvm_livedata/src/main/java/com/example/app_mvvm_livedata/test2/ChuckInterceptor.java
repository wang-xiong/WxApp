package com.example.app_mvvm_livedata.test2;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

class ChuckInterceptor implements Interceptor {
    public ChuckInterceptor(Context context) {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
