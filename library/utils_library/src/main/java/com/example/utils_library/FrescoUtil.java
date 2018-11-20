package com.example.utils_library;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;

import okhttp3.OkHttpClient;

public class FrescoUtil {


    /**
     * Fresco开源地址：https://github.com/facebook/fresco
     * Fresco文档地址：https://www.fresco-cn.org
     * 1.使用的控件SimpleDraweeView
     * 不支持wrap_content
     */

    public static void initFresco(Application application) {
//                ImagePipelineConfig config  = ImagePipelineConfig.newBuilder(this)
//                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(this)
//                        .setMaxCacheSize(100*1024*1024)
//                        .setBaseDirectoryPath(new File(""))
//                        .build())
//                .build();
        //普通初始化
        Fresco.initialize(application);
        //高级初始化
        Fresco.initialize(application, OkHttpImagePipelineConfigFactory.newBuilder(application, new OkHttpClient())
                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(application)
                        .setMaxCacheSize(100 * 1024 * 1024)
                        .setBaseDirectoryPath(new File(""))
                        .build())
                .build());
    }

    /**
     * 支持的uil格式列表:
     * 1.Http/Https
     * 2.本地文件file://
     * 3.ContentProvider
     * 4.res目录下的资源
     * 5.assert目录下的资源
     * 6.Uri中制定的图片数据
     */

    //加载http/https网络图片
    public void loadImageFromUrl(SimpleDraweeView simpleDraweeView, String url) {
        simpleDraweeView.setImageURI(url);
    }

    //加载本地图片
    private void loadImageFromFile(SimpleDraweeView draweeView, String path, int width, int height) {
        Uri uri = Uri.parse("file://" + path);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(draweeView.getController())
                .setImageRequest(request)
                .build();
        draweeView.setController(controller);
    }

    //加载本地图片
    private void loadImageFromFile(SimpleDraweeView draweeView, String path) {
        Uri uri = Uri.parse("file://" + path);
        draweeView.setImageURI(uri);
    }

    private void loadImageFromRes(SimpleDraweeView draweeView, int resId) {
        //Uri uri = Uri.parse("res://包名（任何字符串或者留空）" + resId);
        Uri uri = Uri.parse("res:///" + resId); //一般使用
        draweeView.setImageURI(uri);
    }

    private void loadImageFromContentProvider(SimpleDraweeView draweeView, String patch) {
        Uri uri = Uri.parse("content://" + patch);
        draweeView.setImageURI(uri);
    }

    private void loadImageFromAsset(SimpleDraweeView draweeView, String patch) {
        Uri uri = Uri.parse("asset://" + patch);
        draweeView.setImageURI(uri);
    }

    /**
     * 以高斯模糊显示。
     *
     * @param draweeView View。
     * @param url        url.
     * @param iterations 迭代次数，越大越魔化。
     * @param blurRadius 模糊图半径，必须大于0，越大越模糊。
     */
    public static void showUrlBlur(SimpleDraweeView draweeView, String url, int iterations, int blurRadius) {
        try {
            Uri uri = Uri.parse(url);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setPostprocessor(new IterativeBoxBlurPostProcessor(iterations, blurRadius))
                    .build();
            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(draweeView.getController())
                    .setImageRequest(request)
                    .build();
            draweeView.setController(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadBitmap(Application application, String url) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setProgressiveRenderingEnabled(true)
                .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(request,application);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {

            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

            }
        }, CallerThreadExecutor.getInstance());

    }


    public static void requestLayout(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(width, height);
            view.setLayoutParams(layoutParams);
        } else {
            layoutParams.width = width;
            layoutParams.height = height;
            view.requestLayout();
        }
    }


}
