package com.example.app_test;

public interface IPullToRefresh {
    boolean canPullDown(); // 判断当前是否可以下拉的方法
    boolean canPullUp();
}
