package com.example.component_base.Utils;

import android.util.Log;

import com.example.component_base.constants.BaseApplication;

/**
 * Created by wangxiong on 2018/7/13.
 */

public class WLog {
    public static final String TAG = "wx_app";

    public static void v(String msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BaseApplication.IS_DEBUG) {
            Log.e(tag, msg);
        }
    }
}
