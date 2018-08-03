package com.example.module_welfare.fragement;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.component_base.base.mvp.BaseMvpFragment;
import com.example.module_welfare.R;
import com.example.module_welfare.WelfareConstant;
import com.example.module_welfare.adapter.WelfareAdapter;
import com.example.module_welfare.arouter.RouterCenter;
import com.example.module_welfare.bean.PhotoGirlBean;
import com.example.module_welfare.bean.PreviewBean;
import com.example.module_welfare.contract.WelfareContract;
import com.example.module_welfare.presenter.WelfarePresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangxiong on 2018/7/20.
 */
public class WelfareSecondFragment extends BaseMvpFragment<WelfareContract.View, WelfareContract.Presenter> implements WelfareContract.View , WelfareAdapter.WelfareAdapterListener {
    private RecyclerView mRecyclerView;
    private WelfareAdapter mWelfareAdapter;
    private List<PhotoGirlBean.ResultsBean> mResults;
    private ArrayList<PreviewBean> mPreviewBeanList;

    public WelfareSecondFragment() {
        // Required empty public constructor
    }

    public static WelfareSecondFragment newInstance() {
        WelfareSecondFragment fragment = new WelfareSecondFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_welfare;
    }

    @Override
    protected void intView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
    }

    @Override
    protected void initData() {
        mResults = new ArrayList<>();
        mPreviewBeanList = new ArrayList<>();
        mWelfareAdapter = new WelfareAdapter(getContext(), mResults);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mWelfareAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        loadData();
    }

    @Override
    protected WelfareContract.View createIView() {
        return this;
    }

    @Override
    protected WelfareContract.Presenter createPresenter() {
        return new WelfarePresenter();
    }

    private void loadData() {
        mPresenter.loadData(WelfareConstant.URL_WELFARE_2);
    }

    @Override
    public void refreshView(final PhotoGirlBean photoGirlBean) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mResults.clear();
                mResults.addAll(photoGirlBean.getResults());
                mWelfareAdapter.setOnClickItemListener(WelfareSecondFragment.this);
                mWelfareAdapter.notifyDataSetChanged();
                setPhotoList();
            }
        });
    }

    private void setPhotoList() {
        for (PhotoGirlBean.ResultsBean resultsBean : mResults) {
            PreviewBean previewBean = new PreviewBean(resultsBean.getUrl());
            mPreviewBeanList.add(previewBean);
        }
    }

    @Override
    public void onClickWelfareAdapter(int position) {
        RouterCenter.toImagePreview(mPreviewBeanList, position);
    }
}
