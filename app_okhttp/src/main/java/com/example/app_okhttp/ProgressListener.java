package com.example.app_okhttp;

public interface ProgressListener {
    void onProgress(int progress);
    void onDone(long totalSize);
}
