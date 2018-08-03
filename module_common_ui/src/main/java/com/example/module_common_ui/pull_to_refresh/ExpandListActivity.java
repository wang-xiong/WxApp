package com.example.module_common_ui.pull_to_refresh;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.module_common_ui.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiong on 2018/7/23.
 */

public class ExpandListActivity extends BaseMvcActivity {
    private PullToRefreshExpandableListView mPullToRefreshExpandableListView;
    private ExpandableListView mExpandableListView;
    private List<GroupBean> mGroupBeanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_ui_activity_expand);

        mPullToRefreshExpandableListView = findViewById(R.id.pl_list_view);
        mExpandableListView = mPullToRefreshExpandableListView.getRefreshableView();

        mExpandableListView.setGroupIndicator(null);
        mExpandableListView.setDivider(null);
        mExpandableListView.setSelector(android.R.color.transparent);



        mPullToRefreshExpandableListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ExpandableListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ExpandableListView> pullToRefreshBase) {

            }
        });

        initData();

        mExpandableListView.setAdapter(new MyExpandableListAdapter(this, mGroupBeanList ));

        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

    }

    private void initData() {
        mGroupBeanList = new ArrayList<>();
        List<GroupBean.ChildBean> childBeanList = new ArrayList<>();
        GroupBean.ChildBean childBean1 = new GroupBean.ChildBean("苹果");
        GroupBean.ChildBean childBean2 = new GroupBean.ChildBean("香蕉");
        GroupBean.ChildBean childBean3= new GroupBean.ChildBean("葡萄");
        childBeanList.add(childBean1);
        childBeanList.add(childBean2);
        childBeanList.add(childBean3);
        mGroupBeanList.add(new GroupBean("水果",childBeanList));
    }
}
