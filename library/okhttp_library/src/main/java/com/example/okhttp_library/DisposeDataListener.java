package com.example.okhttp_library;

/**
 * Created by wangxiong on 2018/7/19.
 */

public interface DisposeDataListener {
    void onSuccess(Object responseObj);

    void onFailure(Object responseObj);
}
