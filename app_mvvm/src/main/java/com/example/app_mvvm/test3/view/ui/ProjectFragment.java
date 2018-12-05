package com.example.app_mvvm.test3.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_mvvm.R;
import com.example.app_mvvm.databinding.FragmentProjectDetailsBinding;
import com.example.app_mvvm.test3.model.Project;
import com.example.app_mvvm.test3.repository.GitHubService;
import com.example.app_mvvm.test3.repository.ProjectRepository;
import com.example.app_mvvm.test3.viewmodel.ProjectViewModel;
import com.example.app_mvvm.test3.viewmodel.ProjectViewModelFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectFragment extends Fragment {
    private static final String KEY_PROJECT_ID = "project_id";

    private FragmentProjectDetailsBinding binding;

    private ProjectRepository projectRepository;


    public static ProjectFragment forProject(String projectID) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();

        args.putString(KEY_PROJECT_ID, projectID);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_details, container, false);
        return binding.getRoot();
    }

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
        final ProjectViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(ProjectViewModel.class);

        viewModel.setProjectID(getArguments().getString(KEY_PROJECT_ID));

        binding.setProjectViewModel(viewModel);
        binding.setIsLoading(true);

        observeViewModel(viewModel);
    }

    private void observeViewModel(final ProjectViewModel viewModel) {

        viewModel.getObservableProject().observe(this, new Observer<Project>() {
            @Override
            public void onChanged(@Nullable Project project) {
                if (project != null) {
                    binding.setIsLoading(false);
                    viewModel.setProject(project);
                }
            }
        });
    }
}
