package com.example.module_welfare.presenter;

import com.example.component_base.base.mvp.BasePresenter;
import com.example.module_welfare.bean.PhotoGirlBean;
import com.example.module_welfare.model.WelfareModel;
import com.example.module_welfare.contract.WelfareContract;

/**
 * Created by wangxiong on 2018/7/20.
 */

public class WelfarePresenter extends BasePresenter<WelfareContract.View, WelfareContract.Model> implements WelfareContract.Presenter, WelfareModel.Callback {
    @Override
    protected WelfareContract.Model createModel() {
        return new WelfareModel(this);
    }

    @Override
    public void loadData(String url) {
        mModule.loadData(url);
    }


    @Override
    public void onLoadDataFinish(PhotoGirlBean photoGirlBean) {
        mView.refreshView(photoGirlBean);
    }
}
