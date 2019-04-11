package com.example.app_eventbus;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


public class LiveDataBus {

    private final Map<String, MutableLiveData<Object>> bus;

    private LiveDataBus() {
        this.bus = new HashMap<>();
    }

    private static class SingletonHolder {
        private static final LiveDataBus DATA_BUS = new LiveDataBus();
    }

    public static LiveDataBus getInstance() {
        return SingletonHolder.DATA_BUS;
    }

    public <T> void observeEvent(String eventKey, LifecycleOwner owner, Observer<T> observer) {
        MutableLiveData<T> mutableLiveData = getLiveData(eventKey);
        mutableLiveData.observe(owner, observer);
    }

    public <T> void observeForeverEvent(String eventKey, Observer<T> observer) {
        MutableLiveData<T> mutableLiveData = getLiveData(eventKey);
        mutableLiveData.observeForever(observer);
    }

    public <T> void sendEventInMainThread(String eventKey, T value) {
        MutableLiveData<T> mutableLiveData = getLiveData(eventKey);
        mutableLiveData.setValue(value);
    }

    public <T> void sendEventInBackgroundThread(String eventKey, T value) {
        MutableLiveData<T> mutableLiveData = getLiveData(eventKey);
        mutableLiveData.postValue(value);
    }

    private <T> MutableLiveData<T> getLiveData(String eventKey) {
        if (!bus.containsKey(eventKey)) {
            bus.put(eventKey, new MutableLiveData<>());
        }
        return (MutableLiveData<T>) bus.get(eventKey);
    }

}
