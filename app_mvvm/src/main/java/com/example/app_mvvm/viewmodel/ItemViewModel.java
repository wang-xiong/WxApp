package com.example.app_mvvm.viewmodel;

import android.content.Context;
import android.database.Observable;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.Toast;

import com.example.app_mvvm.model.Person;

public class ItemViewModel {

    private Context context;
    public Person person;
//    public ObservableField<String> itemName = new ObservableField<>();
//    public ObservableField<String> itemDesc = new ObservableField<>();

    public ItemViewModel(Context context, Person person) {
        this.context = context;
        this.person = person;
//        itemName.set(person.getName());
//        itemDesc.set(person.getDesc());


    }

    public void onClickItem(View view) {
        person.setName("lisi");
        Toast.makeText(context, "点击了Item", Toast.LENGTH_SHORT).show();
    }
}
