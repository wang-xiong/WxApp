package com.example.component_base.base.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.component_base.base.mvp.inter.IPresenter;
import com.example.component_base.base.mvp.inter.IView;

/**
 * Created by wangxiong on 2018/7/13.
 */

public abstract class BaseMvpFragment<V extends IView, P extends IPresenter> extends Fragment {
    protected V mView;
    protected P mPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        intView(view);
        initPresenter();
        initData();
        return view;
    }

    private void initPresenter() {
        mView = createIView();
        mPresenter = createPresenter();
        mPresenter.attachView(mView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected abstract int getLayoutId();

    protected abstract void intView(View rootView);

    protected abstract V createIView();

    protected abstract P createPresenter();

    protected abstract void initData();
}
