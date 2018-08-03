package com.example.module_common_ui.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.module_common_ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiong on 2018/7/25.
 */

public class TagListAdapter extends RecyclerView.Adapter<TagListAdapter.TagListViewHolder>{
    private List<TagBean> mDatList;
    private List<TagBean> mSelectList;

    public TagListAdapter(Context mContext, List<TagBean> mDatList) {
        this.mDatList = mDatList;
        this.mSelectList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TagListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_list_item, parent,false);
        return new TagListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TagListViewHolder holder, final int position) {
        holder.tagTv.setText(mDatList.get(position).getTagName());
        holder.tagTv.setOnClickListener(new View.OnClickListener() {
            private boolean isSelected = false;
            @Override
            public void onClick(View v) {
                //取反
                isSelected = !holder.tagTv.isSelected();
                if (isSelected) {
                    holder.tagTv.setSelected(true);
                    holder.tagTv.setBackgroundResource(R.drawable.tag_checked_bg);
                    mSelectList.add(mDatList.get(position));
                } else {
                    holder.tagTv.setSelected(false);
                    holder.tagTv.setBackgroundResource(R.drawable.tag_normal_bg);
                    mSelectList.remove(mDatList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatList.size();
    }

    public List<TagBean> getSelectedTag() {
        return mSelectList;
    }

    class TagListViewHolder extends RecyclerView.ViewHolder{
        TextView tagTv;

        public TagListViewHolder(View itemView) {
            super(itemView);
            tagTv = itemView.findViewById(R.id.tag_tv);
        }
    }

}




