package com.example.app_mvvm_livedata.http;

import android.content.Context;
import android.net.Uri;

import com.example.app_mvvm_livedata.GitHubService;

import java.net.CookiePolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpHelper {


    private Retrofit retrofit;
    private static HttpHelper mInstance;



    public static HttpHelper getInstance(Context context, String baseUrl) {
        if (mInstance == null) {
            synchronized (HttpHelper.class) {
                if (mInstance == null) {
                    mInstance = new HttpHelper(context, baseUrl);
                }
            }
        }
        return mInstance;
    }

    private HttpHelper(Context context, String baseUrl) {
        WXSSlSocketFactory wxSSlSocketFactory = new WXSSlSocketFactory(context);
        WXCookieManager cookieManager = new WXCookieManager(WXCookieStore.getInstance(context), CookiePolicy.ACCEPT_ALL);
        Map<String, String> mCookies = new HashMap<>();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .dispatcher(new Dispatcher())
                .connectionPool(new ConnectionPool(10, 3, TimeUnit.MINUTES))
                .sslSocketFactory(wxSSlSocketFactory.getSSLSocketFactory(), wxSSlSocketFactory.getTrustManager())
                .hostnameVerifier(new WXHostnameVerifier(Uri.parse(baseUrl).getHost()))
                .cookieJar(new WXCookieJar(cookieManager, mCookies))
                .addInterceptor(new WXInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.HTTPS_API_GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

    }

    public <T> T createService(Class<T> tClass) {
        return retrofit.create(tClass);
    }

}
