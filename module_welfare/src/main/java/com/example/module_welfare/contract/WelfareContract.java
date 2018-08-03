package com.example.module_welfare.contract;

import com.example.component_base.base.mvp.inter.IModel;
import com.example.component_base.base.mvp.inter.IPresenter;
import com.example.component_base.base.mvp.inter.IView;
import com.example.module_welfare.bean.PhotoGirlBean;

/**
 * Created by wangxiong on 2018/7/20.
 */

public interface WelfareContract {
    interface View extends IView {
        void refreshView(PhotoGirlBean photoGirlBean);
    }

    interface Model extends IModel {
        void loadData(String url);
    }

    interface Presenter extends IPresenter<View> {
        void loadData(String url);
    }
}
