package com.example.utils_library;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by wangxiong on 2018/8/1.
 */

public class StatusBarHelper {

    /**
     * 1.Android5.0以上：material design风格，半透明(APP 的内容不被上拉到状态)
     * 2.Android4.4(kitkat)以上至5.0：全透明(APP 的内容不被上拉到状态)
     * 3.Android4.4(kitkat)以下:不占据status bar
     */
    public static void handleStatusBar(Activity activity, Boolean isFullTranslucentStatusBar, int statusBarColor) {
        Window window = activity.getWindow();

        /**
         * Android4.4（Api19）以上全透明状态栏实现
         * 1、在style-v19中设置windowTranslucentStatus为true
         * 2、代码设置FLAG_TRANSLUCENT_STATUS，此时状态栏透明
         * 3、但是在布局会上移，需要在布局中设置标题栏布局fitsSystemWindows属性
         * 或者在代码中设置标题的padding大小为状态栏，fitsSystemWindows的本质是给布局设置padding
         * a、一般在使用toolbar时需要设置fitsSystemWindows，同时高度需要设置为wrap_content,minHeight="?attr/actionBarSize"
         * b、建议的方式是给toolbar加父布局，比如LinearLayout然后高度设置wrap_content，颜色设置与toolbar一样
         * toolbar的高度设置为?attr/actionBarSize
         * 4、4.4-5.0是全透明，5.0以上会是半透明
         */



        /**
         * Material Design
         * Android5.0(Api21)以上全透明状态栏实现
         * 1、在style-v21中设置windowTranslucentStatus为false,同时设置android:statusBarColor的颜色透明
         * 2、代码设置如下
         * 2、不适于图片占据状态栏，它适用于纯色的状态栏
         */

        if (isFullTranslucentStatusBar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //设置导航栏透明时，布局会填充整个屏幕，需要在布局中设置标题栏布局fitsSystemWindows属性
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.setStatusBarColor(statusBarColor);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(statusBarColor);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //4.4-5.0使用三方工具类，有些4.4的手机有问题,不使用沉浸式
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                SystemBarTintManager tintManager = new SystemBarTintManager(activity);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintColor(statusBarColor);
            }
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}


