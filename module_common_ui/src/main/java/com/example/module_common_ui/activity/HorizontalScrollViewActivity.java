package com.example.module_common_ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.module_common_ui.R;
import com.example.module_common_ui.view.scrollview.HorizontalScrollViewAdapter;
import com.example.module_common_ui.view.scrollview.MyHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiong on 2018/8/14.
 */

public class HorizontalScrollViewActivity extends BaseMvcActivity {
    private TextView mShowTv;
    private MyHorizontalScrollView mHorizontalScrollView;
    private List<String> mData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_ui_activity_horizontal);
        initData();
        initView();
    }

    private void initView() {
        mShowTv = findViewById(R.id.show_tv);
        mHorizontalScrollView = findViewById(R.id.horizontal_scrollview);
        HorizontalScrollViewAdapter adapter = new HorizontalScrollViewAdapter(this, mData);
        mHorizontalScrollView.setAdapter(adapter);
        mHorizontalScrollView.setCurrentImageChangeListener(new MyHorizontalScrollView.CurrentImageChangeListener() {
            @Override
            public void onCurrentImgChanged(int position, View viewIndicator) {
                mShowTv.setText(mData.get(position));
                viewIndicator.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });

        mHorizontalScrollView.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(HorizontalScrollViewActivity.this,"点击了：" + mData.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        mData = new ArrayList<>();
        mData.add("wx1");
        mData.add("wx2");
        mData.add("wx3");
        mData.add("wx4");
        mData.add("wx5");
        mData.add("wx6");
        mData.add("wx7");
        mData.add("wx8");
        mData.add("wx9");
        mData.add("wx10");
        mData.add("wx11");
        mData.add("wx12");
        mData.add("wx13");
        mData.add("wx14");
        mData.add("wx15");
        mData.add("wx16");
        mData.add("wx17");
        mData.add("wx18");
        mData.add("wx19");
        mData.add("wx20");
    }
}
