package com.example.app_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.utils_library.AlarmManagerUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlarmManagerUtils alarmManagerUtils = AlarmManagerUtils.getInstance(this);
        alarmManagerUtils.createGetUpAlarmManager();
        alarmManagerUtils.getUpAlarmManagerStartWork();
    }
}
