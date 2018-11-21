package com.example.module_common_ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.component_base.ARouter.RouterUrl;
import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.module_common_ui.R;
import com.example.utils_library.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiong on 2018/7/23.
 */

@Route(path = RouterUrl.PATH_COMMON_UI_HOME)
public class HomeActivity extends BaseMvcActivity{
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;

    private List<HomeBean> mJsonList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_ui_activity_home);
        initData();
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.home_ry);
        mAdapter = new HomeAdapter(this, mJsonList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        String json = JsonUtil.getJsonFromFile(this, "customControl.json");
        mJsonList = new Gson().fromJson(json, new TypeToken<ArrayList<HomeBean>>(){}.getType());
    }
}
