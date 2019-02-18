package com.example.app_mvvm_livedata.test2;

import com.example.app_mvvm_livedata.BuildConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManagement {
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    private RetrofitManagement() {
    }

    public static RetrofitManagement getInstance() {
        return RetrofitHolder.retrofitManagement;
    }

    private static class RetrofitHolder {
        private static final RetrofitManagement retrofitManagement = new RetrofitManagement();
    }

    private Retrofit createRetrofit(String url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(6000, TimeUnit.MILLISECONDS)
                .writeTimeout(6000, TimeUnit.MILLISECONDS)
                .connectTimeout(6000, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new FilterInterceptor())
                .retryOnConnectionFailure(true);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
            builder.addInterceptor(new ChuckInterceptor(ContextHolder.getContext()));
        }

        OkHttpClient client = builder.build();

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    <T> ObservableTransformer<BaseResponseBody<T>, T> applySchedulers() {
        return new ObservableTransformer<BaseResponseBody<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponseBody<T>> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Function<BaseResponseBody<T>, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(BaseResponseBody<T> responseBody) throws Exception {
                                switch (responseBody.getCode()) {
                                    case HttpCode.CODE_SUCCESS:
                                        return createData(responseBody.getData());
                                    case HttpCode.CODE_TOKEN_INVALID:
                                        throw new TokenInvalidException();
                                    case HttpCode.CODE_PARAMETER_INVALID:
                                        throw new ParameterInvalidException();
                                    default:
                                        throw new ServerResultException(responseBody.getCode(), responseBody.getMsg());
                                }
                            }
                        });

            }
        };
    }

    private <T> Observable<T> createData(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }

            }
        });
    }

    public <T> T getServer(Class<T> clazz) {
        return getServer(clazz, HttpConfig.BASE_URL_WEATHER);
    }


    public <T> T getServer(Class<T> clazz, String host) {
        T value;
        if (serviceMap.containsKey(host)) {
            Object obj = serviceMap.get(host);
            if (obj == null) {
                value = createRetrofit(host).create(clazz);
            } else {
                value = (T) obj;
            }
        } else {
            value = createRetrofit(host).create(clazz);
            serviceMap.put(host, value);
        }
        return value;
    }
}
