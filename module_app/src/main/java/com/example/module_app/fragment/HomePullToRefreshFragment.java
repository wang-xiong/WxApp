package com.example.module_app.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.component_base.base.mvp.BaseMvpFragment;
import com.example.module_app.R;
import com.example.module_app.activity.HomeDetailActivity;
import com.example.module_app.adapter.HomeAdapter;
import com.example.module_app.adapter.HomePullToRefreshAdapter;
import com.example.module_app.bean.HomeArticleData;
import com.example.module_app.bean.HomeData;
import com.example.module_app.contract.HomeContract;
import com.example.module_app.presenter.HomePresenter;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiong on 2018/7/31.
 */

public class HomePullToRefreshFragment extends BaseMvpFragment<HomeContract.View, HomeContract.Presenter> implements HomeContract.View {
    private PullToRefreshListView mListView;
    private HomePullToRefreshAdapter mHomeAdapter;
    private List<HomeArticleData> mListData = new ArrayList<>();
    private Handler mHomeHandler;

    @Override
    protected int getLayoutId() {
        return R.layout.module_app_fragment_home_pull;
    }

    @Override
    protected void intView(View rootView) {
        mListView = rootView.findViewById(R.id.home_pull_lv);
        mHomeAdapter = new HomePullToRefreshAdapter(getContext(), mListData);
        mListView.setAdapter(mHomeAdapter);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                mPresenter.refreshArticleList();
            }
        });
        mListView.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position = position -mListView.getRefreshableView().getHeaderViewsCount();
                HomeDetailActivity.startHomeDetailActivity(getContext(), mListData.get(position).getTitle(), mListData.get(position).getLink());
            }
        });
        //设置自定义下拉刷新动画文字
        ILoadingLayout headerLayout = mListView.getLoadingLayoutProxy(true, false);
        headerLayout.setPullLabel("向下拖动完成刷新");
        headerLayout.setRefreshingLabel("正在加载。。。");
        headerLayout.setRefreshingLabel("释放完成刷新");
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
                    mListView.onRefreshComplete();
                }
            });

        }
    }

    @Override
    public void refreshData(final HomeData data) {
        if(data != null && data.getData() != null && data.getData().getDatas() != null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mHomeHandler.post(new Runnable() {
                @Override
                public void run() {
                    mListData.clear();
                    mListData.addAll(data.getData().getDatas());
                    //mHomeAdapter.notifyDataSetChanged();
                    mListView.onRefreshComplete();
                }
            });

        }
    }
}
