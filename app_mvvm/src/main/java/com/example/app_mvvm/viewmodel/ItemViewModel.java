package com.example.app_mvvm.viewmodel;

import android.content.Context;
import android.database.Observable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_mvvm.R;
import com.example.app_mvvm.model.Person;

import java.net.URL;

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

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getName() {
        return person.getName();
    }

    public int getImgId() {
        return R.mipmap.ic_launcher;
    }

    @BindingAdapter({"resId"})
    public static void setImg(ImageView imageView, int resId) {
        imageView.setImageResource(resId);
    }

    public void onClickItem(View view) {
        person.setName("lisi");
        Toast.makeText(context, "点击了Item", Toast.LENGTH_SHORT).show();
    }
}
