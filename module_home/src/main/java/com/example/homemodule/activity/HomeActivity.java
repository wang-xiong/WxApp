package com.example.homemodule.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.component_base.ARouter.RouterUrl;
import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.homemodule.R;
import com.example.homemodule.arouter.RouterCenter;

/**
 * Created by wangxiong on 2018/7/23.
 */

@Route(path = RouterUrl.PATH_MODULE_HOME_HOME)
public class HomeActivity extends BaseMvcActivity implements View.OnClickListener{
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_home_activity_home);
        initView();
    }

    private void initView() {
        mButton1 = findViewById(R.id.btn1);
        mButton2 = findViewById(R.id.btn2);
        mButton3 = findViewById(R.id.btn3);
        mButton4 = findViewById(R.id.btn4);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (R.id.btn1 == v.getId()) {
            RouterCenter.toCommonActivity();
        } else if (R.id.btn2 == v.getId()) {
            RouterCenter.toAppMvpTestActivity();
        } else if (R.id.btn3 == v.getId()) {
            RouterCenter.toToolBarActivity();
        } else if (R.id.btn4 == v.getId()) {
            RouterCenter.toAppMainActivity();
        }
    }
}
