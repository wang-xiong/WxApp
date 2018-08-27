package com.example.component_base.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.component_base.Utils.StatusBarHelper;
import com.example.component_base.base.BaseActivity;
import com.example.component_base.base.mvp.inter.IPresenter;
import com.example.component_base.base.mvp.inter.IView;
import com.example.component_base.constants.BaseApplication;

/**
 * Created by wangxiong on 2018/7/13.
 * MVP模式顶级activity
 * 1.利用ActivityControl管理Activity(onCreate添加，onDestroy移除并关闭)
 * 2.创建Presenter，具体由子类实现
 * 3.创建View接口，具体由子类实现
 * 4.绑定Presenter和View;(onCreate绑定，onDestroy解绑)
 */

public abstract class BaseMvpActivity<V extends IView, P extends IPresenter<V>> extends BaseActivity{
    private V mView;
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    private void initPresenter() {
        mView = createIView();
        mPresenter = createPresenter();
        mPresenter.attachView(mView);
    }

    protected abstract V createIView();

    protected abstract P createPresenter();
}
