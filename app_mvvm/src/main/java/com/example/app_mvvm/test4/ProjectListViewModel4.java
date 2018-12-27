package com.example.app_mvvm.test4;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.app_mvvm.test4.base.BaseViewModel;

public class ProjectListViewModel4 extends BaseViewModel<ProjectListRepository> {


    public ProjectListViewModel4(@NonNull Application application) {
        super(application);

    }

    public void loadProjectListData(String userId) {
        mRepository.loadProjectListData(userId);
    }

}
