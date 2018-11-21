package com.example.module_common_ui.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.module_common_ui.view.alert.AlertView;
import com.example.module_common_ui.view.alert.OnItemClickListener;
import com.example.utils_library.AppUtil;
import com.example.utils_library.WLog;

/**
 * Created by wangxiong on 2018/7/30.
 */

public class ImageAlertView extends AlertView implements OnItemClickListener<AlertView> {
    private static final String TAG = ImageAlertView.class.getSimpleName();
    private Context mContext;

    public static final int REQUEST_CODE_IMAGE_FROM_ALBUM = 10001;

    private String[] permission = new String[] {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    public ImageAlertView(Context context) {
        super(new Builder(context)
                .setCancel("取消")
                .setStyle(Style.ActionSheet)
                .setOthers(new String[]{"拍照", "从相册选取"})
                );
        this.mContext = context;
        super.onItemClickListener = this;
    }

    @Override
    public void onItemClick(AlertView alertView, int position) {
        if (position == 0) {
            //拍照
            WLog.i(TAG, "点击了拍照");
            //openCamera();
        } else if (position == 1) {
            //选择照片
            WLog.d(TAG, "点击从相册选取");
            selectImage();
        }
    }

    private void selectImage() {
        AppUtil.startAlbum((Activity) mContext, REQUEST_CODE_IMAGE_FROM_ALBUM);
    }

    public void onActivityResult(int resultCode, Intent data) {
        if (resultCode == REQUEST_CODE_IMAGE_FROM_ALBUM) {
            Uri uri = data.getData();

        }
    }
}
