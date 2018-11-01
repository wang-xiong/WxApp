package com.example.app_jetpack.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;


public interface IPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(LifecycleObserver owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onDestroy(LifecycleObserver owner);
}
