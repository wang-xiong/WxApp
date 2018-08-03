package com.example.module_welfare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.module_welfare.R;
import com.example.module_welfare.bean.PhotoGirlBean;

import java.util.List;

/**
 * Created by wangxiong on 2018/7/20.
 */

public class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.MyViewHolder> {
    private Context mContext;
    private List<PhotoGirlBean.ResultsBean> mResults;
    private WelfareAdapterListener mListener;

    public WelfareAdapter(Context mContext, List<PhotoGirlBean.ResultsBean> mResults) {
        this.mContext = mContext;
        this.mResults = mResults;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(mContext).inflate(R.layout.module_welfare_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        String url = mResults.get(position).getUrl();
        RequestOptions options = new RequestOptions()
                //.placeholder(optionsInit(0))  //加载中显示的图片
                .centerCrop()            //图像则位于视图的中央
                // .override(1090, 1090*3/4)
                //.error(IMG_ERROR) //加载失败时显示的图片centerCrop().
                .diskCacheStrategy(DiskCacheStrategy.ALL);  //图片缓存
        Glide.with(mContext).load(url).apply(options).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClickWelfareAdapter(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
        }
    }

    public void setOnClickItemListener(WelfareAdapterListener listener) {
        this.mListener = listener;
    }

    public  interface WelfareAdapterListener {
        void onClickWelfareAdapter(int position);
    }
}
