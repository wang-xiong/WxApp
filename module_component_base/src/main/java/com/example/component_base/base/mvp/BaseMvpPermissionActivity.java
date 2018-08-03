package com.example.component_base.base.mvp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.example.component_base.base.mvp.inter.IPresenter;
import com.example.component_base.base.mvp.inter.IView;

/**
 * Created by wangxiong on 2018/7/25.
 */

public abstract class BaseMvpPermissionActivity<V extends IView, P extends IPresenter<V>> extends BaseMvpActivity<V, P> {

    /**
     * 照相机权限
     */
    public static final int PERMISSION_CAMERA = 10001;
    /**
     * 文件管理权限
     */
    public static final int PERMISSION_STORAGE = 10002;
    /**
     * 电话权限
     */
    public static final int PERMISSION_PHONE = 10003;

    private AlertDialog mAlertDialog;
    private boolean mIsShouldShow = false;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions != null && permissions.length>0 && grantResults != null && grantResults.length>0) {
            int grantResult = grantResults[0];
            String permission = permissions[0];
            if (grantResult == PackageManager.PERMISSION_DENIED
                    && !ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
                    && !mIsShouldShow) {
                //没有获得权限，并且用户选择了不再提醒
                showPermissionDialog();
            }
        }
    }

    public boolean getPermission(String permission, int requestCode) {
        if (isMarshmallow() && !isGranted(permission)) {
            //未授权，且系统版本为6.0以上
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                mIsShouldShow = true;
                //申请权限
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            } else {
                //没有申请权限
                mIsShouldShow = false;
                showPermissionDialog();
            }
            return false;
        }
        return true;
    }

    /**
     * 判断是否6.0版本以上
     *
     * @return
     */
    private boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 判断是否有权限
     *
     * @param permission
     * @return
     */
    private boolean isGranted(String permission) {
        int granted = ActivityCompat.checkSelfPermission(this, permission);
        return granted == PackageManager.PERMISSION_GRANTED;
    }

    private void showPermissionDialog() {
        mAlertDialog = new AlertDialog.Builder(this)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog.dismiss();
                    }
                })
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog.dismiss();;
                        Uri uri = Uri.parse("package:" + getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
                        startActivity(intent);
                    }
                }).create();
        mAlertDialog.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }
}
