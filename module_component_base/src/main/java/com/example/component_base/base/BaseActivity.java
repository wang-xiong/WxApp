package com.example.component_base.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.component_base.constants.BaseApplication;
import com.example.utils_library.StatusBarHelper;

/**
 * Created by wangxiong on 2018/8/13.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加入Activity的管理
        BaseApplication.getBaseApplication().getActivityControl().addActivity(this);
        StatusBarHelper.handleStatusBar(this, isFullTranslucentStatusBar(), setStatusBarColor());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getBaseApplication().getActivityControl().removeActivity(this);
    }

    /**
     * 状态栏颜色
     * @return
     */
    protected int setStatusBarColor() {
        return Color.TRANSPARENT;
    }

    /**
     * 是否是全透明状态栏
     * @return
     */
    protected boolean isFullTranslucentStatusBar() {
        return false;
    }
}
