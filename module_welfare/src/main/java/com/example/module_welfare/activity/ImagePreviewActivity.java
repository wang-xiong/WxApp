package com.example.module_welfare.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.component_base.ARouter.RouterIntentKV;
import com.example.component_base.ARouter.RouterUrl;
import com.example.component_base.base.mvc.BaseMvcPermissionActivity;
import com.example.module_welfare.R;
import com.example.module_welfare.adapter.ImagePreviewAdapter;
import com.example.module_welfare.bean.PreviewBean;
import com.example.okhttp_library.DisposeDataListener;
import com.example.okhttp_library.RequestCenter;
import com.example.utils_library.FilelDownload;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;


@Route(path = RouterUrl.PATH_MODULE_WELFARE_IMAGE_PREVIEW)
public class ImagePreviewActivity extends BaseMvcPermissionActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private TextView mPager;
    private TextView mSave;

    private ImagePreviewAdapter mImagePreviewAdapter;
    private List<PreviewBean> mPreviewBeanList;
    private int mItemNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        mPreviewBeanList = getIntent().getParcelableArrayListExtra(RouterIntentKV.KEY_WELFARE_PHOTO);
        mItemNum = getIntent().getIntExtra(RouterIntentKV.KEY_WELFARE_POSITION, 0) + 1;

        initView();
        initData();
    }

    private void initView() {
        mViewPager = findViewById(R.id.vp_image);
        mPager = findViewById(R.id.tv_pager);
        mSave = findViewById(R.id.tv_save);

        mSave.setOnClickListener(this);
    }

    private void initData() {
        mImagePreviewAdapter = new ImagePreviewAdapter(this, mPreviewBeanList);
        mViewPager.setAdapter(mImagePreviewAdapter);
        mViewPager.setCurrentItem(mItemNum - 1);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mItemNum = position + 1;
                mPager.setText(mItemNum + "/" + mPreviewBeanList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPager.setText(mItemNum + "/" + mPreviewBeanList.size());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_save) {
            //检测权限
            if (!getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSION_STORAGE)) {
                return;
            }
            //下载图片
            downLoad(mPreviewBeanList.get(mItemNum - 1).getUrlString());
        }
    }

    private void downLoad(String urlString) {
        RequestCenter.requestPhotoData(urlString, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                if (responseObj instanceof Object) {
                    Response response = (Response) responseObj;
                    save(response.body());
                }

            }

            @Override
            public void onFailure(Object responseObj) {
            }
        });
    }

    private void save(ResponseBody responseBody) {
        long timeMillis = System.currentTimeMillis();
        String str = String.valueOf(timeMillis);
        String pictureName = str.substring(str.length() - 6, str.length());
        String fileName = "wx" + pictureName + ".jpg";

        //1.保存到本地
        File fileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "girl");
        FilelDownload.saveFile(responseBody
                , Environment.getExternalStorageDirectory().getAbsolutePath() + "/girl/",
                fileName);

        String path = Environment.getExternalStorageDirectory() + "/girl/" + fileName;

        //2.插入系统图库
        try {
            MediaStore.Images.Media.insertImage(this.getContentResolver()
                    , path, fileName, null);
        } catch (FileNotFoundException e) {
            //插入图库失败
            e.printStackTrace();
        }

        //3.通知图库更新
        this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));

        Log.e("wx", "fileName:" + fileName);
        Log.e("wx", "fileDir.getAbsolutePath():" + fileDir.getAbsolutePath());
        Log.e("wx", "url:" + path);
        Log.e("wx", "url:" + Uri.parse("file://" + path));

//          fileName:wx184763.jpg
//          fileDir.getAbsolutePath():/storage/emulated/0/girl
//          url:/storage/emulated/0/girl/wx184763.jpg
//          url:file:///storage/emulated/0/girl/wx184763.jpg
    }
}
