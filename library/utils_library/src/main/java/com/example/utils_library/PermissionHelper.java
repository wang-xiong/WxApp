package com.example.utils_library;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.util.List;

public class PermissionHelper {

    private Activity mActivity;
    private PermissionInterface mPermissionInterface;
    private AlertDialog mAlertDialog;

    public PermissionHelper(Activity mActivity, PermissionInterface mPermissionInterface) {
        this.mActivity = mActivity;
        this.mPermissionInterface = mPermissionInterface;
    }

    public void requestPermission(String[] permissions) {
        PermissionUtil.requestPermission(mActivity , permissions, mPermissionInterface.getRequestCode());
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == mPermissionInterface.getRequestCode()) {
            if (hasAllPermissions(grantResults)) {
                mPermissionInterface.onRequestPermissionSuccess();
            } else {
                if (shouldRequestPermissionAgain(permissions)) {
                    mActivity.finish();
                } else {
                    List<String> permissionList = PermissionUtil.getRequestPermissionList(mActivity, permissions);
                    StringBuilder builder = new StringBuilder();
                    builder.append("此应用缺少如下权限,请在设置中打开:\n");
                    for (String permission : permissionList) {
                        builder.append(permission);
                        builder.append("\n");
                    }
                    //没有获得所有权限，并且用户选择了不再提醒
                    showPermissionDialog(builder.toString());
                }
            }
        }
    }

    /**
     * 判断是否获取所有权限
     *
     * @param grantResults
     * @return
     */
    private boolean hasAllPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    private boolean shouldRequestPermissionAgain(String[] permissions) {
        for (String permission : permissions) {
            //判断是否显示申请权限对话框，如果同意了或者不在咨询返回false
            //判断下次进入是否继续申请
            boolean requestPermissionAgain = ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission);
            if (requestPermissionAgain) {
                return true;
            }
        }
        return false;
    }

    private void showPermissionDialog(String message) {
        mAlertDialog = new AlertDialog.Builder(mActivity)
                .setMessage(message)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog.dismiss();
                        mActivity.finish();
                    }
                })
                .setPositiveButton("打开设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog.dismiss();
                        mActivity.finish();
                        Uri uri = Uri.parse("package:" + mActivity.getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                        mActivity.startActivity(intent);
                    }
                }).create();
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();

    }



    public void onDestroy() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }


    public interface PermissionInterface {
        int getRequestCode();
        void onRequestPermissionSuccess();
    }
}
