package com.example.app_mvvm.viewmodel;

import android.support.v4.app.Fragment;

import java.util.List;

public class TabPagesVM {

    public List<Fragment> pageList;

    public TabPagesVM(List<Fragment> pageList) {
        this.pageList = pageList;
    }
}
