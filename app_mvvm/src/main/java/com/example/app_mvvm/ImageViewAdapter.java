package com.example.app_mvvm;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

public class ImageViewAdapter {

    @BindingAdapter(value = {"uri", "placeholder", "onImageLoad"}, requireAll = false)
    public static void loadImage(ImageView imageView, String uri, @DrawableRes int placeholder){
        imageView.setImageResource(placeholder);

    }

    @BindingAdapter(value = {"uri", "placeholder", "radius"}, requireAll = false)
    public static void loadRoundImage(ImageView imageView, String uri, @DrawableRes int placeholder, float radius){
        imageView.setImageResource(placeholder);

    }
}
