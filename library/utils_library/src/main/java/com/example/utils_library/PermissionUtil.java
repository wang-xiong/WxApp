package com.example.utils_library;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {

    public static void requestPermission(Activity activity, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && activity.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.M) {
            List<String> permissionList = getRequestPermissionList(activity, permissions);
            if (permissionList != null && permissionList.size() > 0) {
                String[] permissionArray = permissionList.toArray(new String[0]);
                ActivityCompat.requestPermissions(activity, permissionArray, requestCode);
            }
        }
    }

    /**
     * 获得需要申请的权限
     *
     * @param permissions 所有权限
     * @return 需要申请的权限
     */
    public static List<String> getRequestPermissionList(Activity activity, String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            //判断是否获有此权限
            boolean hasPermission = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(activity, permission);
            if (!hasPermission) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }

    public static boolean hasAllPermission(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            //判断是否获有此权限
            boolean hasPermission = PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(activity, permission);
            if (!hasPermission) {
                return false;
            }
        }
        return true;
    }
}
