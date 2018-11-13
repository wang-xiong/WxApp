package com.example.app_mvp.present.main;

import android.text.TextUtils;

import com.example.app_mvp.view.main.MainBaseView;

public class MainBasePresentImpl implements MainBasePresent {

    private MainBaseView mainBaseView;

    @Override
    public void attachView(MainBaseView view) {
        this.mainBaseView = view;
    }

    @Override
    public void detachView() {
        this.mainBaseView = null;
    }

    @Override
    public void login(String userName, String password) {
        if (TextUtils.isEmpty(userName)) {
            mainBaseView.showEmptyTip("用户名不能为空");
        } else if (TextUtils.isEmpty(password)) {
            mainBaseView.showEmptyTip("密码不能为空");
        } else if ("wangxiong".equals(userName) && "123".equals(password)) {
            mainBaseView.showSuccessView(userName);
        } else {
            mainBaseView.showErrorView();
        }
    }
}
