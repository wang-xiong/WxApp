package com.example.app_test;

import android.app.Application;

import com.example.utils_library.FrescoUtil;

public class FrescoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FrescoUtil.initFresco(this);

    }
}
