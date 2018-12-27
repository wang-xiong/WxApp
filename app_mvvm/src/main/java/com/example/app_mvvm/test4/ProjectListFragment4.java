package com.example.app_mvvm.test4;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.app_mvvm.R;
import com.example.app_mvvm.test3.model.Project;
import com.example.app_mvvm.test3.repository.ProjectRepository;
import com.example.app_mvvm.test3.view.adapter.ProjectAdapter1;
import com.example.app_mvvm.test3.view.callback.ProjectClickCallback;
import com.example.app_mvvm.test3.view.ui.TestActivity3;
import com.example.app_mvvm.test4.base.BaseFragment;
import com.example.app_mvvm.test4.config.Constants;

import java.util.List;

public class ProjectListFragment4 extends BaseFragment<ProjectListViewModel4> {
    public static final String TAG = ProjectListFragment4.class.getSimpleName();

    private ProjectAdapter1 projectAdapter;
    private TextView loadingTip;
    private View contentView;


    public static ProjectListFragment4 newInstance() {
        return new ProjectListFragment4();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_project_list;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        RecyclerView recyclerView = rootView.findViewById(R.id.project_list);
        loadingTip = rootView.findViewById(R.id.loading_projects);
        contentView = rootView.findViewById(R.id.content_layout);
        setIsLoading(true);
        projectAdapter = new ProjectAdapter1(new ProjectClickCallback() {
            @Override
            public void onClick(Project project) {
//                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
//                    ((TestActivity3) getActivity()).show(project);
//                }

                MutableLiveData<List<Project>> data = registerObserver(Constants.EVENT_KEY_HOME);
                data.observe(ProjectListFragment4.this, new Observer<List<Project>>() {
                    @Override
                    public void onChanged(List<Project> projects) {
                        setIsLoading(false);
                        //projectAdapter.setProjectList(projects);
                    }
                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(projectAdapter);
    }

    @Override
    protected void dataObserver() {
        registerObserver(Constants.EVENT_KEY_HOME).observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                setIsLoading(false);
                projectAdapter.setProjectList(projects);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.loadProjectListData("Google");
    }

    private void setIsLoading(boolean isLoading) {
        loadingTip.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        contentView.setVisibility(!isLoading ? View.VISIBLE : View.GONE);
    }

    private MutableLiveData<List<Project>> registerObserver(String eventKey) {
        return registerObserver(eventKey, "");
    }

}
