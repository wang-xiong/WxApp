package com.example.okhttp_library.Test;

import java.util.List;

/**
 * Created by wangxiong on 2018/7/19.
 */

public class TestModel {
    private String ecode;
    private String emsg;
    private List<Person> data;


    private class Person {
        private String id;
        private String name;
    }
}
