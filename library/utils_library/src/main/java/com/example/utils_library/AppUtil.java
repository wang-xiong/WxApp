package com.example.utils_library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

/**
 * Created by wangxiong on 2018/7/30.
 */

public class AppUtil {

    /**
     * 判单是否安装某应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstall(Context context, String packageName) {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return false;
        }
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        List<PackageInfo> infoList = packageManager.getInstalledPackages(0);
        for (PackageInfo info : infoList) {
            if (info.packageName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }


    public static void showInputMethod(Activity activity) {
        if (activity == null || activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.showSoftInput(activity.getCurrentFocus(), 0);
    }

    public static void hideInputMethod(final Activity activity) {
        if (activity == null || activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager = ((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE));
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    /**
     * 打开图库选取照片
     *
     * @param activity
     * @param requestCode
     */
    public static void startAlbum(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, requestCode);
    }
}
