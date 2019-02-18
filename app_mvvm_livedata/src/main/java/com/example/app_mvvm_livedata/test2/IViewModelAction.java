package com.example.app_mvvm_livedata.test2;

import android.arch.lifecycle.MutableLiveData;

public interface IViewModelAction {

    void startLoading();

    void startLoading(String message);

    void dismissLoading();

    void showToast(String message);

    void finish();

    void finishWithResultOk();

    MutableLiveData<BaseActionEvent> getActionLiveData();
}
