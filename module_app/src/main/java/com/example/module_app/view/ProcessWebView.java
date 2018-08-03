package com.example.module_app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.module_app.R;

/**
 * Created by wangxiong on 2018/8/3.
 */

public class ProcessWebView extends WebView {
    private ProgressBar progressBar;

    public ProcessWebView(Context context) {
        this(context, null);
    }

    public ProcessWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProcessWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        progressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                10, 0, 0));
        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.webview_process_bg));
        addView(progressBar);
        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(GONE);
                } else {
                    if (progressBar.getVisibility() == GONE){
                        progressBar.setVisibility(VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LayoutParams lp = (LayoutParams) progressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressBar.setLayoutParams(lp);
    }
}
