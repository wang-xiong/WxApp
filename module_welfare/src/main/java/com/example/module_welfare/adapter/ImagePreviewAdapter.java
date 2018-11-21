package com.example.module_welfare.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.module_welfare.R;
import com.example.module_welfare.bean.PreviewBean;
import com.example.utils_library.ImageLoadUtil;

import java.util.List;

/**
 * Created by wangxiong on 2018/7/22.
 */

public class ImagePreviewAdapter extends PagerAdapter implements View.OnClickListener{
    private Context mContext;
    private List<PreviewBean> mPreviewBeanList;

    public ImagePreviewAdapter(Context mContext, List<PreviewBean> mPreviewBeanList) {
        this.mContext = mContext;
        this.mPreviewBeanList = mPreviewBeanList;
    }

    @Override
    public int getCount() {
        return mPreviewBeanList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_preview_item, container, false);
        ImageView imageView = view.findViewById(R.id.iv_photo);
        ProgressBar progressBar = view.findViewById(R.id.pb_photo);

        imageView.setOnClickListener(this);
        ImageLoadUtil.load(mContext, mPreviewBeanList.get(position).getUrlString(), imageView);
        container.addView(view);
        return view;
    }

    /**
     * PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() ==R.id.iv_photo) {

        }
    }
}
