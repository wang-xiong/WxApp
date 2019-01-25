package com.example.app_mvvm_livedata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment implements ProjectClickCallback {

    public static final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.loading_projects)
    ProgressBar loadingTip;

    @BindView(R.id.project_list)
    RecyclerView mRecyclerView;

    private HomeViewModel mViewModel;
    private HomeProjectAdapter projectAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_project_list, container, false);
        ButterKnife.bind(this, rootView);
        setIsLoading(true);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        projectAdapter = new HomeProjectAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(projectAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.loadProjectListData("Google").observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> projects) {
                projectAdapter.setProjectList(projects);
            }
        });
        mViewModel.getIsShowLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean showLoading) {
                if (showLoading != null) {
                    setIsLoading(showLoading);
                }
            }
        });
    }

    private void setIsLoading(boolean isLoading) {
        loadingTip.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.refresh)
    void refresh() {
        mViewModel.loadProjectListData("Google");
    }

    @OnClick(R.id.call)
    void call() {
        mViewModel.loadProjectListDataByRx("Google");
    }

    @OnClick(R.id.cancel)
    void cancel() {
        mViewModel.cancel();
    }

    @Override
    public void onClick(Project project) {

    }
}
