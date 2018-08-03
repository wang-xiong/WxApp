package com.example.component_base.base.mvc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.component_base.Utils.StatusBarHelper;
import com.example.component_base.constants.BaseApplication;

/**
 * Created by wangxiong on 2018/7/25.
 */

public abstract class BaseMvcActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //加入Activity的管理
        BaseApplication.getBaseApplication().getActivityControl().addActivity(this);

        StatusBarHelper.handleStatusBar(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getBaseApplication().getActivityControl().removeActivity(this);
    }

}
