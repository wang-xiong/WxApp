package com.example.app_test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.utils_library.AlarmManagerUtils;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //高版本重复设置闹钟达到低版本中setRepeating相同效果
        AlarmManagerUtils.getInstance(context).getUpAlarmManagerWorkOnReceiver();
    }
}
