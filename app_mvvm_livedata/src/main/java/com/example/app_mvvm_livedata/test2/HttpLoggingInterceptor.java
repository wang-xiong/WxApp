package com.example.app_mvvm_livedata.test2;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

class HttpLoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }

    public void setLevel(int Level) {

    }

    public class Level {
        public static final int BODY = 1;
    }
}
