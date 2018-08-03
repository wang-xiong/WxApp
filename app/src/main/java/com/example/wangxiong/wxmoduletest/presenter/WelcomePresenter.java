package com.example.wangxiong.wxmoduletest.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.component_base.base.mvp.BasePresenter;
import com.example.wangxiong.wxmoduletest.contract.WelcomeContract;
import com.example.wangxiong.wxmoduletest.modle.WelcomeModel;

/**
 * Created by wangxiong on 2018/7/13.
 */

public class WelcomePresenter extends BasePresenter<WelcomeContract.View, WelcomeContract.Model> implements WelcomeContract.Presenter{

    private Context context;

    public WelcomePresenter(Context context) {
        this.context = context;
    }

    @Override
    protected WelcomeContract.Model createModel() {
        return new WelcomeModel();
    }

    @Override
    public void loadWelcomeImg() {
        Bitmap bitmap = mModule.getWelcomeImg(context);
        mView.updateView(bitmap);
    }
}
