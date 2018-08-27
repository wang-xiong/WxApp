package com.example.module_common_ui.view.scrollview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.module_common_ui.R;

import java.util.List;

/**
 * Created by wangxiong on 2018/8/15.
 */

public class HorizontalScrollViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mData;

    public HorizontalScrollViewAdapter(Context mContext, List<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.common_ui_horizontal_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mData.get(position));
        return convertView;
    }

    private class ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            imageView = itemView.findViewById(R.id.item_img);
            textView = itemView.findViewById(R.id.item_tv);
        }
    }
}
