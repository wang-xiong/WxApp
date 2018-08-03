package com.example.module_common_ui.pull_to_refresh;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.module_common_ui.R;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PullToRefreshActivity extends BaseMvcActivity {
    private PullToRefreshListView mPullToRefreshListView;
    private List<String> mData;
    private ArrayAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_ui_activity_pull_to_refresh);
        mPullToRefreshListView = findViewById(R.id.pull_to_refresh_list_view);
        mData = new ArrayList<>();
        loadData();
        mListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mData);
        mPullToRefreshListView.setAdapter(mListAdapter);

        // 1.实现下拉刷新
        /*mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                new MyAsyncTask().execute();
            }
        });*/

        //2.实现下拉和上拉舒刷新，
        //2.1设置监听事件
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new MyAsyncTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new MyAsyncTask().execute();
            }
        });

        //2.2设置下拉刷新模式
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        //设置自定义下拉刷新动画文字
        ILoadingLayout headerLayout = mPullToRefreshListView.getLoadingLayoutProxy(true, false);
        headerLayout.setPullLabel("向下拖动完成刷新");
        headerLayout.setRefreshingLabel("正在加载。。。");
        headerLayout.setRefreshingLabel("释放完成刷新");

        //设置自定义上拉刷新动画文字
        ILoadingLayout footLayout = mPullToRefreshListView.getLoadingLayoutProxy(false, true);
        footLayout.setPullLabel("向上拽动完成刷新...");
        footLayout.setRefreshingLabel("正在疯刷新数据...");
        footLayout.setReleaseLabel("松开完成刷新...");
        //footLayout.setLoadingDrawable(getResources().getDrawable(R.drawable.default_ptr_flip));
    }

    private int count = 1;
    private void loadData() {
        for (int i=0; i<10; i++) {
            mData.add(String.format(Locale.CHINA, "模拟第%d条数据", count));
            count ++;
        }
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loadData();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (success) {
                //异步调用pullToRefreshListView.onRefreshComplete是会消失的，同步调用头部动画不会消失。
                mPullToRefreshListView.onRefreshComplete();
            }
        }
    }
}
