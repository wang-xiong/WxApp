package com.example.utils_library;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by wangxiong on 2018/8/3.
 */

public class WebViewUtil {

    public static void init(WebView webview) {
        // 默认缓存模式
        WebSettings settings = webview.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 设置支持javaScript
        settings.setJavaScriptEnabled(true);
        settings.setSaveFormData(true);
        // 不保存密码
        settings.setSavePassword(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setSupportMultipleWindows(false);
        settings.setLightTouchEnabled(true);
        // 不支持页面放大
        settings.setBuiltInZoomControls(false);
        //隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        // 滚动条
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // 非常关键，否则设置了WebChromeClient后会出现乱码
        settings.setDefaultTextEncodingName("utf-8");
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBlockNetworkImage(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
    }
}
