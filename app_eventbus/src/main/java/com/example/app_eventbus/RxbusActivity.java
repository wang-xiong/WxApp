package com.example.app_eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.aap_eventbus.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class RxbusActivity extends AppCompatActivity {

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus3);

        //1.订阅事件
        disposable = RxBus1.getInstance().toObservable().map(new Function<Object, MessageEvent>() {
            @Override
            public MessageEvent apply(Object o) throws Exception {
                return (MessageEvent) o;
            }
        }).subscribe(new Consumer<MessageEvent>() {
            @Override
            public void accept(MessageEvent messageEvent) throws Exception {
                if (messageEvent != null) {
                    Log.d("", messageEvent.getMessage());
                }
            }
        });

        //2.发布事件
        RxBus1.getInstance().post(new MessageEvent("Test Rxbus"));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        disposable.dispose();
    }

}
