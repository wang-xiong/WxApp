package com.example.app_apm.blockcanary;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.app_apm.ApmApplication;
import com.example.app_apm.R;
import com.squareup.leakcanary.RefWatcher;

import java.lang.ref.WeakReference;

/**
 *使用方法
 * 1.在build.gradle添加依赖
 * 2.在Application初始化BlockCanary
 *原理:
 *blockCanary 利用了handler原理在dispatchMessage()的上下方分别打印方法执行的时间，然后根据上下两个时间差，
 * 来判断dispatchMessage()中是否产生了耗时的操作，也就是这个dispatchMessage():是否有Ui卡顿；
 * 如果有Ui卡顿上下两个值计算的阀值就是配置的阀值，如果超过这个阀值，就可以dump出Ui卡顿的信息。
 * 通过堆栈信息来定位卡顿问题
 */
public class BlockCanaryActivity extends AppCompatActivity {

    private MyHandler myHandler;
    private LeakThread mLeakThread;
    private boolean isClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_leak);


        myHandler = new MyHandler(this);
        mLeakThread = new LeakThread(this);
        mLeakThread.start();

        findViewById(R.id.hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(10*1000);
                    Log.e("wx", "hello");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static class MyHandler  extends Handler {
        private WeakReference<BlockCanaryActivity> weak;
        public MyHandler(BlockCanaryActivity activity) {
            weak = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what ==0 ) {
                if (weak != null && weak.get() != null) {
                    Log.e("wx", "v");
                }
            }
        }
    }

    //1.内存泄露的原因：非静态内部类持有外部类的引用。LeakThread做了耗时操作导致MemoryLeakActivity无法释放。
    //解决方法：将LeakThread改为静态内部类
    private static class LeakThread extends Thread {

        private WeakReference<BlockCanaryActivity> weakReference;

        private LeakThread(BlockCanaryActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            try {
                Thread.sleep(6*60*1000);
                if (weakReference != null && weakReference.get() != null) {
                    if (weakReference.get().isClose) {
                        return;
                    }
                    weakReference.get().myHandler.sendEmptyMessage(0);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void close() {
            if(null != weakReference && null != weakReference.get()) {
                weakReference.get().isClose = true;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacksAndMessages(null);
        mLeakThread.close();
        //可以不用写，默认会检测Activity的内存泄露
        //fragment也是类似在onDestroy添加如下监听
        RefWatcher refWatcher = ApmApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }


}
