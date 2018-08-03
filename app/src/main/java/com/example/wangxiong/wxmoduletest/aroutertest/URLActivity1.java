package com.example.wangxiong.wxmoduletest.aroutertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.wangxiong.wxmoduletest.R;

@Route(path = "/wxmoduletest/URLActivity1")
public class URLActivity1 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url1);
    }
}
