package com.example.homemodule.test;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Foreground implements Application.ActivityLifecycleCallbacks {

    private static Foreground mInstance;

    private List<AppForegroundListener> mListeners = new CopyOnWriteArrayList<>();

    public static Foreground getInstance(Application application) {
        if (mInstance == null) {
            mInstance = new Foreground();
            application.registerActivityLifecycleCallbacks(mInstance);
        }
        return mInstance;
    }

    public void destroy() {
        if (mInstance != null) {
            mInstance = null;
        }
        if (mListeners != null) {
            mListeners.clear();
        }
    }

    public void addListener(AppForegroundListener listener) {
        if (mListeners != null) {
            mListeners.add(listener);
        }
    }

    public void clearListener() {
        if (mListeners != null) {
            mListeners.clear();
        }
    }

    public void removeListener(AppForegroundListener listener) {
        if (mListeners != null) {
            mListeners.remove(listener);
        }
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        for (AppForegroundListener listener : mListeners) {
            listener.onBecameForeground();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        for (AppForegroundListener listener : mListeners) {
            listener.onBecameBackground();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public interface AppForegroundListener {

        void onBecameForeground();

        void onBecameBackground();
    }
}
