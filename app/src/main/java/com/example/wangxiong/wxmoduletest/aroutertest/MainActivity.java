package com.example.wangxiong.wxmoduletest.aroutertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.wangxiong.wxmoduletest.R;


public class MainActivity extends AppCompatActivity {

    //发现服务，首先定义服务对象，并且添加注解，我们不需要知道接口的具体实现类
    @Autowired(name = "/service/hello")
    IService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.testA);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toComponentFirst();
            }
        });

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ARouter.getInstance().build("/wxmoduletest/RouterTestActivity").navigation();
                ARouter.getInstance().build("/wxmoduletest/RouterTestActivity").navigation(MainActivity.this, new NavCallback() {

                    @Override
                    public void onFound(Postcard postcard) {
                        //找到了
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        //找不到
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        //被拦截了
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        postcard.getGroup();//返回当前的组名
                        //跳转完了
                    }
                });
            }
        });

        Fragment fragment = (Fragment) ARouter.getInstance().build("/wxmoduletest/RouterTestFragment").navigation();

        WebView webView = findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/router.html");

        //然后添加注解初始化，自动赋值。
        ARouter.getInstance().inject(this);
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                service.sayHello(MainActivity.this);
            }
        });


    }

    private void toComponentFirst() {
        Intent intent = new Intent();
        //intent.setClass(this, ToolbarActivity.class);
        //startActivity(intent);
    }


}
