package com.example.utils_library;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by wangxiong on 2018/8/27.
 */

public class ScreenUtil {

    public int dp2px(Context context, int dp) {
        DisplayMetrics metrics  = context.getApplicationContext().getResources().getDisplayMetrics();
        return (int) (metrics.density * dp + 0.5f);
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        try {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return metrics.widthPixels;
        } catch (Exception e) {
        }
        return 0;
    }
    /**
     * 获取屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        try {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return metrics.heightPixels;
        } catch (Exception e) {
        }
        return 0;
    }
}
