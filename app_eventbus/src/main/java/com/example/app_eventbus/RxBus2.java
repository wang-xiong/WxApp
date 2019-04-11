package com.example.app_eventbus;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus2 {

    /**
     * 没有被压处理的Rxbus
     */

    private static volatile RxBus2 instance;

    private final Subject<Object> mBus;


    private RxBus2() {
        this.mBus = PublishSubject.create().toSerialized();
    }

    public static RxBus2 getInstance() {
        if (instance == null) {
            synchronized (RxBus2.class) {
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

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Observable<Object> toObservable() {
        return mBus;
    }

    public boolean hasObservables() {
        return mBus.hasObservers();
    }


    private static class Holder {
        private static final RxBus2 rxBus = new RxBus2();
    }
}
