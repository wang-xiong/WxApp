package com.example.lib_mvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.app_mvvm.test4.utils.ViewModelUtil;
import com.example.lib_mvvm.utils.ViewModelUtil;

public class AbsViewModel<R extends AbsRepository> extends AndroidViewModel {

    protected R mRepository;

    public AbsViewModel(@NonNull Application application) {
        super(application);
        mRepository = ViewModelUtil.getNewInstance(this, 0);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRepository != null) {
            mRepository.onCleared();
        }
    }
}
