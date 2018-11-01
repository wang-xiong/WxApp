package com.example.app_jetpack.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LoginViewModel extends AndroidViewModel {

    public MutableLiveData<String> loginString = new MutableLiveData();

    public MutableLiveData<String> data = new MutableLiveData();
    public MutableLiveData<String> loginString1 = new MutableLiveData();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void login() {
        //登录成功后返回response;
        String response = null;
        loginString.setValue(response);

    }

    public void login(LifecycleOwner owner) {
        data.observe(owner, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                loginString1.setValue(s);
            }
        });


        //登录成功后返回response;
        String response = null;
        loginString.setValue(response);
    }
}
