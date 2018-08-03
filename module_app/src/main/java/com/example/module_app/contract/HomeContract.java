package com.example.module_app.contract;

import com.example.component_base.base.mvp.inter.IModel;
import com.example.component_base.base.mvp.inter.IPresenter;
import com.example.component_base.base.mvp.inter.IView;
import com.example.module_app.bean.HomeData;

/**
 * Created by wangxiong on 2018/7/31.
 */

public interface HomeContract {
    interface View extends IView {
        void update(HomeData data);
        void refreshData(HomeData data);
    }

    interface Model extends IModel{
        void loadArticleList(String url, Class<?> clazz);
        void refreshArticleList(String url, Class<?> clazz);
    }

    interface Presenter extends IPresenter<View> {
        void loadArticleList();
        void refreshArticleList();
        void loadNextPage();
    }
}
