package com.example.module_common_ui.view.alert;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.module_common_ui.R;

import java.util.List;

/**
 * Created by wangxiong on 2018/7/30.
 */

public class AlertViewAdapter extends BaseAdapter {
    private List<String> mData;
    private List<String> mDestructive;

    public AlertViewAdapter(List<String> mData, List<String> mDestructive) {
        this.mData = mData;
        this.mDestructive = mDestructive;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alertbutton, parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mData.get(position));
        if (mDestructive!= null && mDestructive.contains(mData.get(position))){
            holder.textView.setTextColor(parent.getContext().getResources().getColor(R.color.textColor_alert_button_destructive));
        }
        else{
            holder.textView.setTextColor(parent.getContext().getResources().getColor(R.color.textColor_alert_button_others));
        }
        return convertView;
    }

    class ViewHolder {
        private TextView textView;

        public ViewHolder(View view) {
            this.textView = view.findViewById(R.id.tvAlert);
        }
    }
}
