package com.example.app_dagger2.dagge2.test1;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

public class LoginPresenter {

    ICommonView iView;

    @Inject
    public LoginPresenter (ICommonView iView) {
        this.iView = iView;
    }

    public void login(User user) {
        Context context = iView.getContext();
        Toast.makeText(context, "login....", Toast.LENGTH_SHORT).show();
    }
}
