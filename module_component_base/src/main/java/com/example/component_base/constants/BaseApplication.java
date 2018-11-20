package com.example.component_base.constants;

import android.app.Application;
import android.content.Context;

import com.example.component_base.ARouter.ARouterConfig;
import com.example.component_base.BuildConfig;
import com.example.utils_library.WLog;

/**
 * Created by wangxiong on 2018/7/13.
 * 1.创建全局Application实例mBaseApplication，并提供获取方法
 * 2.创建mActivityControl，管理Activity
 */

public class BaseApplication extends Application {

    public static boolean IS_DEBUG = BuildConfig.DEBUG;

    private static BaseApplication mBaseApplication;

    private ActivityControl mActivityControl;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mBaseApplication = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        WLog.i("onCreate");
        mActivityControl = new ActivityControl();
        ARouterConfig.init(this,IS_DEBUG);
    }

    /**
     * 程序终止时执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        WLog.i("onTerminate");
        exitApp();
    }

    private void exitApp() {
        mActivityControl.finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 低内存时执行
     */
    @Override
    public void onLowMemory() {
        WLog.i("onCreate");
        super.onLowMemory();
    }

    /**
     * 程序清理内存时执行
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        WLog.i("onTrimMemory");
        super.onTrimMemory(level);
    }

    public static BaseApplication getBaseApplication() {
        return mBaseApplication;
    }

    public ActivityControl  getActivityControl() {
        return mActivityControl;
    }
}
