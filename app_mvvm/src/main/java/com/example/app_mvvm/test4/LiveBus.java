package com.example.app_mvvm.test4;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.concurrent.ConcurrentHashMap;


public class LiveBus {

    private static volatile LiveBus instance;
    private final ConcurrentHashMap<String, LiveBusData<Object>> mLiveBus;

    public LiveBus() {
        mLiveBus = new ConcurrentHashMap<>();
    }

    public static LiveBus getDefault() {
        if (instance == null) {
            synchronized (LiveBus.class) {
                if (instance == null) {
                    instance = new LiveBus();
                }
            }
        }
        return instance;
    }

    public <T> void postEvent(String eventKey, String tag, T t) {
        MutableLiveData<T> mutableLiveData = subscribe(eventKey, tag);
        mutableLiveData.postValue(t);
    }


    public <T> MutableLiveData<T> subscribe(String eventKey, String tag) {
        String key = mergeEventKey(eventKey, tag);
        if (!mLiveBus.containsKey(key)) {
            mLiveBus.put(key, new LiveBusData<>(true));
        } else {
            LiveBusData liveBusData = mLiveBus.get(key);
            if (liveBusData != null)
                liveBusData.isFirstSubscribe = false;
        }

        return (MutableLiveData<T>) mLiveBus.get(key);
    }


    private String mergeEventKey(String eventKey, String tag) {
        if (!TextUtils.isEmpty(tag)) {
            return eventKey + tag;
        } else {
            return eventKey;
        }
    }

    public void clear(String eventKey) {
        clear(eventKey, null);
    }

    private void clear(String eventKey, String tag) {
        if (mLiveBus.size() > 0) {
            String key = mergeEventKey(eventKey, tag);
            mLiveBus.remove(key);
        }
    }

    private static class LiveBusData<T> extends MutableLiveData<T> {
        private boolean isFirstSubscribe;

        public LiveBusData(boolean isFirstSubscribe) {
            this.isFirstSubscribe = isFirstSubscribe;
        }

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
            super.observe(owner, new ObserverWrapper<>(observer, isFirstSubscribe));
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
