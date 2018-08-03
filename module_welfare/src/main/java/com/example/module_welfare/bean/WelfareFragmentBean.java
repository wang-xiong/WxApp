package com.example.module_welfare.bean;

import android.support.v4.app.Fragment;


/**
 * Created by wangxiong on 2018/7/20.
 */
public class WelfareFragmentBean {
    private String title;
    private Fragment fragment;

    public WelfareFragmentBean(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
