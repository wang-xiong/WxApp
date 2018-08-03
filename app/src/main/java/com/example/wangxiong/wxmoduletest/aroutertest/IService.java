package com.example.wangxiong.wxmoduletest.aroutertest;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Created by wangxiong on 2018/7/11.
 */


//暴露服务
public interface IService extends IProvider{
    void sayHello(Context context);
}
