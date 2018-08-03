package com.example.wangxiong.wxmoduletest.aroutertest;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * Created by wangxiong on 2018/7/11.
 */

//暴露服务
@Route(path = "/service/hello", name = "测试服务")
public class MyService implements IService {
    @Override
    public void sayHello(Context context) {
        Toast.makeText(  context , "hello", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void init(Context context) {

    }
}
