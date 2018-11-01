package com.example.app_jetpack.workmanager;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;

public class MyWorker extends Worker {


    @NonNull
    @Override
    public WorkerResult doWork() {
        Log.e("", "doWork");
        return WorkerResult.SUCCESS;
    }
}
