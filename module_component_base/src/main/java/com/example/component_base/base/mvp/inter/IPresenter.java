package com.example.component_base.base.mvp.inter;

/**
 * Created by wangxiong on 2018/7/13.
 */

public interface IPresenter<V extends IView> {
    void attachView(V view);
    void detachView();
}
