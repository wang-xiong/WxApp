package com.example.app_mvvm.viewmodel;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

public class MainViewModel {
    public String username;
    public String password;

    private Context context;

    public MainViewModel(Context context) {
        this.context = context;
    }

    public TextWatcher userNameChangeListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                username = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    public TextWatcher passwordChangeListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    public void login(View view) {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            if (username.equals("wx") && password.equals("123456")) {
                Toast.makeText(context, "登录成功",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "登录失败",Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "用户名密码不能为空",Toast.LENGTH_LONG).show();
        }
    }
}
