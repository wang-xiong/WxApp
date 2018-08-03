package com.example.component_base.base.mvp;

import com.example.component_base.base.mvp.inter.IModel;
import com.example.component_base.base.mvp.inter.IPresenter;
import com.example.component_base.base.mvp.inter.IView;

/**
 * Created by wangxiong on 2018/7/13.
 * Mvp模式，Presenter基类，负责关联IView,IModel
 */

public abstract class BasePresenter<V extends IView, M extends IModel> implements IPresenter<V>{
    protected V mView;
    protected M mModule;

    @Override
    public void attachView(V view) {
        mView = view;
        mModule = createModel();
    }

    @Override
    public void detachView() {
        mView = null;
    }

    protected abstract M createModel();

}
