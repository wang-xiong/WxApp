package com.example.app_test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.example.utils_library.AlarmManagerUtils;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    PullToRefreshLayout pullToRefreshLayout;
    PullableListView contentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] data = new String[] {"Apple", "Banana", "Orange", "Watermelon",
                "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango",
                "Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape"
                , "Pineapple", "Strawberry", "Cherry", "Mango" };
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refreshLayout);
        contentList = (PullableListView) findViewById(R.id.plvContent);
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data);
        contentList.setAdapter(adapter);
        pullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                //refreshData();
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 3000);

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 3000);
            }
        });



//        AlarmManagerUtils alarmManagerUtils = AlarmManagerUtils.getInstance(this);
//        alarmManagerUtils.createGetUpAlarmManager();
//        alarmManagerUtils.getUpAlarmManagerStartWork();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public void refreshData() {
        // 刷新数据
        //lvAdapter.notifyDataSetChanged();
    }
}
