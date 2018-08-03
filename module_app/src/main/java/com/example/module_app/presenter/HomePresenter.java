package com.example.module_app.presenter;

import com.example.component_base.base.mvp.BasePresenter;
import com.example.module_app.bean.HomeData;
import com.example.module_app.contract.HomeContract;
import com.example.module_app.model.HomeModel;

/**
 * Created by wangxiong on 2018/7/31.
 */

public class HomePresenter extends BasePresenter<HomeContract.View, HomeContract.Model> implements HomeContract.Presenter, HomeModel.Callback {
    private String url = "http://www.wanandroid.com/article/list/10/json";
    private String url2 = "http://www.wanandroid.com/article/list/20/json";

    @Override
    protected HomeContract.Model createModel() {
        return new HomeModel(this);
    }

    @Override
    public void loadArticleList() {
        mModule.loadArticleList(url, HomeData.class);
    }

    @Override
    public void refreshArticleList() {
        mModule.refreshArticleList(url2, HomeData.class);
    }

    @Override
    public void loadNextPage() {
        //mModule.loadArticleList(url2, HomeData.class);
    }

    @Override
    public void onLoadDataFinish(Object object) {
        if(object instanceof HomeData) {
            HomeData data = (HomeData) object;
            mView.update(data);
        }
    }

    @Override
    public void onRefreshFinish(Object object) {
        if(object instanceof HomeData) {
            HomeData data = (HomeData) object;
            mView.refreshData(data);
        }
    }

}
