package com.example.homemodule.test.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.component_base.ARouter.RouterUrl;
import com.example.component_base.base.mvp.BaseMvpActivity;
import com.example.homemodule.R;
import com.example.homemodule.test.contract.MvpTestContract;
import com.example.homemodule.test.presenter.MvpTestPresenter;

@Route(path = RouterUrl.PATH_MODULE_MVP_TEST)
public class MvpTestActivity extends BaseMvpActivity<MvpTestContract.View, MvpTestContract.Presenter> implements MvpTestContract.View, View.OnClickListener {
    private Button mHomeButton;
    private Button mOwnerButton;

    @Override
    protected MvpTestContract.View createIView() {
        return this;
    }

    @Override
    protected MvpTestContract.Presenter createPresenter() {
        return new MvpTestPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_home_activity_mvptest);
        intView();
        initData();

    }


    private void initData() {
        mPresenter.loadHomeData();
    }

    private void intView() {
        mHomeButton = findViewById(R.id.home);
        mOwnerButton = findViewById(R.id.owner);
        mHomeButton.setOnClickListener(this);
        mOwnerButton.setOnClickListener(this);
    }

    @Override
    public void updateView(String firstTab, String secondTab) {
        mHomeButton.setText(firstTab);
        mOwnerButton.setText(secondTab);
    }

    @Override
    public void onClick(View v) {
    }
}
