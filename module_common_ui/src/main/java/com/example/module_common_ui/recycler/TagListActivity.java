package com.example.module_common_ui.recycler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.module_common_ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiong on 2018/7/25.
 */

public class TagListActivity extends AppCompatActivity {
    private PullRefreshRecyclerView mRecyclerView;
    private TagListAdapter mTagListAdapter;
    private List<TagBean> mTagBeanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_list);
        initView();
        initData();
        setRecycler();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rv_tag_list);
    }

    private void initData() {
        mTagBeanList = new ArrayList<>();
        mTagBeanList.add(new TagBean("1","准时"));
        mTagBeanList.add(new TagBean("2","非常绅士"));
        mTagBeanList.add(new TagBean("3","非常有礼貌"));
        mTagBeanList.add(new TagBean("4","很会照顾女生"));
        mTagBeanList.add(new TagBean("5","我的男神是个大暖男哦"));
        mTagBeanList.add(new TagBean("6","谈吐优雅"));
        mTagBeanList.add(new TagBean("7","迟到"));
        mTagBeanList.add(new TagBean("8","态度恶劣"));
        mTagBeanList.add(new TagBean("9","有不礼貌行为"));
        mTagBeanList.add(new TagBean("10","有侮辱性语言有暴力倾向"));
        mTagBeanList.add(new TagBean("11","人身攻击"));
        mTagBeanList.add(new TagBean("12","临时改变行程"));
        mTagBeanList.add(new TagBean("13","客户迟到并无理要求延长约会时间"));
        mTagBeanList.add(new TagBean("1","准时"));
        mTagBeanList.add(new TagBean("2","非常绅士"));
        mTagBeanList.add(new TagBean("3","非常有礼貌"));
        mTagBeanList.add(new TagBean("4","很会照顾女生"));
        mTagBeanList.add(new TagBean("5","我的男神是个大暖男哦"));
        mTagBeanList.add(new TagBean("6","谈吐优雅"));
        mTagBeanList.add(new TagBean("7","迟到"));
        mTagBeanList.add(new TagBean("8","态度恶劣"));
        mTagBeanList.add(new TagBean("9","有不礼貌行为"));
        mTagBeanList.add(new TagBean("10","有侮辱性语言有暴力倾向"));
        mTagBeanList.add(new TagBean("11","人身攻击"));
        mTagBeanList.add(new TagBean("12","临时改变行程"));
        mTagBeanList.add(new TagBean("13","客户迟到并无理要求延长约会时间"));
        mTagBeanList.add(new TagBean("1","准时"));
        mTagBeanList.add(new TagBean("2","非常绅士"));
        mTagBeanList.add(new TagBean("3","非常有礼貌"));
        mTagBeanList.add(new TagBean("4","很会照顾女生"));
        mTagBeanList.add(new TagBean("5","我的男神是个大暖男哦"));
        mTagBeanList.add(new TagBean("6","谈吐优雅"));
        mTagBeanList.add(new TagBean("7","迟到"));
        mTagBeanList.add(new TagBean("8","态度恶劣"));
        mTagBeanList.add(new TagBean("9","有不礼貌行为"));
        mTagBeanList.add(new TagBean("10","有侮辱性语言有暴力倾向"));
        mTagBeanList.add(new TagBean("11","人身攻击"));
        mTagBeanList.add(new TagBean("12","临时改变行程"));
        mTagBeanList.add(new TagBean("13","客户迟到并无理要求延长约会时间"));
    }

    private void setRecycler() {
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//        //setSpanSizeLookup 用法 spanCount/spanSize = 每行的item
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (mTagBeanList.get(position).getTagName().length() > 9) {
//                    return 2;
//                }
//                return 1;
//            }
//        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTagListAdapter = new TagListAdapter(this, mTagBeanList);
        mRecyclerView.setAdapter(mTagListAdapter);
        mRecyclerView.setOnRefreshListener(new PullRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //mRecyclerView.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                //mRecyclerView.stopLoadMore();
            }
        });
//        HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper(mTagListAdapter);
//        TextView textView = new TextView(this);
//        textView.setText("header 1");
//
//        TextView textView2 = new TextView(this);
//        textView2.setText("header 2");
//
//        TextView textView3 = new TextView(this);
//        textView3.setText("footer 1");
//        headerAndFooterWrapper.addHeaderView(textView);
//        headerAndFooterWrapper.addHeaderView(textView2);
//        headerAndFooterWrapper.addFooterView(textView3);
//
//        mRecyclerView.setAdapter(headerAndFooterWrapper);
    }

}