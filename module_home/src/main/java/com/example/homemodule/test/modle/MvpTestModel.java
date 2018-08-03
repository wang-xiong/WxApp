package com.example.homemodule.test.modle;


import com.example.homemodule.test.contract.MvpTestContract;

/**
 * Created by wangxiong on 2018/7/13.
 * MVP模式，Home模块相关业务方法的具体实现
 */

public class MvpTestModel implements MvpTestContract.Model {
    @Override
    public String getHomeData() {
        return "首页";
    }

    @Override
    public String getOwnerName() {
        return "我的";
    }
}
