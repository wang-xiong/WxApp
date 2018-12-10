package com.example.app_okhttp.okhttptest;

public interface ProgressListener {
    void onProgress(int progress);
    void onDone(long totalSize);
}
