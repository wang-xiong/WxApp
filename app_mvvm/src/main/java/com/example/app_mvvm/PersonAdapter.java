package com.example.app_mvvm;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_mvvm.databinding.PersonItemBinding;
import com.example.app_mvvm.model.Person;
import com.example.app_mvvm.viewmodel.ItemViewModel;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.Holder> {

    private Context mContext;
    private List<Person> mDataList;

    public PersonAdapter(Context mContext, List<Person> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PersonItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.person_item, parent, false);
        Holder holder = new Holder(itemBinding.getRoot());
        holder.setItemBinding(itemBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
//        holder.name.setText(mDataList.get(position).getName());
////        holder.desc.setText(mDataList.get(position).getDesc());
        if (holder.getItemBinding().getItemViewModel() == null) {
            ItemViewModel itemViewModel = new ItemViewModel(mContext, mDataList.get(position));
            holder.getItemBinding().setItemViewModel(itemViewModel);
        } else {
            holder.getItemBinding().getItemViewModel().setPerson(mDataList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private PersonItemBinding itemBinding;
//        private TextView name;
//        private TextView desc;

        public Holder(View itemView) {
            super(itemView);
//            name = itemView.findViewById(R.id.item_name);
//            desc = itemView.findViewById(R.id.item_desc);
        }

        public PersonItemBinding getItemBinding() {
            return itemBinding;
        }

        public void setItemBinding(PersonItemBinding itemBinding) {
            this.itemBinding = itemBinding;
        }
    }
}
