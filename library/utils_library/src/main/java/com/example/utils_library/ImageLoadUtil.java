package com.example.utils_library;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by wangxiong on 2018/7/22.
 */

public class ImageLoadUtil {
    private static final int IMG_LOADING = R.drawable.ic_image_loading;
    private static final int IMG_ERROR = R.drawable.ic_empty_picture;

    public static void load(Context context, String url, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(IMG_LOADING)  //加载中显示的图片
                .centerCrop()            //图像则位于视图的中央
                // .override(1090, 1090*3/4)
                .error(IMG_ERROR) //加载失败时显示的图片centerCrop().
                .diskCacheStrategy(DiskCacheStrategy.ALL);  //图片缓存
        Glide.with(context).load(url).into(imageView);
    }
}
