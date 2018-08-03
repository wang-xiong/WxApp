package com.example.module_app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.component_base.Utils.WebViewUtil;
import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.module_app.IntentKV;
import com.example.module_app.R;

/**
 * Created by wangxiong on 2018/8/3.
 */

public class HomeDetailActivity extends BaseMvcActivity {
    private Toolbar mToolbar;
    private TextView mTitleTV;
    private WebView mWebView;
    private String mTitle;
    private String mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_app_activity_homedetail);
        handleIntent();
        initView();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(IntentKV.HOME_DETAIL_TITLE);
        mUrl = intent.getStringExtra(IntentKV.HOME_DETAIL_URL);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mTitleTV = findViewById(R.id.toolbar_title);
        mWebView = findViewById(R.id.home_wb);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        mTitleTV.setText(mTitle);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        WebViewUtil.init(mWebView);
        mWebView.loadUrl(mUrl);

        //点击拦截 true表示拦截, false表示不拦截
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //view.loadUrl(articleLink);
                return false;

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
    }

    public static void startHomeDetailActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, HomeDetailActivity.class);
        intent.putExtra(IntentKV.HOME_DETAIL_TITLE, title);
        intent.putExtra(IntentKV.HOME_DETAIL_URL, url);
        context.startActivity(intent);
    }
}
