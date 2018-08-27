package com.example.rxjava_library.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.example.rxjava_library.R;
import com.example.rxjava_library.movie.Constant;
import com.example.rxjava_library.movie.MovieResult;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangxiong on 2018/8/10.
 */

public class RetrofitActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        textView = findViewById(R.id.retrofit_tv);
        initRetrofit();
    }

    private void initRetrofit() {
        //获取Retrofit实例，设置baseUrl
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .build();
        //通过Retrofit实例创建接口服务对象
        RequestServices requestServices = retrofit.create(RequestServices.class);

        //服务接口对象获取Call对象
        Call<ResponseBody> call = requestServices.getString();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String result = response.body().toString();
                    //onResponse是运行在主线程，可以更新UI
                    textView.setText(result);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("wx", "onFailure");
            }
        });
    }

    private void doTest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.TEST_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        RequestServices requestServices = retrofit.create(RequestServices.class);

        Call<RetrofitTestResult> call = requestServices.getCall();

        call.enqueue(new Callback<RetrofitTestResult>() {
            @Override
            public void onResponse(Call<RetrofitTestResult> call, Response<RetrofitTestResult> response) {
                response.body().show();
            }

            @Override
            public void onFailure(Call<RetrofitTestResult> call, Throwable t) {

            }
        });

    }

    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    public void doTest2() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestServices services = retrofit.create(RequestServices.class);
        Call<MovieResult> call = services.getTop250(0, 20);
        //异步请求
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                MovieResult movieResult = response.body();

            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });

        //同步请求
        try {
            Response<MovieResult> response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doTest3() {
        // 添加公共参数拦截器
//        BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
//                .addHeaderParam("userName","")//添加公共参数
//                .addHeaderParam("device","")
//                .build();

        //项目中设置头信息
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .addHeader("Accept-Encoding", "gzip")
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .method(originalRequest.method(), originalRequest.body());
                requestBuilder.addHeader("Authorization", "Bearer " + BaseConstant.TOKEN);//添加请求头信息，服务器进行token有效性验证
                Request request = requestBuilder.build();

                return chain.proceed(request);
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .writeTimeout(1000, TimeUnit.MILLISECONDS)
                .readTimeout(1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)//错误重连
                //.addInterceptor(basicParamsInterceptor)
                .addInterceptor(interceptor);

        OkHttpClient okHttpClient = builder.build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        RequestServices services = retrofit.create(RequestServices.class);

        services.getTop250ByRxJava(0, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<MovieResult>() {
                    @Override
                    public void onNext(MovieResult movieResult) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
