package com.example.app_mvvm;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_mvvm.BR;
import com.example.app_mvvm.viewmodel.TabVM;

import java.util.List;

public class ViewGroupAdapter {

    @BindingAdapter({"vm", "data"})
    public static void setDataTwoWay(final ViewGroup container, final TabVM vm, List<TabVM.Item> itemList) {
        if (vm == null) {
            return;
        }
        bind(container, vm, itemList);


    }

    private static void bind(ViewGroup viewGroup, TabVM vm, List<TabVM.Item> itemList) {
        if (vm == null)
            return;


        if (itemList != null && !itemList.isEmpty()) {
            viewGroup.removeAllViews();
            for (int pos = 0; pos < itemList.size(); pos++) {
                final Object viewModel = itemList.get(pos);
                final ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_main_tab, viewGroup, true);
                binding.setVariable(BR.item, viewModel);

                setListener(vm.getOnItemClick(), ViewHolder.createViewHolder(viewGroup.getContext(), binding.getRoot()), viewModel, pos);
            }
        }
    }

    private static void setListener(final OnItemClickListener onItemClickListener, final ViewHolder viewHolder, final Object data, final int pos) {
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(viewHolder, data, pos);
                }
            }
        });
    }


}
