package com.example.wangxiong.wxmoduletest.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.component_base.base.mvp.BaseMvpActivity;
import com.example.wangxiong.wxmoduletest.R;
import com.example.wangxiong.wxmoduletest.arouter.RouterCenter;
import com.example.wangxiong.wxmoduletest.contract.WelcomeContract;
import com.example.wangxiong.wxmoduletest.presenter.WelcomePresenter;

public class WelcomeActivity extends BaseMvpActivity<WelcomeContract.View, WelcomeContract.Presenter> implements WelcomeContract.View
        , View.OnClickListener{

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mImageView = findViewById(R.id.welcome);
        mImageView.setOnClickListener(this);
        initData();
        toHome();
    }



    @Override
    protected WelcomeContract.View createIView() {
        return this;
    }

    @Override
    protected WelcomeContract.Presenter createPresenter() {
        return new WelcomePresenter(this);
    }

    @Override
    public void updateView(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
    }

    private void toHome() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                RouterCenter.toHomeActivity();
                finish();
            }
        }).start();
    }

    private void initData() {
        mPresenter.loadWelcomeImg();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.welcome:

                break;
        }
    }
}
