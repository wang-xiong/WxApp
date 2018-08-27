package com.example.module_common_ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;

import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.module_common_ui.R;

/**
 * Created by wangxiong on 2018/8/24.
 */

public class CustomView2Activity extends BaseMvcActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_ui_activity_custom_view2);
    }

}
