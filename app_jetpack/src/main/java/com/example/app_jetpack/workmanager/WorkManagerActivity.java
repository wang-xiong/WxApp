package com.example.app_jetpack.workmanager;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.app_jetpack.workmanager.MyWorker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;

public class WorkManagerActivity extends AppCompatActivity {
    /**
     * 18发布Android WorkManager
     * JobScheduler Firebase的JobDispatcher AlarmManager Services
     * JobScheduler是SDK21中出现的，SDK23稳定
     * AlarmManager一直存在
     * WorkManager使用
     * 在API23+ 使用JobScheduler
     * 在14-22
     * 如果在应用中使用了Firebase JobDispatcher并且有可选的Firebase依赖项就使用Firebase JobDispatcher
     * 否则使用自定义的AlarmManager + BroadcastReceiver的实现方式
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worl_manager);

        //添加限定条件
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) //网络状态
                .setRequiresBatteryNotLow(true) //不在电量不足时执行
                .setRequiresCharging(true) //在充电时执行
                .setRequiresStorageNotLow(true) //不在存储不足时执行
                //.setRequiresDeviceIdle(true) //在待机状态下不执行 api23+
                .build();

        //一次性任务
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setConstraints(constraints)
                .build();
        //执行任务
        WorkManager workManager = WorkManager.getInstance();
        workManager.enqueue(workRequest);

        LiveData<WorkStatus> statusLiveData  = workManager.getStatusById(workRequest.getId());


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
        Data data = new Data.Builder()
                .putString("date", simpleDateFormat.format(new Date()))
                .build();

        //定时任务（最小的时间是15分钟）
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .setInputData(data)
                .build();

        //任务链
        WorkManager.getInstance()
                .beginWith(workRequest)
                .then(workRequest)
                .enqueue();

        //任务唯一性
        WorkManager.getInstance()
                .beginUniqueWork("unique", ExistingWorkPolicy.REPLACE, workRequest)
                .enqueue();

        /**
         * 非定时任务
         * 应用杀死，手机重启都可以
         */


        /**不能用于应用保活
         * 1、定时任务有最小间隔时间的限制，是 15 分钟
         * 2、只有程序运行时，任务才会得到执行
         * 3、无法拉起 Activity
         */

        //Target API要求
        /**
         * 18年Target API大小26
         * 19 更新到最新
         */

    }
}
