package com.example.module_welfare.fragement;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.module_welfare.WelfareConstant;
import com.example.module_welfare.bean.PhotoGirlBean;
import com.example.module_welfare.R;
import com.example.module_welfare.adapter.WelfareAdapter;
import com.example.okhttp_library.DisposeDataListener;
import com.example.okhttp_library.RequestCenter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangxiong on 2018/7/20.
 * 普通Fragment
 */
public class WelfareFragment extends Fragment {
    private static final String ARG_PARAM = "type";
    private String mType;
    private List<PhotoGirlBean.ResultsBean> mResults;


    private OnFragmentInteractionListener mListener;

    public WelfareFragment() {
        // Required empty public constructor
    }

    /**
     * 创建实例
     *
     * @param param
     * @return
     */
    public static WelfareFragment newInstance(String param) {
        WelfareFragment fragment = new WelfareFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(ARG_PARAM);
        }
        mResults = new ArrayList<>();
    }

    private RecyclerView mRecyclerView;
    private WelfareAdapter mWelfareAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welfare, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mWelfareAdapter = new WelfareAdapter(getContext(), mResults);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mWelfareAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        loadData();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void loadData() {
        RequestCenter.requestWelfareData(WelfareConstant.URL_WELFARE_1, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                PhotoGirlBean photoGirlBean = (PhotoGirlBean) responseObj;
                mResults.addAll(photoGirlBean.getResults());
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mWelfareAdapter.notifyDataSetChanged();
                        Log.e("wx","mResults:" + mResults.size() + "" + mResults.get(0).getUrl());
                    }
                });

            }

            @Override
            public void onFailure(Object responseObj) {

            }
        }, PhotoGirlBean.class);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
