package com.example.app_mvvm.test3.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.app_mvvm.test3.model.Project;
import com.example.app_mvvm.test3.repository.ProjectRepository;

public class ProjectViewModel extends AndroidViewModel {

    private static final String TAG = ProjectViewModel.class.getName();

    private static final MutableLiveData ABSENT = new MutableLiveData();
    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }

    private final LiveData<Project> projectObservable;
    private final MutableLiveData<String> projectID;

    public ObservableField<Project> project = new ObservableField<>();

    public ProjectViewModel(@NonNull final ProjectRepository projectRepository, Application application) {
        super(application);
        this.projectID = new MutableLiveData<>();
        projectObservable = Transformations.switchMap(projectID, new Function<String, LiveData<Project>>() {
            @Override
            public LiveData<Project> apply(String input) {
                if (input.isEmpty()) {
                    Log.i(TAG, "ProjectViewModel projectID is absent!!!");
                    return ABSENT;
                }
                Log.i(TAG,"ProjectViewModel projectID is " + projectID.getValue());
                return projectRepository.getProjectDetails("Google", projectID.getValue());
            }
        });
    }

    public void setProject(Project project) {
        this.project.set(project);
    }

    public void setProjectID(String projectID) {
        this.projectID.setValue(projectID);
    }

    public LiveData<Project> getObservableProject() {
        return projectObservable;
    }
}
