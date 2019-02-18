package com.example.app_mvvm_livedata.test2.demo;


import com.example.app_mvvm_livedata.test2.BaseResponseBody;
import com.example.app_mvvm_livedata.test2.HttpConfig;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiServer {

    /**
     *
     * baseUrl不同，因此通过申明头部来为接口动态制定参数，需要用到Retrofit的拦截器
     */
    @Headers({HttpConfig.HTTP_REQUEST_TYPE_KEY + ":" + HttpConfig.HTTP_REQUEST_WEATHER})
    @GET("onebox/weather/query")
    Observable<BaseResponseBody<Weather>> queryWeather(@Query("cityname") String cityName);
}
