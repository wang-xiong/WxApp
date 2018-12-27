package com.example.lib_mvvm;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.example.lib_mvvm.utils.ViewModelUtil;


public abstract class AbsLifecycleFragment<T extends AbsViewModel> extends BaseFragment {

    protected T mViewModel;

    @Override
    protected void initView(Bundle savedInstanceState) {
        Class<T> viewModelClass = ViewModelUtil.getInstance(this, 0);
        mViewModel = ViewModelProviders.of(this).get(viewModelClass);
    }
}