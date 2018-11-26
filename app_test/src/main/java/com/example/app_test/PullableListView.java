package com.example.app_test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class PullableListView extends ListView implements IPullToRefresh {
    private boolean canPullDown = true;
    public PullableListView(Context context) {
        super(context);
    }
    public PullableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public PullableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setCanPullDown(boolean canPullDown) {
        this.canPullDown = canPullDown;
    }
    @Override
    public boolean canPullDown() {
        View firstView = getChildAt(0);
        return canPullDown && firstView != null && firstView.getTop() == 0;
    }

    @Override
    public boolean canPullUp() {
        int position = getLastVisiblePosition();
        return position == getCount() -1;
    }
}
