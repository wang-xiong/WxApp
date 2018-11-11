package com.example.module_common_ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.module_common_ui.R;

public class ShadowActivity extends BaseMvcActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_ui_activity_shadow);
    }
}
