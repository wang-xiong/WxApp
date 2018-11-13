package com.example.app_mvvm.View;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.app_mvvm.PersonAdapter;
import com.example.app_mvvm.R;
import com.example.app_mvvm.databinding.ActivityMainBinding;
import com.example.app_mvvm.model.Person;
import com.example.app_mvvm.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainViewModel.MainViewModelListener {
    private ActivityMainBinding activityMainBinding;
    private MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PersonAdapter adapter = new PersonAdapter(this, initData());
        recyclerView.setAdapter(adapter);*/

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this);
        activityMainBinding.setMainViewModel(mainViewModel);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mainViewModel.loadData();
    }

    @Override
    public void showRecyclerView(List<Person> dataList) {
        activityMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PersonAdapter adapter = new PersonAdapter(this, dataList);
        activityMainBinding.recyclerView.setAdapter(adapter);
    }
}
