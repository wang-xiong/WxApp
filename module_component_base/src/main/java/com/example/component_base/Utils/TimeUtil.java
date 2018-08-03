package com.example.component_base.Utils;

/**
 * Created by wangxiong on 2018/8/2.
 */

public class TimeUtil {
    private static long lastClickTime;

    public static boolean isFastClick(long intervalTime) {
        long time = System.currentTimeMillis();
        if (time-lastClickTime<intervalTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
