package com.example.app_mvvm_livedata.test2;

public class BaseRepo<T> {

    protected T remoteDataSource;

    public BaseRepo(T remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }
}
