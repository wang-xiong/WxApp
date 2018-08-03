package com.example.module_common_ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.module_common_ui.view.ImageAlertView;

/**
 * Created by wangxiong on 2018/7/27.
 */

public class AccountActivity extends BaseMvcActivity {
    private View mAccountLayout;
    private ImageAlertView mImageAlertView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_ui_activity_account);
        mAccountLayout = findViewById(R.id.account_layout);

        mImageAlertView = new ImageAlertView(this);
        mAccountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageAlertView.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageAlertView.onActivityResult(resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImageAlertView.dismiss();
    }
}
