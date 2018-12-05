package com.example.app_mvvm.test3.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.app_mvvm.test3.model.Project;
import com.example.app_mvvm.test3.repository.ProjectRepository;

import java.util.List;

public class ProjectListViewModel extends AndroidViewModel {

    private final LiveData<List<Project>> projectListObservable;

    public ProjectListViewModel(ProjectRepository projectRepository, @NonNull Application application) {
        super(application);
        // If any transformation is needed, this can be simply done by Transformations class ...
        projectListObservable = projectRepository.getProjectList("Google");
    }

    public LiveData<List<Project>> getProjectListObservable() {
        return projectListObservable;
    }
}
