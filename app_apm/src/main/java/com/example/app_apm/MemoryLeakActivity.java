package com.example.app_apm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.leakcanary.RefWatcher;

/**
 *使用方法
 * 1.在build.gradle添加依赖
 * 2.在Application初始化，提供监听RefWatcher
 * 3.在需要监听的对象，添加监听：refWatcher.watch(this);
 * 4.在manifest添加需要的权限
 */
public class MemoryLeakActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_leak);

        LeakThread leakThread = new LeakThread();
        leakThread.start();
    }

    //1.内存泄露的原因：非静态内部类持有外部类的引用。LeakThread做了耗时操作导致MemoryLeakActivity无法释放。
    //解决方法：将LeakThread改为静态内部类
    static class LeakThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(6*60*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //可以不用写，默认会检测Activity的内存泄露
        //fragment也是类似在onDestroy添加如下监听
        RefWatcher refWatcher = ApmApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
