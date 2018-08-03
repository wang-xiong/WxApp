package com.example.module_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.component_base.ARouter.RouterUrl;
import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.module_app.fragment.HomeFragment;
import com.example.module_app.fragment.HomePullToRefreshFragment;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUrl.PATH_MODULE_APP_MAIN)
public class MainActivity extends BaseMvcActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;

    private LinearLayout mBottomLayout;
    private TextView mBottomTabFirst;
    private TextView mBottomTabSecond;
    private TextView mBottomTabThird;
    private TextView mBottomTabFourth;

    private NavigationView mNavigationView;
    private TextView mHeaderLoginTv;
    private MenuItem mMenuItemWelfare;
    private MenuItem mMenuItemVideo;
    private MenuItem mMenuItemAboutUs;
    private MenuItem mMenuItemLogout;

    private List<Fragment> mFragmentList;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_app_activity_main);
        initView();
        initData();


    }

    private void initView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        initBottomView();
        initNavigationView();
    }

    private void initBottomView() {
        mBottomLayout = findViewById(R.id.bottom_layout);
        mBottomTabFirst = mBottomLayout.findViewById(R.id.bottom_tab_first);
        mBottomTabSecond = mBottomLayout.findViewById(R.id.bottom_tab_second);
        mBottomTabThird = mBottomLayout.findViewById(R.id.bottom_tab_third);
        mBottomTabFourth = mBottomLayout.findViewById(R.id.bottom_tab_fourth);
        setBottomSelected(mBottomTabFirst.getId());
        mBottomTabFirst.setOnClickListener(this);
        mBottomTabSecond.setOnClickListener(this);
        mBottomTabThird.setOnClickListener(this);
        mBottomTabFourth.setOnClickListener(this);
    }

    private void initNavigationView() {
        mNavigationView = findViewById(R.id.nav_view);
        mHeaderLoginTv = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_login_tv);
        mMenuItemWelfare = mNavigationView.getMenu().findItem(R.id.nav_item_welfare);
        mMenuItemVideo = mNavigationView.getMenu().findItem(R.id.nav_item_video);
        mMenuItemAboutUs = mNavigationView.getMenu().findItem(R.id.nav_item_about_us);
        mMenuItemLogout = mNavigationView.getMenu().findItem(R.id.nav_item_logout);

        //mBottomNavigationView = findViewById(R.id.bottom_nav_view);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        //BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);


        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(this);

        mHeaderLoginTv.setOnClickListener(this);
    }

    private void initData() {
        initFragmentList();
        showFragment(R.id.frame_layout, mFragmentList.get(0));
    }


    private void initFragmentList() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new HomePullToRefreshFragment());
    }

    private void setBottomSelected(int id) {
        if (R.id.bottom_tab_first == id) {
            mBottomTabFirst.setSelected(true);
            mBottomTabSecond.setSelected(false);
            mBottomTabThird.setSelected(false);
            mBottomTabFourth.setSelected(false);

        } else if (R.id.bottom_tab_second == id) {
            mBottomTabFirst.setSelected(false);
            mBottomTabSecond.setSelected(true);
            mBottomTabThird.setSelected(false);
            mBottomTabFourth.setSelected(false);
        } else if (R.id.bottom_tab_third == id) {
            mBottomTabFirst.setSelected(false);
            mBottomTabSecond.setSelected(false);
            mBottomTabThird.setSelected(true);
            mBottomTabFourth.setSelected(false);
        } else if (R.id.bottom_tab_fourth == id) {
            mBottomTabFirst.setSelected(false);
            mBottomTabSecond.setSelected(false);
            mBottomTabThird.setSelected(false);
            mBottomTabFourth.setSelected(true);

        }
    }

    @Override
    public void onClick(View v) {
        if (R.id.nav_header_login_tv == v.getId()) {
            //WLog.d(TAG, "点击了登录");
        } else if (R.id.bottom_tab_first == v.getId()) {
            setBottomSelected(v.getId());
        } else if (R.id.bottom_tab_second == v.getId()) {
            setBottomSelected(v.getId());
        } else if (R.id.bottom_tab_third == v.getId()) {
            setBottomSelected(v.getId());
        } else if (R.id.bottom_tab_fourth == v.getId()) {
            setBottomSelected(v.getId());
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item == mMenuItemWelfare) {
            //WLog.d(TAG, "点击了福利");
            RouterCenter.toWelfare();
            closeDrawer();
        } else if (item == mMenuItemVideo) {
            //WLog.d(TAG, "点击了视频");
            closeDrawer();
        } else if (item == mMenuItemAboutUs) {
            //WLog.d(TAG, "点击了关于我们");
            closeDrawer();
        } else if (item == mMenuItemLogout) {
            //WLog.d(TAG, "点击了退出");
            closeDrawer();
        }
        return true;
    }

    /**
     * 关闭侧滑
     */
    private void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void showFragment(int frameLayoutId, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()) {
            if (mCurrentFragment != null) {
                transaction.hide(mCurrentFragment).show(fragment);
            } else {
                transaction.show(fragment);
            }
        } else {
            if (mCurrentFragment != null) {
                transaction.hide(fragment).add(frameLayoutId, fragment);
            } else {
                transaction.add(frameLayoutId, fragment);
            }
        }
        mCurrentFragment = fragment;
        transaction.commit();
    }
}
