package com.example.app_mvvm.test2.View.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Created by jinliangshan on 17/4/27.
 */
public abstract class OnItemClickListener<T> {
    public abstract void onItemClick(RecyclerView.ViewHolder holder, T data, int position);

    public boolean onItemLongClick(RecyclerView.ViewHolder holder, T data, int position) {
        return false;
    }
}
