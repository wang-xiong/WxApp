package com.example.rxjava_library.base;

import com.example.rxjava_library.movie.Constant;
import com.example.rxjava_library.movie.MovieResult;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangxiong on 2018/8/10.
 */

public class RetrofitServiceManager {

    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private Retrofit mRetrofit;
    private static RetrofitServiceManager instance;

    private RetrofitServiceManager() {
        HttpCommonInterceptor httpCommonInterceptor = new HttpCommonInterceptor.Builder()
                .addHeadParams("platform", "android")
                .addHeadParams("userToken", "1234343434dfdfd3434")
                .addHeadParams("userId", "1234")
                .build();

        //1.创建OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(httpCommonInterceptor);

        //2.创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();
    }


    public static RetrofitServiceManager getInstance() {
        if (instance ==  null) {
            synchronized (RetrofitServiceManager.class) {
                if (instance== null) {
                    instance = new RetrofitServiceManager();
                }
            }
        }
        return instance;
    }

    public <T, M> void request(Class<T> service, RetrofitServiceListener <T, M> listener) {
        Observable<M> observable = listener.getObservable(mRetrofit.create(service));
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener.getObserver());
    }

    /**
     * 获取service接口
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    public interface RetrofitServiceListener<T, M> {
        Observable<M> getObservable(T t);

        Observer<M> getObserver();
    }



}
