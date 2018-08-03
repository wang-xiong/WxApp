package com.example.module_welfare.model;

import com.example.module_welfare.bean.PhotoGirlBean;
import com.example.module_welfare.contract.WelfareContract;
import com.example.okhttp_library.DisposeDataListener;
import com.example.okhttp_library.RequestCenter;

/**
 * Created by wangxiong on 2018/7/20.
 */

public class WelfareModel implements WelfareContract.Model, DisposeDataListener {
    private Callback mCallback;

    public WelfareModel(Callback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void loadData(String url) {
        RequestCenter.requestWelfareData(url,this, PhotoGirlBean.class);
    }

    @Override
    public void onSuccess(Object responseObj) {
        PhotoGirlBean photoGirlBean = (PhotoGirlBean) responseObj;
        if (mCallback != null) {
            mCallback.onLoadDataFinish(photoGirlBean);
        }
    }

    @Override
    public void onFailure(Object responseObj) {

    }

    public  interface Callback {
        void onLoadDataFinish(PhotoGirlBean photoGirlBean);
    }
}
