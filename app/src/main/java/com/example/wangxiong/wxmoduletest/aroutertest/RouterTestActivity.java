package com.example.wangxiong.wxmoduletest.aroutertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.wangxiong.wxmoduletest.R;

// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = "/wxmoduletest/RouterTestActivity")
//@Route(path = "/wxmoduletest/RouterTestActivity", group = "wxmoduletest")
public class RouterTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router_test);
    }
}
