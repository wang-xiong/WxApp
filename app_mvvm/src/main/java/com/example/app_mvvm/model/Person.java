package com.example.app_mvvm.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.app_mvvm.BR;

public class Person extends BaseObservable {
    private String name;
    private String desc;


    public Person(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getDesc() {
        return desc;
    }

    public void setDesc(String password) {
        this.desc = password;
        notifyPropertyChanged(BR.desc);
    }
}
