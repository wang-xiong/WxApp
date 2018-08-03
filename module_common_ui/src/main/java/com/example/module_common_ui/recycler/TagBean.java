package com.example.module_common_ui.recycler;

/**
 * Created wangxiong
 *
 */

public class TagBean {

    private String tagId;
    private String tagName;

    public TagBean(String tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
