package com.example.app_okhttp.okdowntest;


interface OnItemChildClickListener<T> {
    void onItemChildClick(final DownloadListAdapter.ViewHolder viewHolder, final T data, int position);
}
