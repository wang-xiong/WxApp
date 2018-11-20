package com.example.component_base.base.mvc;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.component_base.base.BaseActivity;

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
