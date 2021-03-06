package com.example.app_okhttp.okdown.download;

import android.content.Context;

import com.example.app_okhttp.okdown.callback.DownloadCallback;
import com.example.app_okhttp.okdown.data.DownloadData;

import java.util.HashMap;
import java.util.Map;

import static com.example.app_okhttp.okdown.data.Consts.ERROR;
import static com.example.app_okhttp.okdown.data.Consts.FINISH;
import static com.example.app_okhttp.okdown.data.Consts.NONE;
import static com.example.app_okhttp.okdown.data.Consts.PAUSE;


/**
 * Created by hongkl on 17/8/21.
 */
public class DownloadManager {

    private Map<String, DownloadProgressHandler> progressHandlerMap = new HashMap<>();//保存任务的进度处理对象
    private Map<String, DownloadData> downloadDataMap = new HashMap<>();//保存任务数据
    private Map<String, DownloadCallback> callbackMap = new HashMap<>();//保存任务回调
    private Map<String, FileTask> fileTaskMap = new HashMap<>();//保存下载线程

    private Context mContext;
    private volatile static DownloadManager downloadManager;
    private DownloadData downloadData;

    public static DownloadManager getInstance(Context context){
        if(downloadManager == null){
            synchronized (DownloadManager.class){
                if(downloadManager == null){
                    downloadManager = new DownloadManager(context);
                }
            }
        }
        return downloadManager;
    }

    private DownloadManager(Context context){this.mContext = context;}

    public synchronized void init(String url,String path,String name,int childTaskCount){
        downloadData = new DownloadData();
        downloadData.setUrl(url);
        downloadData.setPath(path);
        downloadData.setName(name);
        downloadData.setChildTaskCount(childTaskCount);
    }


    /**
     * 链式开启下载
     *
     * @param downloadCallback
     * @return
     */
    public DownloadManager start(DownloadCallback downloadCallback) {
        execute(downloadData, downloadCallback);
        return downloadManager;
    }

    /****
     * 根据URL开始下载
     * @param downloadData
     * @param downloadCallback
     * @return
     */
    public DownloadManager start(DownloadData downloadData,DownloadCallback downloadCallback){
        execute(downloadData,downloadCallback);
        return downloadManager;
    }

    /**
     * 根据url开始下载（需先注册监听）
     *
     * @param url
     */
    public DownloadManager start(String url) {
        execute(downloadDataMap.get(url), callbackMap.get(url));
        return downloadManager;
    }

    /**
     * 暂停
     *
     * @param url
     */
    public void pause(String url) {
        if (progressHandlerMap.containsKey(url))
            progressHandlerMap.get(url).pause();
    }

    /**
     * 继续
     *
     * @param url
     */
    public void resume(String url) {
        if (progressHandlerMap.containsKey(url) &&
                (progressHandlerMap.get(url).getCurrentState() == PAUSE ||
                        progressHandlerMap.get(url).getCurrentState() == ERROR)) {
            progressHandlerMap.remove(url);
            execute(downloadDataMap.get(url), callbackMap.get(url));
        }
    }


    /**
     * 重新开始
     *
     * @param url
     */
    public void restart(String url) {
        //文件已下载完成的情况
        if (progressHandlerMap.containsKey(url) && progressHandlerMap.get(url).getCurrentState() == FINISH) {
            progressHandlerMap.remove(url);
            fileTaskMap.remove(url);
            innerRestart(url);
            return;
        }

        //任务已经取消，则直接重新下载
        if (!progressHandlerMap.containsKey(url)) {
            innerRestart(url);
        } else {
            innerCancel(url, true);
        }
    }

    /**
     * 退出时释放资源
     *
     * @param url
     */
    public void destroy(String url) {
        if (progressHandlerMap.containsKey(url)) {
            progressHandlerMap.get(url).destroy();
            progressHandlerMap.remove(url);
            callbackMap.remove(url);
            downloadDataMap.remove(url);
            fileTaskMap.remove(url);
        }
    }

    public void destroy(String... urls) {
        if (urls != null) {
            for (String url : urls) {
                destroy(url);
            }
        }
    }

    /***
     * 下载
     * @param downloadData
     * @param downloadCallback
     */
    private synchronized void execute(DownloadData downloadData,DownloadCallback downloadCallback){
        if(progressHandlerMap.get(downloadData.getUrl()) != null){
            return;
        }

        if(downloadData.getChildTaskCount() == 0){
            downloadData.setChildTaskCount(1);
        }

        DownloadProgressHandler progressHandler = new DownloadProgressHandler(mContext, downloadData, downloadCallback);
        FileTask fileTask = new FileTask(mContext, downloadData, progressHandler.getHandler());
        progressHandler.setFileTask(fileTask);

        downloadDataMap.put(downloadData.getUrl(),downloadData);
        callbackMap.put(downloadData.getUrl(),downloadCallback);
        fileTaskMap.put(downloadData.getUrl(),fileTask);
        progressHandlerMap.put(downloadData.getUrl(),progressHandler);

        ThreadPool.getInstance().getThreadPoolExecutor().execute(fileTask);

        if(ThreadPool.getInstance().getThreadPoolExecutor().getActiveCount() == ThreadPool.getInstance().getCorePoolSize()){
            downloadCallback.onWait();
        }

    }

    /**
     * 实际的重新下载操作
     *
     * @param url
     */
    protected void innerRestart(String url) {
        execute(downloadDataMap.get(url), callbackMap.get(url));
    }

    /**
     * 取消
     *
     * @param url
     */
    public void cancel(String url) {
        innerCancel(url, false);
    }

    public void innerCancel(String url, boolean isNeedRestart) {
        if (progressHandlerMap.get(url) != null) {
            if (progressHandlerMap.get(url).getCurrentState() == NONE) {
                //取消缓存队列中等待下载的任务
                ThreadPool.getInstance().getThreadPoolExecutor().remove(fileTaskMap.get(url));
                callbackMap.get(url).onCancel();
            } else {
                //取消已经开始下载的任务
                progressHandlerMap.get(url).cancel(isNeedRestart);
            }
            progressHandlerMap.remove(url);
            fileTaskMap.remove(url);
        }
    }


    /**
     * 注册监听
     *
     * @param downloadData
     * @param downloadCallback
     */
    public synchronized void setOnDownloadCallback(DownloadData downloadData, DownloadCallback downloadCallback) {
        downloadDataMap.put(downloadData.getUrl(), downloadData);
        callbackMap.put(downloadData.getUrl(), downloadCallback);
    }


}
