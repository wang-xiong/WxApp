package com.example.autoregister_app;

import android.util.Log;

import com.example.base_interface.IComponent;

public abstract class BaseComponent implements IComponent {

    public void doSth() {
        Log.i("BaseComponent", "do something else");
    }
}
