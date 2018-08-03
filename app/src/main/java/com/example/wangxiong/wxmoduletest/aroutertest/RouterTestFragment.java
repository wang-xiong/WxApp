package com.example.wangxiong.wxmoduletest.aroutertest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.wangxiong.wxmoduletest.R;

/**
 * Created by wangxiong on 2018/7/11.
 */

@Route(path = "/wxmoduletest/RouterTestFragment")
public class RouterTestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.activity_main, container, false);
        return view;
    }
}
