package com.example.utils_library;

import android.util.Log;

/**
 * Created by wangxiong on 2018/7/13.
 */

public class WLog {
    private static final String TAG = "wx_app";
    public static boolean IS_DEBUG = BuildConfig.DEBUG;

    public static void v(String msg) {
        if (IS_DEBUG) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (IS_DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (IS_DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (IS_DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (IS_DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (IS_DEBUG) {
            Log.v(TAG, "[" + tag + "]" + msg);
        }
    }

    public static void d(String tag, String msg) {
        if (IS_DEBUG) {
            Log.d(TAG, "[" + tag + "]" + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (IS_DEBUG) {
            Log.i(TAG, "[" + tag + "]" + msg);
        }
    }

    public static void w(String tag, String msg) {
        if (IS_DEBUG) {
            Log.w(TAG, "[" + tag + "]" + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (IS_DEBUG) {
            Log.e(TAG, "[" + tag + "]" + msg);
        }
    }
}
