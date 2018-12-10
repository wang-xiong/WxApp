package com.example.app_okhttp.okdown.download;

import android.content.Context;

/**
 * Created by hongkl on 17/8/21.
 */
public class OkDownBuilder {
    private String url;//下载链接
    private String path;//保存路径
    private String name;//文件名
    private int childTaskCount;//单个任务采用几个线程下载

    private Context context;

    public OkDownBuilder url(String url) {
        this.url = url;
        return this;
    }

    public OkDownBuilder path(String path) {
        this.path = path;
        return this;
    }

    public OkDownBuilder name(String name) {
        this.name = name;
        return this;
    }

    public OkDownBuilder childTaskCount(int childTaskCount) {
        this.childTaskCount = childTaskCount;
        return this;
    }

    public OkDownBuilder(Context context) {
        this.context = context.getApplicationContext();
    }

    public DownloadManager build() {
        DownloadManager downloadManager = DownloadManager.getInstance(context);

        downloadManager.init(url,path,name,childTaskCount);

        return downloadManager;
    }


}
