package com.example.app_eventbus;


import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus3 {

    /**
     * 有被压处理的Rxbus
     */

    private static volatile RxBus3 instance;

    private final FlowableProcessor<Object> mBus;


    private RxBus3() {
        this.mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBus3 getInstance() {
        if (instance == null) {
            synchronized (RxBus3.class) {
                if (instance == null) {
                    instance = Holder.rxBus;
                }
            }
        }
        return instance;
    }

    public void post(Object o) {
        mBus.onNext(o);
    }

    public <T> Flowable<T> toFlowable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Flowable<Object> toFlowable() {
        return mBus;
    }

    public boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }


    private static class Holder {
        private static final RxBus3 rxBus = new RxBus3();
    }
}
