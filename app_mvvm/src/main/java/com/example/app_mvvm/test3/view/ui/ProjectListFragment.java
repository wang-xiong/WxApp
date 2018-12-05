package com.example.app_mvvm.test3.view.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_mvvm.R;
import com.example.app_mvvm.databinding.FragmentProjectListBinding;
import com.example.app_mvvm.test3.model.Project;
import com.example.app_mvvm.test3.repository.GitHubService;
import com.example.app_mvvm.test3.repository.ProjectRepository;
import com.example.app_mvvm.test3.view.adapter.ProjectAdapter;
import com.example.app_mvvm.test3.view.callback.ProjectClickCallback;
import com.example.app_mvvm.test3.viewmodel.ProjectListViewModel;
import com.example.app_mvvm.test3.viewmodel.ProjectViewModelFactory;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectListFragment extends Fragment {
    public static final String TAG = "ProjectListFragment";

    private ProjectAdapter projectAdapter;
    private FragmentProjectListBinding binding;

    private ProjectRepository projectRepository;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.HTTPS_API_GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService gitHubService = retrofit.create(GitHubService.class);
        projectRepository = new ProjectRepository(gitHubService);

        ViewModelProvider.Factory factory = new ProjectViewModelFactory(projectRepository, getActivity().getApplication());
        final ProjectListViewModel viewModel = ViewModelProviders.of(this, factory).get(ProjectListViewModel.class);
        viewModel.getProjectListObservable().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> projects) {
                if (projects != null) {
                    binding.setIsLoading(false);
                    projectAdapter.setProjectList(projects);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false);

        projectAdapter = new ProjectAdapter(new ProjectClickCallback() {
            @Override
            public void onClick(Project project) {
                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    ((TestActivity3) getActivity()).show(project);
                }
            }
        });
        binding.projectList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.projectList.setAdapter(projectAdapter);
        binding.setIsLoading(true);
        return binding.getRoot();
    }

}
