package com.example.app_mvp.view.main;

import com.example.app_mvp.base.BaseView;

public interface MainBaseView extends BaseView {

    void showErrorView();

    void showEmptyTip(String tip);

    void showSuccessView(String userName);
}
