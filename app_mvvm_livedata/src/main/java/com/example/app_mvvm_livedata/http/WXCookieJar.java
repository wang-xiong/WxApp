package com.example.app_mvvm_livedata.http;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class WXCookieJar implements CookieJar {
    private final WXCookieManager cookieManager;
    private Map<String, String> mCookies;

    public WXCookieJar(WXCookieManager cookieManager, Map<String, String> cookies) {
        this.cookieManager = cookieManager;
        this.mCookies = cookies;
    }

    @Override
    public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
        cookieManager.put(url.uri(), cookies);
    }

    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
        List<Cookie> resultCookies = new ArrayList<>(cookieManager.get(url.uri()));
        if (mCookies != null && mCookies.size() > 0) {
            Set<Map.Entry<String, String>> entries = mCookies.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                resultCookies.add(new Cookie.Builder().domain("baidu.com").path("/").name(entry.getKey()).value(entry.getValue()).build());
            }
        }
        return resultCookies;
    }
}
