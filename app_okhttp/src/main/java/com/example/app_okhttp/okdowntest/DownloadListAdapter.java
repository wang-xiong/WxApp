package com.example.app_okhttp.okdowntest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.app_okhttp.R;
import com.example.app_okhttp.okdown.callback.DownloadCallback;
import com.example.app_okhttp.okdown.data.DownloadData;
import com.example.app_okhttp.okdown.download.DownloadManager;
import com.example.app_okhttp.okdown.utils.Utils;

import java.io.File;
import java.util.List;

public class DownloadListAdapter extends RecyclerView.Adapter<DownloadListAdapter.ViewHolder> {
    private Context mContext;
    private List<DownloadData> dataList;
    private ViewHolder viewHolder;


    public DownloadListAdapter(Context context, List<DownloadData> data, boolean isOpenLoadMore) {
        //super(context, datas, isOpenLoadMore);
        this.mContext = context;
        this.dataList = data;
    }

    private void setListener(final DownloadListAdapter.ViewHolder viewHolder, final DownloadData downloadData) {
        DownloadManager.getInstance(mContext).setOnDownloadCallback(downloadData, new DownloadCallback() {
            @Override
            public void onStart(long currentSize, long totalSize, float progress) {
                viewHolder.setText(R.id.name, downloadData.getName() + ":准备中");
            }

            @Override
            public void onProgress(long currentSize, long totalSize, float progress) {
                viewHolder.setText(R.id.name, downloadData.getName() + ":下载中");
                viewHolder.setText(R.id.download_size, Utils.formatSize(currentSize) + "/" + Utils.formatSize(totalSize));
                viewHolder.setText(R.id.percentage, progress + "%");
                ((ProgressBar) viewHolder.getView(R.id.progress_bar)).setProgress((int) progress);
            }

            @Override
            public void onPause() {
                viewHolder.setText(R.id.name, downloadData.getName() + ":已暂停");
            }

            @Override
            public void onCancel() {
                viewHolder.setText(R.id.name, downloadData.getName() + ":已取消");
            }

            @Override
            public void onFinish(File file) {
                viewHolder.setText(R.id.name, downloadData.getName() + ":已完成");
            }

            @Override
            public void onWait() {
                viewHolder.setText(R.id.name, downloadData.getName() + ":等待中");
            }

            @Override
            public void onError(String error) {
                viewHolder.setText(R.id.name, downloadData.getName() + ":出错");
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_download_layout, viewGroup, false);
        this.viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DownloadData data = dataList.get(position);
        holder.setText(R.id.name, data.getName());
        holder.setText(R.id.download_size, Utils.formatSize(data.getCurrentLength()) + "/" + Utils.formatSize(data.getTotalLength()));
        holder.setText(R.id.percentage, data.getPercentage() + "%");
        ((ProgressBar) holder.getView(R.id.progress_bar)).setProgress((int) data.getPercentage());

        holder.getView(R.id.start).setTag(position);
        holder.getView(R.id.pause).setTag(position);
        holder.getView(R.id.resume).setTag(position);
        holder.getView(R.id.cancel).setTag(position);
        holder.getView(R.id.restart).setTag(position);

        setListener(holder, data);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setOnItemChildClickListener(int id, final OnItemChildClickListener<DownloadData> onItemChildClickListener) {
        viewHolder.getView(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChildClickListener.onItemChildClick(viewHolder, dataList.get((Integer) v.getTag()), (Integer) v.getTag());
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void setText(int id, String str) {
            TextView textView = itemView.findViewById(id);
            textView.setText(str);
        }

        public View getView(int id) {
            return itemView.findViewById(id);
        }
    }
}
