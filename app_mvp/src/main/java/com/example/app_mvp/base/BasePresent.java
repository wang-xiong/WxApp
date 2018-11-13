package com.example.app_mvp.base;

public interface BasePresent<V extends BaseView> {

    void attachView(V view);

    void detachView();
}
