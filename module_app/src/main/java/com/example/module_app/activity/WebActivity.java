package com.example.module_app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.module_app.R;


/**
 * Android混合开发：Java+h5
 * 1、WebView如何加载H5的页面
 * 2、Android如何调用H5的方法
 * 3、H5任何调用Android的方法
 */

public class WebActivity extends BaseMvcActivity {


    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_app_activity_webview);
        intWebView();
        findViewById(R.id.test_js_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeJsMethod();
                invokeAndroidMethod();
            }
        });
    }

    private void intWebView() {
        webView = findViewById(R.id.test_wv);
        loadWebView();
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
    }


    private void loadWebView() {
        //加载assets文件夹下的test.html
        //webView.loadUrl("http://www.baidu.com");
        //加载网页
        webView.loadUrl("file:///android_asset/test.html");
    }

    private void invokeJsMethod() {
       WebSettings webSettings = webView.getSettings();
       //设置可调用js方法
        webSettings.setJavaScriptEnabled(true);

        //1、调用无参数无返回值的方法
        webView.loadUrl("JavaScript:show()");
        //2、调用有参数有返回值的方法
        webView.evaluateJavascript("sum(1,2)", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                //value是js的返回结果
            }
        });

        //3、调用带参数的方法
        //固定字符串之间用单引号
        webView.loadUrl("javascript:alertMessage('hello word')");
        //变量名用转译分隔符隔开
        String content = "hello word";
        webView.loadUrl("javascript:alertMessage( \" " + content + " \" )");
    }

    private  void invokeAndroidMethod() {
        //1、定义类，方法
        //2、打开js接口，参数1类名， 参数2，别名
        //3、h5调用方法为window.类名.别名.方法名
        webView.addJavascriptInterface(new JsInteration(), "android");
    }


    public class JsInteration {

        @JavascriptInterface
        public String getValue() {
            return "I from Android";
        }
    }
}
