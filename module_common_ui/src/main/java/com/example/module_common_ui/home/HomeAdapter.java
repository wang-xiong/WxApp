package com.example.module_common_ui.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.module_common_ui.R;
import com.example.module_common_ui.activity.AccountActivity;
import com.example.module_common_ui.activity.ConstraintActivity;
import com.example.module_common_ui.activity.CustomView2Activity;
import com.example.module_common_ui.activity.CustomViewActivity;
import com.example.module_common_ui.activity.DialogActivity;
import com.example.module_common_ui.activity.HorizontalScrollViewActivity;
import com.example.module_common_ui.activity.ShadowActivity;
import com.example.module_common_ui.activity.TestActivity;
import com.example.module_common_ui.popup.PopupActivity;
import com.example.module_common_ui.pull_to_refresh.ExpandListActivity;
import com.example.module_common_ui.pull_to_refresh.PullToRefreshActivity;
import com.example.module_common_ui.recycler.TagListActivity;

import java.util.List;

/**
 * Created by wangxiong on 2018/7/23.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<HomeBean> mList;

    public HomeAdapter(Context mContext, List<HomeBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_item, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.textView.setTag(mList.get(position));
        holder.textView.setText(mList.get(position).getTitle());
        holder.textView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        HomeBean homeBean = (HomeBean) v.getTag();
        if ("0".equals(homeBean.getType())) {
            Intent intent = new Intent(mContext, TagListActivity.class);
            mContext.startActivity(intent);
        } else if ("1".equals(homeBean.getType())){
            Intent intent = new Intent(mContext, PullToRefreshActivity.class);
            mContext.startActivity(intent);
        } else if ("2".equals(homeBean.getType())) {
            Intent intent = new Intent(mContext, ExpandListActivity.class);
            mContext.startActivity(intent);
        } else if ("3".equals(homeBean.getType())) {
            Intent intent = new Intent(mContext, PopupActivity.class);
            mContext.startActivity(intent);
        } else if ("4".equals(homeBean.getType())) {
            Intent intent = new Intent(mContext, AccountActivity.class);
            mContext.startActivity(intent);
        } else if ("5".equals(homeBean.getType())) {
            Intent intent = new Intent(mContext, DialogActivity.class);
            mContext.startActivity(intent);
        } else if ("6".equals(homeBean.getType())) {
            Intent intent = new Intent(mContext, ConstraintActivity.class);
            mContext.startActivity(intent);
        } else if ("7".equals(homeBean.getType())) {
            Intent intent = new Intent(mContext, HorizontalScrollViewActivity.class);
            mContext.startActivity(intent);
        } else if ("8".equals(homeBean.getType())) {
            Intent intent = new Intent(mContext, CustomViewActivity.class);
            mContext.startActivity(intent);
        } else if ("9".equals(homeBean.getType())) {
            Intent intent = new Intent(mContext, CustomView2Activity.class);
            mContext.startActivity(intent);
        } else if ("10".equals(homeBean.getType())) {
            Intent intent = new Intent(mContext, TestActivity.class);
            mContext.startActivity(intent);
        } else if ("11".equals(homeBean.getType())) {
            Intent intent = new Intent(mContext, ShadowActivity.class);
            mContext.startActivity(intent);
        }
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public HomeViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_des);
        }
    }
}
