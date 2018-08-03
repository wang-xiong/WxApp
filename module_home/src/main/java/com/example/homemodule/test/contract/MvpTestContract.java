package com.example.homemodule.test.contract;

import com.example.component_base.base.mvp.inter.IModel;
import com.example.component_base.base.mvp.inter.IPresenter;
import com.example.component_base.base.mvp.inter.IView;

/**
 * Created by wangxiong on 2018/7/13.
 * MVP模式，
 */

public interface MvpTestContract {

    /**
     * Home模块的View接口，继承IView，包含相关界面UI处理方法
     */
    interface View extends IView {
        void updateView(String firstTab, String secondTab);
    }

    /**
     * Home模块的Model接口，继承IModel，包含相关数据获取处理方法
     */
    interface Model extends IModel {
        String getHomeData();
        String getOwnerName();
    }

    /**
     * Home模块的Presenter接口，继承IPresenter，包含相关业务逻辑处理方法
     */
    interface Presenter extends IPresenter<View> {
        void loadHomeData();
    }
}
