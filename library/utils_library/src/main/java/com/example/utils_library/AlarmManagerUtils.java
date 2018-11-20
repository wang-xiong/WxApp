package com.example.utils_library;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class AlarmManagerUtils {

    /**
     * 1.进程被杀死，AlarmManager停止工作
     * 2.手机重启，AlarmManager停止工作
     * 3.各厂商的“心跳对齐”
     */

    /**
     * 使用方法：
     * AlarmManagerUtils alarmManagerUtils = AlarmManagerUtils.getInstance(this);
     * alarmManagerUtils.createGetUpAlarmManager();
     * alarmManagerUtils.getUpAlarmManagerStartWork();
     * 新建广播，在广播接收器中调用
     * AlarmManagerUtils.getInstance(context).getUpAlarmManagerWorkOnReceiver();
     */

    private static final long TIME_INTERVAL = 5 * 1000;//闹钟执行任务的时间间隔
    private Context context;
    private static AlarmManager am;
    private static PendingIntent pendingIntent;

    //
    private AlarmManagerUtils(Context context) {
        this.context = context;
    }

    //饿汉式单例设计模式
    private static AlarmManagerUtils instance;

    public static AlarmManagerUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (AlarmManagerUtils.class) {
                if (instance == null) {
                    instance = new AlarmManagerUtils(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public void createGetUpAlarmManager() {
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent("WANG_LEI");
        intent.putExtra("msg", "赶紧起床");
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);//每隔5秒发送一次广播
    }

    @SuppressLint("NewApi")
    public void getUpAlarmManagerStartWork() {
        //版本适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 6.0及以上
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 4.4及以上
            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                    pendingIntent);
        } else {
            am.setRepeating(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(), TIME_INTERVAL, pendingIntent);
        }
    }

    @SuppressLint("NewApi")
    public void getUpAlarmManagerWorkOnReceiver() {
        //高版本重复设置闹钟达到低版本中setRepeating相同效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 6.0及以上
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + TIME_INTERVAL, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 4.4及以上
            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + TIME_INTERVAL, pendingIntent);
        }
    }
}
