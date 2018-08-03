package com.example.module_welfare.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.component_base.ARouter.RouterUrl;
import com.example.module_welfare.R;
import com.example.module_welfare.bean.WelfareFragmentBean;
import com.example.module_welfare.fragement.WelfareFirstFragment;
import com.example.module_welfare.fragement.WelfareFragment;
import com.example.module_welfare.fragement.WelfareSecondFragment;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUrl.PATH_MODULE_WELFARE_WELFARE)
public class WelfareActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TextView mTabTv1;
    private TextView mTabTv2;
    private List<WelfareFragmentBean> mWelfareFragmentBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare);

        iniView();
        initData();
    }

    private void iniView() {
        mViewPager = findViewById(R.id.view_pager);
        mTabTv1 = findViewById(R.id.top_tab_tv1);
        mTabTv2 = findViewById(R.id.top_tab_tv2);
    }
    private void initData() {
        mWelfareFragmentBeanList = new ArrayList<>();
        mWelfareFragmentBeanList.add(new WelfareFragmentBean("靓妹", WelfareFirstFragment.newInstance()));
        mWelfareFragmentBeanList.add(new WelfareFragmentBean("丑妹", WelfareSecondFragment.newInstance()));
        mViewPager.setAdapter(new WelfareAdapter(getSupportFragmentManager()));
        mTabTv1.setText(mWelfareFragmentBeanList.get(0).getTitle());
        mTabTv2.setText(mWelfareFragmentBeanList.get(1).getTitle());
    }

    private class WelfareAdapter extends FragmentPagerAdapter {

        public WelfareAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mWelfareFragmentBeanList.get(position).getFragment();
        }

        @Override
        public int getCount() {
            return mWelfareFragmentBeanList.size();
        }
    }
}
