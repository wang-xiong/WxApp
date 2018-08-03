package com.example.module_common_ui.home;

/**
 * Created by wangxiong on 2018/7/23.
 */

public class HomeBean {
    private String type;
    private String title;

    public HomeBean(String type, String title) {
        this.type = type;
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
