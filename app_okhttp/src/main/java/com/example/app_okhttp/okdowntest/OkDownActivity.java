package com.example.app_okhttp.okdowntest;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.app_okhttp.R;
import com.example.app_okhttp.okdown.OkDown;
import com.example.app_okhttp.okdown.callback.DownloadCallback;
import com.example.app_okhttp.okdown.download.DownloadManager;
import com.example.app_okhttp.okdown.utils.Utils;

import java.io.File;

public class OkDownActivity extends AppCompatActivity {

    /**
     * http://1.198.5.23/imtt.dd.qq.com/16891/B8723A0DB2F2702C04D801D9FD19822C.apk //阴阳师
     * http://1.82.215.170/imtt.dd.qq.com/16891/85B6221DE84C466310575D9FBCA453A8.apk  //天天酷跑
     * http://1.198.5.22/imtt.dd.qq.com/16891/8EEC7D8996760973B5CEA15ECA1700E3.apk  //消消乐
     */

    private TextView mTip;
    private TextView mProgress;
    private TextView mPause;
    private TextView mResume;
    private TextView mCancel;
    private TextView mRestart;
    private ProgressBar progressBar;

    private Context mContext;

    private String url;

    private DownloadManager downloadManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_down);

        mContext = this;

        mTip = (TextView) findViewById(R.id.tip);
        mProgress = (TextView) findViewById(R.id.progress);
        mPause = (TextView) findViewById(R.id.pause);
        mResume = (TextView) findViewById(R.id.resume);
        mCancel = (TextView) findViewById(R.id.cancel);
        mRestart = (TextView) findViewById(R.id.restart);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()

        final String name = "消消乐";
        url = "http://1.198.5.22/imtt.dd.qq.com/16891/8EEC7D8996760973B5CEA15ECA1700E3.apk";
        String url2 = "http://orp6z38cm.bkt.clouddn.com/107117018";




        downloadManger = OkDown.init(mContext)
                .url(url2)
                .path(Environment.getExternalStorageDirectory() + "/OkDown/")
                .name(name + ".mp4")
                .childTaskCount(3)
                .build()
                .start(new DownloadCallback() {

                    @Override
                    public void onStart(long currentSize, long totalSize, float progress) {
                        mTip.setText(name + "：准备下载中...");
                        progressBar.setProgress((int) progress);
                        mProgress.setText(Utils.formatSize(currentSize) + " / " + Utils.formatSize(totalSize) + "--------" + progress + "%");
                    }

                    @Override
                    public void onProgress(long currentSize, long totalSize, float progress) {
                        mTip.setText(name + "：下载中...");
                        progressBar.setProgress((int) progress);
                        mProgress.setText(Utils.formatSize(currentSize) + " / " + Utils.formatSize(totalSize) + "--------" + progress + "%");
                    }

                    @Override
                    public void onPause() {
                        mTip.setText(name + "：暂停中...");
                    }

                    @Override
                    public void onCancel() {
                        mTip.setText(name + "：已取消...");
                    }

                    @Override
                    public void onFinish(File file) {}

                    @Override
                    public void onWait() {}

                    @Override
                    public void onError(String error) {
                        mTip.setText(name + "：下载出错...");
                    }
                });

    }
}
