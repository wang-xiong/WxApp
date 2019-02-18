package com.example.app_mvvm_livedata.test2;

public interface RequestMultiplyCallback<T> extends RequestCallback<T> {

    void onFail(BaseException e);
}
