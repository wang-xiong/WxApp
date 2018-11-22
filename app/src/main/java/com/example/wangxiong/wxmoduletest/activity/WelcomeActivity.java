package com.example.wangxiong.wxmoduletest.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.component_base.base.mvp.BaseMvpPermissionActivity;
import com.example.utils_library.permission.PermissionHelper;
import com.example.utils_library.permission.PermissionUtil;
import com.example.wangxiong.wxmoduletest.R;
import com.example.wangxiong.wxmoduletest.arouter.RouterCenter;
import com.example.wangxiong.wxmoduletest.contract.WelcomeContract;
import com.example.wangxiong.wxmoduletest.presenter.WelcomePresenter;

public class WelcomeActivity extends BaseMvpPermissionActivity<WelcomeContract.View, WelcomeContract.Presenter> implements WelcomeContract.View
        , View.OnClickListener {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mImageView = findViewById(R.id.welcome);
        mImageView.setOnClickListener(this);

        if (PermissionUtil.hasAllPermission(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE})) {
            initData();
            toHome();
        } else {
            requestPermission(new String[]{Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE}, new PermissionHelper.PermissionInterface() {
                @Override
                public int getRequestCode() {
                    return 1111;
                }

                @Override
                public void onRequestPermissionSuccess() {
                    initData();
                    toHome();
                }
            });
        }
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
