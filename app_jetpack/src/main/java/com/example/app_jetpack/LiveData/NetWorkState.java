package com.example.app_jetpack.LiveData;

import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

public class NetWorkState extends LiveData<Boolean> {

    private Context context;
    private boolean value;

    public NetWorkState(Context context) {
        this.context = context;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //value = true;
        }
    };

    @Override
    protected void setValue(Boolean value) {
        setValue(value);
    }

    @Override
    protected void onActive() {
        super.onActive();
        context.unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(broadcastReceiver, intentFilter);
    }
}