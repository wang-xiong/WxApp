package com.example.component_base.base.mvc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.component_base.Utils.StatusBarHelper;
import com.example.component_base.base.BaseActivity;
import com.example.component_base.constants.BaseApplication;

/**
 * Created by wangxiong on 2018/7/25.
 */

public abstract class BaseMvcActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
