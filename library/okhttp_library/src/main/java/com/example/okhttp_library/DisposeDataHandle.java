package com.example.okhttp_library;

/**
 * Created by wangxiong on 2018/7/19.
 */

public class DisposeDataHandle {

    public DisposeDataListener mListener;
    public Class<?> mClass;

    public DisposeDataHandle(DisposeDataListener mListener) {
        this.mListener = mListener;
    }

    public DisposeDataHandle(DisposeDataListener mListener, Class<?> clazz) {
        this.mListener = mListener;
        this.mClass = clazz;
    }
}
