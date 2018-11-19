package com.example.app_plugin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public class AssetUtils {

    public static AssetManager mAssetManager;
    public static Resources mResources;
    public static DexClassLoader mDexClassLoader;


    /**
     * 通过OkIo将Assets目录下的文件移动到包的files目录下
     *
     * @param context
     * @param fileName
     */
    public static void moveAssetByOkIo(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(fileName);
            Source source = Okio.source(inputStream);
            BufferedSource buffer = Okio.buffer(source);
            Log.e("MainActivity", "" + context.getFileStreamPath(fileName));
            buffer.readAll(Okio.sink(context.getFileStreamPath(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 通过传统的Java Io将Assets目录下的文件移动到包的files目录下
     *
     * @param context
     * @param fileName
     */
    public static void moveAssetByJavaIo(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            assetManager.open(fileName);
            File file = context.getFileStreamPath(fileName);
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int count;
            while ((count = inputStream.read()) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 通过addAssetPath添加路径，构建新的AssetManager， Resources ,使用者需要重写getAssets和getResources
     *
     * @param context
     * @param fileName
     */
    public static void addAssetPath(Context context, String fileName) {
        try {
            String dexPath = context.getFileStreamPath(fileName).getAbsolutePath();
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, dexPath);
            mAssetManager = assetManager;
            mResources = new Resources(assetManager, context.getResources().getDisplayMetrics()
                    , context.getResources().getConfiguration());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public static void createClassLoader(Context context, String fileName) {
        String dexPath = context.getFileStreamPath(fileName).getAbsolutePath();
        String optimizedDirectory = context.getDir("dex", Context.MODE_PRIVATE).getAbsolutePath();
        mDexClassLoader = new DexClassLoader(dexPath, optimizedDirectory, null, context.getClassLoader());
    }
}
