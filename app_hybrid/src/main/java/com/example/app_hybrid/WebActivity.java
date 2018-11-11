package com.example.app_hybrid;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Set;


/**
 * Android混合开发：Java+h5
 * 1、WebView如何加载H5的页面
 * 2、Android如何调用H5的方法
 * 3、H5任何调用Android的方法
 */

public class WebActivity extends AppCompatActivity {


    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = findViewById(R.id.test_wv);
        intWebView();

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeJsMethod1();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeJsMethod2();
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeJsMethod3();
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeJsMethod4();
            }
        });
    }

    private void intWebView() {
        //webView.loadUrl("http://www.baidu.com");
        //加载assets文件夹下的test.html
        webView.loadUrl("file:///android_asset/webview_test.html");
        //webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                 AlertDialog dialog = new AlertDialog.Builder(WebActivity.this)
                        .setTitle("Alert")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setCancelable(false)
                        .create();
                 dialog.show();

                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                // 根据协议的参数，判断是否是所需要的url(原理同方式2)
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）

                Uri uri = Uri.parse(message);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if ( uri.getScheme().equals("js")) {

                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("webview")) {
                        // 执行JS所需要调用的逻辑
                        System.out.println("js调用了Android的方法");
                        // 可以在协议上带有参数并传递到Android上
                        HashMap<String, String> params = new HashMap<>();
                        Set<String> collection = uri.getQueryParameterNames();

                        //参数result:代表消息框的返回值(输入值)
                        result.confirm("js调用了Android的方法成功啦");
                    }
                    return true;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });

        /*webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //request.getUrl();
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //根据协议的参数，判断是否是所需要的url
                //一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）

                Uri uri = Uri.parse(url);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if ( uri.getScheme().equals("js")) {

                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("webview")) {
                        // 执行JS所需要调用的逻辑
                        Toast.makeText(WebActivity.this, "JS调用Android代码", Toast.LENGTH_SHORT).show();
                        // 可以在协议上带有参数并传递到Android上
                        HashMap<String, String> params = new HashMap<>();
                        Set<String> collection = uri.getQueryParameterNames();

                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });*/
        WebSettings webSettings = webView.getSettings();
        //设置可调用js方法
        webSettings.setJavaScriptEnabled(true);
        //设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        invokeAndroidMethod();
    }

    //1、调用无参数无返回值的方法
    private void invokeJsMethod1() {
        webView.loadUrl("JavaScript:show()");
    }

    //2、调用带参数的方法
    private void invokeJsMethod2() {
        //固定字符串之间用单引号
        //webView.loadUrl("javascript:alertMessage('hello word')");
        //变量名用转译分隔符隔开
        String content = "Android调用了js方法";
        webView.loadUrl("javascript:alertMessage( \" " + content + " \" )");
    }

    //3、调用有参数有返回值的方法
    private void invokeJsMethod3() {
        webView.evaluateJavascript("sum(1,2)", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                //value是js的返回结果
                Toast.makeText(WebActivity.this, "调用js结果value:" + value, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void invokeJsMethod4() {
        String content = "Android调用了js方法";
        webView.evaluateJavascript("javascript:alertMessage(\"" + content + " \" )", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Toast.makeText(WebActivity.this, "调用js结果value:" + value, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Android调用JS方法：
     * 1.loadUrl 方便简洁 效率低、返回值麻烦 使用场景：不需要返回值对性能要求低时
     * 2.evaluateJavascript 效率高 4.4以上
     */
    private void invokeJsMethod() {
        String content = "Android调用了js方法";
        if (Build.VERSION.SDK_INT < 18) {
            webView.loadUrl("javascript:alertMessage( \" " + content + " \" )");
        } else {
            webView.evaluateJavascript("javascript:alertMessage(\"" + content + " \" )", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Toast.makeText(WebActivity.this, "调用js结果value:" + value, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * 对于JS调用Android代码的方法有3种：
     * 1. 通过WebView的addJavascriptInterface（）进行对象映射
     * 2. 通过 WebViewClient 的shouldOverrideUrlLoading ()方法回调拦截 url
     * 3. 通过 WebChromeClient 的onJsAlert()、onJsConfirm()、onJsPrompt（）方法回调拦截JS对话框alert()、confirm()、prompt（） 消息
     */

    private void invokeAndroidMethod() {
        //1、定义类，方法
        //2、打开js接口，参数1类名， 参数2，别名
        //3、h5调用方法为window.类名.别名.方法名
        //问题存在内存泄露
        webView.addJavascriptInterface(new AndroidToJs(), "android");

    }


    public class AndroidToJs {
        //1.定义JS需要调用的方法
        @JavascriptInterface
        public String hello(String msg) {
            return "JS调用了Android的方法";
        }
    }
}
