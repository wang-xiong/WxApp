package com.example.module_app.model;

import com.example.module_app.contract.HomeContract;
import com.example.okhttp_library.DisposeDataListener;
import com.example.okhttp_library.RequestCenter;

/**
 * Created by wangxiong on 2018/7/31.
 */

public class HomeModel implements HomeContract.Model, DisposeDataListener {
    private Callback callback;

    public HomeModel(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void loadArticleList(String url, Class<?> clazz) {
        RequestCenter.requestArticleListData(url, this, clazz);
    }

    @Override
    public void refreshArticleList(String url, Class<?> clazz) {
        RequestCenter.requestArticleListData(url, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                if (callback != null) {
                    callback.onRefreshFinish(responseObj);
                }
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        }, clazz);
    }

    @Override
    public void onSuccess(Object responseObj) {
        if (callback != null) {
            callback.onLoadDataFinish(responseObj);
        }
    }

    @Override
    public void onFailure(Object responseObj) {

    }

    public interface Callback {
        void onLoadDataFinish(Object responseObj);
        void onRefreshFinish(Object responseObj);
    }
}
