package com.example.module_common_ui.pull_to_refresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.module_common_ui.R;

import java.util.List;

/**
 * Created by wangxiong on 2018/7/23.
 */

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<GroupBean> mGroupBeanList;

    public MyExpandableListAdapter(Context mContext, List<GroupBean> mGroupBeanList) {
        this.mContext = mContext;
        this.mGroupBeanList = mGroupBeanList;
    }

    @Override
    public int getGroupCount() {
        return mGroupBeanList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroupBeanList.get(groupPosition).getChildList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupBeanList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroupBeanList.get(groupPosition).getChildList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.common_ui_expand_group_item, parent, false);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.textView.setText(mGroupBeanList.get(groupPosition).getGroupName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.expand_child_item, parent, false);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.textView.setText(mGroupBeanList.get(groupPosition).getChildList().get(childPosition).getChildName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        private TextView textView;

        public GroupViewHolder(View view) {
            this.textView = view.findViewById(R.id.tv_group_name);
        }
    }

    class ChildViewHolder {
        private TextView textView;

        public ChildViewHolder(View view) {
            this.textView = view.findViewById(R.id.tv_child_name);
        }
    }
}
