package com.example.app_test;

import android.app.Application;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;

import okhttp3.OkHttpClient;

public class FrescoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        FrescoUtil.initFresco(this);

    }
}
