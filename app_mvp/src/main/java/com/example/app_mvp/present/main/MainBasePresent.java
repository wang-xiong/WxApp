package com.example.app_mvp.present.main;

import com.example.app_mvp.view.main.MainBaseView;
import com.example.app_mvp.base.BasePresent;

public interface MainBasePresent extends BasePresent<MainBaseView> {
    void login(String userName, String password);
}
