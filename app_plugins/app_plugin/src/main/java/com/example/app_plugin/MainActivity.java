package com.example.app_plugin;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_plugin_module.ICommonInterface;
import com.example.utils_library.AssetUtils;

public class MainActivity extends AppCompatActivity {

    /**
     * 一、新建app_plugin、plugin1、app_plugin_module，其中新建app_plugin和plugin1依赖于app_plugin_module
     * 二、在app_plugin_module新建接口ICommonInterface
     * 三、在plugin1:
     * a.实现ICommonInterface接口
     * b.创建Task任务将编译成功的apk赋值到app_plugin的assets目录下
     * c.执行task
     * 四.app_plugin中操作assets目录下的apk:
     * a..先复制到包下面的files目录下，两种方法：OkIo和传统的JavaIO其中OKio更快一点
     * b.构造AssetManager,Resources，从而使得通过AssetManager或Resources操作对应资源
     * c.构造DexClassLoader, 加载类
     * d.通过加载的类创建对应实例
     * 5.调用响应的方法
     */

    private TextView mTextView;
    private ImageView mImageView;
    private Button mButton;
    private String APK_FILE_NAME = "plugin1.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.tv);
        mImageView = findViewById(R.id.img);
        mButton = findViewById(R.id.test_btn);

        //1.移动Assets下文件到包目录下。
        AssetUtils.moveAssetByOkIo(MainActivity.this, APK_FILE_NAME);
        //2.构造AssetManager,Resources
        AssetUtils.addAssetPath(getBaseContext(), APK_FILE_NAME);
        //3.构造DexClassLoader
        AssetUtils.createClassLoader(MainActivity.this, APK_FILE_NAME);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPluginTest();
            }
        });

    }

    @Override
    public AssetManager getAssets() {
        //return super.getAssets();
        return AssetUtils.mAssetManager == null ? super.getAssets() : AssetUtils.mAssetManager;
    }


    @Override
    public Resources getResources() {
        //return super.getResources();
        return AssetUtils.mResources == null ? super.getResources() : AssetUtils.mResources;
    }

    private void doPluginTest() {
        try {
            //4.通过DexClassLoader加载PluginResources类
            Class pluginResources = AssetUtils.mDexClassLoader.loadClass("com.example.plugin1.PluginResources");
            //5.通过PluginResources构造ICommonInterface实例
            ICommonInterface pluginObject = (ICommonInterface) pluginResources.newInstance();

            mTextView.setText(pluginObject.getString());
            mImageView.setImageResource(pluginObject.getDrawable());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


}
