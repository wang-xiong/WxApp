package com.example.app_eventbus;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.ConcurrentHashMap;


public class LiveDataBus3 {

    private static volatile LiveDataBus3 instance;
    private final ConcurrentHashMap<String, MutableLiveDataBus<Object>> mLiveBus;

    private LiveDataBus3() {
        mLiveBus = new ConcurrentHashMap<>();
    }

    public static LiveDataBus3 getInstance() {
        if (instance == null) {
            synchronized (LiveDataBus3.class) {
                if (instance == null) {
                    instance = new LiveDataBus3();
                }
            }
        }
        return instance;
    }

    public <T> void sendEventInMainThread(String eventKey, T value) {
        MutableLiveData<T> mutableLiveData = getLiveData(eventKey);
        mutableLiveData.setValue(value);
    }

    public <T> void sendEventInBackgroundThread(String eventKey, T value) {
        MutableLiveData<T> mutableLiveData = getLiveData(eventKey);
        mutableLiveData.postValue(value);
    }

    public <T> void observeEvent(String eventKey, LifecycleOwner owner, Observer<T> observer) {
        MutableLiveDataBus<T> mutableLiveData = getLiveData(eventKey);
        mutableLiveData.observe(owner, observer);
    }

    public <T> void observeForeverEvent(String eventKey, Observer<T> observer) {
        MutableLiveDataBus<T> mutableLiveData = getLiveData(eventKey);
        mutableLiveData.observeForever(observer);
    }

    private <T> MutableLiveDataBus<T> getLiveData(String eventKey) {
        if (!mLiveBus.containsKey(eventKey)) {
            mLiveBus.put(eventKey, new MutableLiveDataBus<>(true));
        } else {
            MutableLiveDataBus mutableLiveDataBus = mLiveBus.get(eventKey);
            if (mutableLiveDataBus != null) {
                mutableLiveDataBus.isFirstSubscribe = false;
            }
        }

        return (MutableLiveDataBus<T>) mLiveBus.get(eventKey);
    }

    private static class MutableLiveDataBus<T> extends MutableLiveData<T> {
        private boolean isFirstSubscribe;

        public MutableLiveDataBus(boolean isFirstSubscribe) {
            this.isFirstSubscribe = isFirstSubscribe;
        }

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
            super.observe(owner, new ObserverWrapper<>(observer, isFirstSubscribe));
        }

        @Override
        public void observeForever(@NonNull Observer<T> observer) {
            super.observeForever(new ObserverWrapper<>(observer, false));
        }
    }

    private static class ObserverWrapper<T> implements Observer<T> {
        private boolean isChanged;
        private Observer<T> observer;

        public ObserverWrapper(Observer<T> observer, boolean isFirstSubscribe) {
            this.observer = observer;
            this.isChanged = isFirstSubscribe;
        }

        @Override
        public void onChanged(@Nullable T t) {
            if (isChanged) {
                observer.onChanged(t);
            } else {
                isChanged = true;
            }
        }
    }
}
