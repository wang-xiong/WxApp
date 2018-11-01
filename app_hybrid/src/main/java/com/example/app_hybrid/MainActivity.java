package com.example.app_hybrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = null;
        WebSettings webSettings = webView.getSettings();

        //支持与与JavaScript交互
        webSettings.setJavaScriptEnabled(true);

        
    }
}
