package com.example.lib_mvvm;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    protected FragmentActivity activity;
    private View rootView;
    private boolean mIsFirstVisible = true;

    private boolean hasStarted = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(getLayoutResId(), null, false);
        initView(savedInstanceState);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (FragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.activity = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean isVis = isHidden() || getUserVisibleHint();
        if (isVis && mIsFirstVisible) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ///FragmentTabHost+Fragment的结构下，调用show或者hider时会告白
        if (!hidden) {
            //在最前端显示，相当于onResume()
            onVisible();
        } else {
            //不在前段显示了，相当于onPause()
            onInVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //fragment+ViewPager使用时会调用此方法
        if (isVisibleToUser) {
            hasStarted = true;
            //进入界面
            onVisible();
        } else {
            if (hasStarted) {
                //离开界面
                onInVisible();
            }
        }
    }

    private void onInVisible() {

    }

    private void onVisible() {
        if (mIsFirstVisible && isResumed()) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }

    protected void lazyLoad(){}

    protected abstract int getLayoutResId();

    protected abstract void initView(Bundle savedInstanceState);

    protected <T extends View> T getViewById(int id) {
        return rootView.findViewById(id);
    }

}
