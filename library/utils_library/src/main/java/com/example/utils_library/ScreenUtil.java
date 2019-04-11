package com.example.utils_library;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * Created by wangxiong on 2018/8/27.
 */

public class ScreenUtil {

    /**
     * 获取标题栏的高度
     *
     * @param activity 请求调用的Activity实例
     * @return 返回获取的标题栏高度
     */
    public static int getTitleBarHeight(Activity activity) {
        TypedValue typedValue = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
            DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
            return TypedValue.complexToDimensionPixelOffset(typedValue.data, displayMetrics);
        }
        return 0;
    }

    /**
     * 获取状态栏高度
     *
     * @param context 请求调用的Context对象
     * @return 返回获取的状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return resourceId == 0 ? 0 : context.getResources().getDimensionPixelOffset(resourceId);
    }

    /**
     * 获取虚拟按键的高度，不论虚拟按键是否显示都会返回其固定高度
     *
     * @param context 请求调用的Context对象
     * @return 返回获取的虚拟按键的高度
     */
    public static int getNaigationBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        return resourceId == 0 ? 0 : context.getResources().getDimensionPixelOffset(resourceId);
    }

    /**
     * 获取虚拟按键的高度，会根据当前是否显示虚拟按键来返回相应的值
     *
     * @param activity 请求调用的Activity实例
     * @return 返回获取的虚拟按键高度
     */
    public static int getNaigationBarHeightIfShow(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int usableHeight = displayMetrics.heightPixels;
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        int realHeight = displayMetrics.heightPixels;
        return realHeight - usableHeight;
    }

    /**
     * 获取不包括虚拟按键在内的屏幕高度
     *
     * @param context 请求调用的Context对象
     * @return 返回获取的屏幕高度
     */
    public static int getAvailableScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 获取不包括虚拟按键在内的屏幕高度
     *
     * @param activity 请求调用的Activity实例
     * @return 返回获取的屏幕高度
     */
    public static int getAvailableScreenHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 获取包括虚拟机在内的屏幕高度
     *
     * @param activity 请求调用的Activity实例
     * @return 返回获取的屏幕高度
     */
    public static int getToatalScreenHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


    /**
     * 获取屏幕宽度
     *
     * @param activity 请求调用的Activity实例
     * @return 返回获取的屏幕宽度
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context 请求调用的Context对象
     * @return 返回获取的屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Point point = new Point();
            windowManager.getDefaultDisplay().getSize(point);
            return point.x;
        }
        return 0;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context 请求调用的Context对象
     * @return 返回获取的屏幕宽度
     */
    public static int getScreenWidth2(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }
}
