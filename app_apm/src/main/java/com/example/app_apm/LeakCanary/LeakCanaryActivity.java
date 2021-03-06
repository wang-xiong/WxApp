package com.example.app_apm.LeakCanary;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.app_apm.ApmApplication;
import com.example.app_apm.R;
import com.squareup.leakcanary.RefWatcher;

import java.lang.ref.WeakReference;

/**
 *使用方法
 * 1.在build.gradle添加依赖
 * 2.在Application初始化，提供监听RefWatcher
 * 3.在需要监听的对象，添加监听：refWatcher.watch(this);
 * 4.在manifest添加需要的权限
 */
public class LeakCanaryActivity extends AppCompatActivity {

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
    }

    private static class MyHandler  extends Handler {
        private WeakReference<LeakCanaryActivity> weak;
        public MyHandler(LeakCanaryActivity activity) {
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

        private WeakReference<LeakCanaryActivity> weakReference;

        private LeakThread(LeakCanaryActivity activity) {
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
