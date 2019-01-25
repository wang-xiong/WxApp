package com.example.app_mvvm_livedata;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private HomeRepository homeRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        homeRepository = new HomeRepository(application);
    }

    public LiveData<Boolean> getIsShowLoading() {
        return homeRepository.getIsShowLoading();
    }

    public LiveData<List<Project>> loadProjectListData(String google) {
        return homeRepository.getProjectList(google);
    }

    public void loadProjectListDataByRx(String google) {
        homeRepository.loadProjectListDataByRx(google);
    }

    public void cancel() {
        homeRepository.clear();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        homeRepository.clear();
    }
}
