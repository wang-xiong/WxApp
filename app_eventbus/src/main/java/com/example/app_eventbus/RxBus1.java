package com.example.app_eventbus;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;


import io.reactivex.Observable;

public class RxBus1 {

    /**
     * 订阅者处理事件出现异常后，订阅者无法再收到事件，这是RxJava本身的设计原则；
     * 所以为了满足事件总线出现异常也不会终止订阅关系，使用JakeWharton大神的RxRelay实现Rxbus。
     */

    private final Relay<Object> mBus;

    private RxBus1() {
        this.mBus = PublishRelay.create().toSerialized();
    }

    public static RxBus1 getInstance() {
        return Holder.rxBus;
    }

    public void post(Object o) {
        mBus.accept(o);
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
        private static final RxBus1 rxBus = new RxBus1();
    }
}
