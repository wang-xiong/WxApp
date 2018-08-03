package com.example.module_common_ui.pull_to_refresh;

import java.util.List;

/**
 * Created by wangxiong on 2018/7/23.
 */

public class GroupBean {
    private String groupName;
    private List<ChildBean> childList;

    public GroupBean(String groupName, List<ChildBean> childList) {
        this.groupName = groupName;
        this.childList = childList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<ChildBean> getChildList() {
        return childList;
    }

    public void setChildList(List<ChildBean> childList) {
        this.childList = childList;
    }

    public static class ChildBean {
        private String childName;

        public ChildBean(String childName) {
            this.childName = childName;
        }

        public String getChildName() {
            return childName;
        }

        public void setChildName(String childName) {
            this.childName = childName;
        }
    }
}
