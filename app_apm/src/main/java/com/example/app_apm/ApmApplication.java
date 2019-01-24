package com.example.app_apm;

import android.app.Application;
import android.content.Context;

import com.example.app_apm.blockcanary.AppBlockContext;
import com.github.anrwatchdog.ANRWatchDog;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class ApmApplication extends Application {
    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        //1.初始化LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            this.mRefWatcher = RefWatcher.DISABLED;
            return;
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
        }
        this.mRefWatcher = LeakCanary.install(this);
        // Normal app init code...

        //2.初始化BlockCanary
        BlockCanary.install(this, new AppBlockContext()).start();

        //3.初始化ANRWatchDog
        new ANRWatchDog().start();
    }

    public static RefWatcher getRefWatcher(Context context) {
        ApmApplication apmApplication = (ApmApplication) context.getApplicationContext();
        return apmApplication.mRefWatcher;
    }
}
