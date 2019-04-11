package com.example.utils_library;

import android.content.Context;
import android.widget.Toast;

public class ToastUril {

    private static Toast toast;

    /**
     * 弹出Toast提示，保证同一应用同一时间只有一个Toast显示。
     * @param context
     * @param info
     */
    public static void showToast(Context context, String info) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), info, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(info);
            toast.show();
        }
    }
}
