package com.example.app_mvvm.test3.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.app_mvvm.test3.repository.ProjectRepository;

public class ProjectViewModelFactory implements ViewModelProvider.Factory {

    private ProjectRepository projectRepository;
    private Application application;

    public ProjectViewModelFactory(ProjectRepository projectRepository, Application application) {
        this.projectRepository = projectRepository;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(ProjectListViewModel.class)) {
            return (T) new ProjectListViewModel(projectRepository, application);
        } else if (modelClass.isAssignableFrom(ProjectViewModel.class)) {
            return (T) new ProjectViewModel(projectRepository, application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
