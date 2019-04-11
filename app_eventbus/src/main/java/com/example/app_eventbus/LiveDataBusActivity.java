package com.example.app_eventbus;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.aap_eventbus.R;

import io.reactivex.disposables.Disposable;

public class LiveDataBusActivity extends AppCompatActivity {

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus3);

        //1.注册事件
        LiveDataBus.getInstance().observeEvent("key_test", this, new Observer<MessageEvent>() {
            @Override
            public void onChanged(@Nullable MessageEvent event) {

            }
        });
        //2.发送事件
        LiveDataBus.getInstance().sendEventInMainThread("key_test", new MessageEvent("aaaa"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
