package com.example.module_app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.module_app.R;
import com.example.module_app.bean.HomeArticleData;

import java.util.List;

/**
 * Created by wangxiong on 2018/7/31.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private Context mContext;
    private List<HomeArticleData> mListData;

    public HomeAdapter(Context mContext, List<HomeArticleData> mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_app_home_item, parent, false);
        return new HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        HomeArticleData feedArticleData = mListData.get(position);
        if (feedArticleData.isCollect()) {
            holder.likeImg.setImageResource(R.drawable.icon_like_article_selected);
        } else {
            holder.likeImg.setImageResource(R.drawable.icon_like_article_not_selected);
        }
        if (!TextUtils.isEmpty(feedArticleData.getTitle())) {
            holder.titleTv.setText(feedArticleData.getTitle());
        }
        if (!TextUtils.isEmpty(feedArticleData.getAuthor())) {
            holder.authorTv.setText(mContext.getString(R.string.mall_item_article_author, feedArticleData.getAuthor()));
        }

        if (!TextUtils.isEmpty(feedArticleData.getChapterName())) {
            holder.categoryTv.setText(mContext.getString(R.string.mall_item_article_classify, feedArticleData.getChapterName()));
        }
        if (!TextUtils.isEmpty(feedArticleData.getNiceDate())) {
            holder.dateTv.setText(mContext.getString(R.string.mall_item_article_time, feedArticleData.getNiceDate()));
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageView likeImg;
        TextView titleTv;
        TextView authorTv;
        TextView categoryTv;
        TextView dateTv;

        public HomeViewHolder(View itemView) {
            super(itemView);
            likeImg = itemView.findViewById(R.id.item_home_like_iv);
            titleTv = itemView.findViewById(R.id.item_home_title);
            authorTv = itemView.findViewById(R.id.item_home_author);
            categoryTv = itemView.findViewById(R.id.item_home_chapterName);
            dateTv = itemView.findViewById(R.id.item_home_niceDate);

        }
    }
}
