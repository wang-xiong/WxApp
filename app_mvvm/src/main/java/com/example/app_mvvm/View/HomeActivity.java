package com.example.app_mvvm.View;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.app_mvvm.R;
import com.example.app_mvvm.databinding.ActivityHomeBinding;
import com.example.app_mvvm.viewmodel.TabPagesVM;
import com.example.app_mvvm.viewmodel.TabVM;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActivityHomeBinding activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new ExploreFragment());
        fragmentList.add(new NotifyFragment());
        fragmentList.add(new ProfileFragment());
        activityHomeBinding.setPages(new TabPagesVM(fragmentList));

        ObservableList<TabVM.Item> tabItemList = new ObservableArrayList<>();
        tabItemList.add(new TabVM.Item(new ObservableBoolean(true), R.drawable.tab_home_normal, R.drawable.tab_home));
        tabItemList.add(new TabVM.Item(new ObservableBoolean(false), R.drawable.tab_explore_normal, R.drawable.tab_explore));
        tabItemList.add(new TabVM.Item(new ObservableBoolean(true), R.drawable.tab_notifications_normal, R.drawable.tab_notifications));
        tabItemList.add(new TabVM.Item(new ObservableBoolean(true), R.drawable.tab_profile_normal, R.drawable.tab_profile));
        activityHomeBinding.setTab(new TabVM(tabItemList));
    }
}
