package com.example.app_mvvm.test2.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;

import com.example.app_mvvm.test2.View.listener.OnItemClickListener;


public class TabVM {

    public ObservableInt curIndex = new ObservableInt(0);

    public ObservableList<Item> itemList = new ObservableArrayList<>();

    public OnItemClickListener<Item> onItemClick = new OnItemClickListener<Item>() {
        @Override
        public void onItemClick(RecyclerView.ViewHolder holder, Item data, int position) {
            for (Item item : itemList) {
                if (item == data) {
                    item.isSelected.set(true);
                } else {
                    item.isSelected.set(false);
                }
            }
            curIndex.set(position);
        }
    };

    public OnItemClickListener<Item> getOnItemClick() {
        return onItemClick;
    }

    public TabVM(ObservableList<Item> data) {
        itemList.clear();
        itemList.addAll(data);
    }

    public static class Item {
        public ObservableBoolean isSelected;
        public int iconResNormal;
        public int iconResSelected;

        public Item(ObservableBoolean isSelected, int iconResNormal, int iconResSelected) {
            this.isSelected = isSelected;
            this.iconResNormal = iconResNormal;
            this.iconResSelected = iconResSelected;
        }
    }

}
