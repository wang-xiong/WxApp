package com.example.component_base.base.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.component_base.base.mvp.inter.IPresenter;
import com.example.component_base.base.mvp.inter.IView;
import com.example.utils_library.permission.PermissionHelper;

/**
 * Created by wangxiong on 2018/7/25.
 */

public abstract class BaseMvpPermissionActivity<V extends IView, P extends IPresenter<V>> extends BaseMvpActivity<V, P> {

    private PermissionHelper mPermissionHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPermissionHelper != null) {
            mPermissionHelper.onDestroy();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionHelper != null) {
            mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void requestPermission(String[] permissions, PermissionHelper.PermissionInterface permissionInterface) {
        mPermissionHelper = new PermissionHelper(this, permissionInterface);
        mPermissionHelper.requestPermission(permissions);
    }

}
