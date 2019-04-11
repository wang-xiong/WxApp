package com.example.app_eventbus;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class LiveDataBus2 {

    private final Map<String, MutableLiveDataBus<Object>> bus;

    private LiveDataBus2() {
        this.bus = new HashMap<>();
    }

    private static class SingletonHolder {
        private static final LiveDataBus2 DATA_BUS = new LiveDataBus2();
    }

    public static LiveDataBus2 getInstance() {
        return SingletonHolder.DATA_BUS;
    }

    public <T> void observeEvent(String eventKey, LifecycleOwner owner, Observer<T> observer) {
        MutableLiveDataBus<T> mutableLiveData = getLiveData(eventKey);
        mutableLiveData.observe(owner, observer);
    }

    public <T> void sendEventInMainThread(String eventKey, T value) {
        MutableLiveData<T> mutableLiveData = getLiveData(eventKey);
        mutableLiveData.setValue(value);
    }

    public <T> void sendEventInBackgroundThread(String eventKey, T value) {
        MutableLiveDataBus<T> mutableLiveData = getLiveData(eventKey);
        mutableLiveData.postValue(value);
    }

    public <T> void observeForeverEvent(String eventKey, Observer<T> observer) {
        MutableLiveDataBus<T> mutableLiveData = getLiveData(eventKey);
        mutableLiveData.observeForever(observer);
    }

    private <T> MutableLiveDataBus<T> getLiveData(String key) {
        if (!bus.containsKey(key)) {
            bus.put(key, new MutableLiveDataBus<>());
        }
        return (MutableLiveDataBus<T>) bus.get(key);
    }

    private static class BusObserverWrapper<T> implements Observer<T> {
        private Observer<T> observer;

        private BusObserverWrapper(Observer<T> observer) {
            this.observer = observer;
        }

        @Override
        public void onChanged(@Nullable T t) {
            if (observer != null) {
                //判断是observeForever注册的方式，直接返回。
                if (isCallOnObserveForever()) {
                    return;
                }
                observer.onChanged(t);
            }
        }

        private boolean isCallOnObserveForever() {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            if (stackTrace.length > 0) {
                for (StackTraceElement element : stackTrace) {
                    if ("android.arch.lifecycle.LiveData".equals(element.getClassName()) &&
                            "observeForever".equals(element.getMethodName())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private static class MutableLiveDataBus<T> extends MutableLiveData<T> {
        private Map<Observer<T>, BusObserverWrapper<T>> observableMap = new HashMap<>();

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
            super.observe(owner, observer);
            try {
                //反射的方式改变ObserverWrapper的version值和LiveData值相同。
                hook(observer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void observeForever(@NonNull Observer<T> observer) {
            if (!observableMap.containsKey(observer)) {
                observableMap.put(observer, new BusObserverWrapper<>(observer));
            }
            super.observeForever(Objects.requireNonNull(observableMap.get(observer)));
        }

        @Override
        public void removeObserver(@NonNull Observer<T> observer) {
            if (observableMap.containsKey(observer)) {
                super.removeObserver(Objects.requireNonNull(observableMap.remove(observer)));
            } else {
                super.removeObserver(observer);
            }

        }

        private void hook(Observer<T> observer) throws Exception {
            Class<LiveData> liveDataClass = LiveData.class;

            Field fieldObservers = liveDataClass.getDeclaredField("mObservers");
            fieldObservers.setAccessible(true);
            Object objectObservers = fieldObservers.get(this);

            Class<?> classObservers = objectObservers.getClass();
            Method methodGet = classObservers.getDeclaredMethod("get", Object.class);
            methodGet.setAccessible(true);

            Object objectWrapperEntry = methodGet.invoke(objectObservers, observer);
            Object objectWrapper = null;
            if (objectWrapperEntry instanceof Map.Entry) {
                objectWrapper = ((Map.Entry) objectWrapperEntry).getValue();
            }
            if (objectWrapper == null) {
                throw new NullPointerException("Wrapper cant not be null");
            }
            Field fieldVersion = liveDataClass.getDeclaredField("mVersion");
            fieldVersion.setAccessible(true);
            Object objectVersion = fieldVersion.get(this);

            Class<?> classObserverWrapper = objectWrapper.getClass().getSuperclass();
            Field fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion");
            fieldLastVersion.setAccessible(true);
            fieldLastVersion.set(objectWrapper, objectVersion);
        }
    }

}
