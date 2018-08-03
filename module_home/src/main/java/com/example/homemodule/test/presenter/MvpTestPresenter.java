package com.example.homemodule.test.presenter;

import com.example.component_base.base.mvp.BasePresenter;
import com.example.homemodule.test.contract.MvpTestContract;
import com.example.homemodule.test.modle.MvpTestModel;

/**
 * Created by wangxiong on 2018/7/12.
 */

public class MvpTestPresenter extends BasePresenter<MvpTestContract.View, MvpTestContract.Model> implements MvpTestContract.Presenter {

    @Override
    protected MvpTestContract.Model createModel() {
        return new MvpTestModel();
    }

    @Override
    public void loadHomeData() {
        String homeName = mModule.getHomeData();
        String ownerName = mModule.getOwnerName();
        mView.updateView(homeName, ownerName);
    }
}