package com.example.wangxiong.wxmoduletest.contract;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.component_base.base.mvp.inter.IModel;
import com.example.component_base.base.mvp.inter.IPresenter;
import com.example.component_base.base.mvp.inter.IView;

/**
 * Created by wangxiong on 2018/7/13.
 */

public interface WelcomeContract {

    interface View extends IView {
        void updateView(Bitmap bitmap);
    }

    interface Model extends IModel {
        Bitmap getWelcomeImg(Context context);
    }

    interface Presenter extends IPresenter<View> {
        void loadWelcomeImg();
    }
}
