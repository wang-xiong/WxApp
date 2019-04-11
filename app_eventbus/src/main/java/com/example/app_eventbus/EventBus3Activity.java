package com.example.app_eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aap_eventbus.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBus3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus3);
        EventBus.getDefault().register(this);

        EventBus.getDefault().post(new MessageEvent("Test eventBus"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        messageEvent.getMessage();
    }


    /* EventBus3.0有四种线程模型:
     * POSTING (默认) 表示事件处理函数的线程跟发布事件的线程在同一个线程。
     * MAIN 表示事件处理函数的线程在主线程(UI)线程，因此在这里不能进行耗时操作。
     * BACKGROUND 表示事件处理函数的线程在后台线程，因此不能进行UI操作。
     *   如果发布事件的线程是主线程(UI线程)，那么事件处理函数将会开启一个后台线程，如果果发布事件的线程是在后台线程，那么事件处理函数就使用该线程。
     * ASYNC 表示无论事件发布的线程是哪一个，事件处理函数始终会新建一个子线程运行，同样不能进行UI操作。
     *
     * */
}
