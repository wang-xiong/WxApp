package com.example.rxjava_library.test;

/**
 * Created by wangxiong on 2018/8/10.
 */

public class RetrofitTestResult {
    private int status;

    private content content;

    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    public void show() {

    }
}
