package com.example.module_app.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.component_base.base.mvp.BaseMvpFragment;
import com.example.module_app.adapter.HomeAdapter;
import com.example.module_app.presenter.HomePresenter;
import com.example.module_app.R;
import com.example.module_app.bean.HomeArticleData;
import com.example.module_app.bean.HomeData;
import com.example.module_app.contract.HomeContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiong on 2018/7/31.
 */

public class HomeFragment extends BaseMvpFragment<HomeContract.View, HomeContract.Presenter> implements HomeContract.View {
    private RecyclerView mRecyclerView;
    private HomeAdapter mHomeAdapter;
    private List<HomeArticleData> mListData = new ArrayList<>();
    private Handler mHomeHandler;

    @Override
    protected int getLayoutId() {
        return R.layout.module_app_fragment_home;
    }

    @Override
    protected void intView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.home_rv);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(this.getContext()));
        mHomeAdapter = new HomeAdapter(getContext(), mListData);
        mRecyclerView.setAdapter(mHomeAdapter);
        mHomeHandler = new Handler();
    }

    @Override
    protected HomeContract.View createIView() {
        return this;
    }

    @Override
    protected HomeContract.Presenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void initData() {
        mPresenter.loadArticleList();
    }

    @Override
    public void update(final HomeData data) {
        if(data != null && data.getData() != null && data.getData().getDatas() != null) {
            mHomeHandler.post(new Runnable() {
                @Override
                public void run() {
                    mListData.addAll(data.getData().getDatas());
                    mHomeAdapter.notifyDataSetChanged();
                }
            });

        }
    }

    @Override
    public void refreshData(HomeData data) {

    }
}
