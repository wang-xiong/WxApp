package com.example.utils_library;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {


    /**
     * dp值转换为px值
     *
     * @param context 请求的Context对象
     * @param dp      需要转换的dp值
     * @return 转换后的px值
     */
    public int dp2px(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * px值转换为dp值
     *
     * @param context 请求的Context对象
     * @param px      需要转换的px值
     * @return 转换后的dp值
     */
    public int px2dp(Context context, float px) {
        return (int) (px / context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * sp值转换为px值
     *
     * @param context 请求的Context对象
     * @param sp      需要转换的sp值
     * @return 转换后的px值
     */
    public int sp2px(Context context, float sp) {
        return (int) (sp * context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }

    /**
     * px值转换为sp值
     *
     * @param context 请求的Context对象
     * @param px      需要转换的px值
     * @return 转换后的sp值
     */
    public int px2sp(Context context, float px) {
        return (int) (px / context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }
}
