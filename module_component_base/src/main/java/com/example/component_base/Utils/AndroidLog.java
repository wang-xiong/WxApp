package com.example.component_base.Utils;

import android.util.Log;

import java.util.Calendar;

/**
 * Log工具类 默认tag为voice log输出由prop中的log等级开关控制 "adb shell setprop log.tag.voice VERBOSE"
 * 可设定tag的log的输出等级 设定后重启应用生效
 */
public class AndroidLog {
    private static final String LOG_TAG = "wx_app";
    private static boolean isOpenV = Log.isLoggable(LOG_TAG, Log.VERBOSE);
    private static boolean isOpenD = Log.isLoggable(LOG_TAG, Log.DEBUG);
    private static boolean isOpenI = true; // Log.isLoggable(LOG_TAG, Log.INFO);
    private static boolean isOpenW = true; // Log.isLoggable(LOG_TAG, Log.WARN);
    private static boolean isOpenE = true;// Log.isLoggable(LOG_TAG, Log.ERROR);

    public static String getFileLineMethod() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        StringBuffer toStringBuffer = new StringBuffer("[").append(traceElement.getFileName())
                .append(" | ").append(traceElement.getLineNumber()).append(" | ")
                .append(traceElement.getMethodName()).append("()").append("]");
        return toStringBuffer.toString();
    }

    private static String _FILE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getFileName();
    }

    private static String _FUNC_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getMethodName();
    }

    private static int _LINE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getLineNumber();
    }

    private static String _TIME_() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        int sss = c.get(Calendar.MILLISECOND);
        return "[" + day + "-" + hour + ":" + minute + ":" + second + ":" + sss + "]";
    }

    public static void v(String msg, String log) {
        if (isOpenV) {
            StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
            StringBuffer toStringBuffer = new StringBuffer("[").append(traceElement.getFileName())
                    .append(" | ").append(traceElement.getLineNumber()).append(" | ")
                    .append(traceElement.getMethodName()).append("()").append("]");
            String TAG = toStringBuffer.toString();
            Log.v(msg, TAG + log /* + _TIME_() */);
        }
    }

    public static void v(String log) {
        if (isOpenV) {
            StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
            StringBuffer toStringBuffer = new StringBuffer("[").append(traceElement.getFileName())
                    .append(" | ").append(traceElement.getLineNumber()).append(" | ")
                    .append(traceElement.getMethodName()).append("()").append("]");
            String TAG = toStringBuffer.toString();
            Log.v(LOG_TAG, TAG + log /* + _TIME_() */);
        }
    }

    public static void d(String msg, String log) {
        if (isOpenD) {
            StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
            StringBuffer toStringBuffer = new StringBuffer("[").append(traceElement.getFileName())
                    .append(" | ").append(traceElement.getLineNumber()).append(" | ")
                    .append(traceElement.getMethodName()).append("()").append("]");
            String TAG = toStringBuffer.toString();
            Log.d(msg, TAG + log /* + _TIME_() */);
        }
    }

    public static void d(String log) {
        if (isOpenD) {
            StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
            StringBuffer toStringBuffer = new StringBuffer("[").append(traceElement.getFileName())
                    .append(" | ").append(traceElement.getLineNumber()).append(" | ")
                    .append(traceElement.getMethodName()).append("()").append("]");
            String TAG = toStringBuffer.toString();
            Log.d(LOG_TAG, TAG + log /* + _TIME_() */);
        }
    }

    public static void i(String msg, String log) {
        if (isOpenI) {
            StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
            StringBuffer toStringBuffer = new StringBuffer("[").append(traceElement.getFileName())
                    .append(" | ").append(traceElement.getLineNumber()).append(" | ")
                    .append(traceElement.getMethodName()).append("()").append("]");
            String TAG = toStringBuffer.toString();
            Log.i(msg, TAG + log /* + _TIME_() */);
        }
    }

    public static void i(String log) {
        if (isOpenI) {
            StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
            StringBuffer toStringBuffer = new StringBuffer("[").append(traceElement.getFileName())
                    .append(" | ").append(traceElement.getLineNumber()).append(" | ")
                    .append(traceElement.getMethodName()).append("()").append("]");
            String TAG = toStringBuffer.toString();
            Log.i(LOG_TAG, TAG + log /* + _TIME_() */);
        }
    }

    public static void w(String msg, String log) {
        if (isOpenW) {
            StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
            StringBuffer toStringBuffer = new StringBuffer("[").append(traceElement.getFileName())
                    .append(" | ").append(traceElement.getLineNumber()).append(" | ")
                    .append(traceElement.getMethodName()).append("()").append("]");
            String TAG = toStringBuffer.toString();
            Log.w(msg, TAG + log /* + _TIME_() */);
        }
    }

    public static void w(String log) {
        if (isOpenW) {
            StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
            StringBuffer toStringBuffer = new StringBuffer("[").append(traceElement.getFileName())
                    .append(" | ").append(traceElement.getLineNumber()).append(" | ")
                    .append(traceElement.getMethodName()).append("()").append("]");
            String TAG = toStringBuffer.toString();
            Log.w(LOG_TAG, TAG + log /* + _TIME_() */);
        }
    }

    public static void e(String msg, String log) {
        if (isOpenE) {
            StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
            StringBuffer toStringBuffer = new StringBuffer("[").append(traceElement.getFileName())
                    .append(" | ").append(traceElement.getLineNumber()).append(" | ")
                    .append(traceElement.getMethodName()).append("()").append("]");
            String TAG = toStringBuffer.toString();
            Log.e(msg, TAG + log /* + _TIME_() */);
        }
    }

    public static void e(String log) {
        if (isOpenE) {
            StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
            StringBuffer toStringBuffer = new StringBuffer("[").append(traceElement.getFileName())
                    .append(" | ").append(traceElement.getLineNumber()).append(" | ")
                    .append(traceElement.getMethodName()).append("()").append("]");
            String TAG = toStringBuffer.toString();
            Log.e(LOG_TAG, TAG + log /* + _TIME_() */);
        }
    }

}
