package com.example.rxjava_library.movie;

import com.example.rxjava_library.movie.MovieResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wangxiong on 2018/8/13.
 */

public interface MovieService {



    /**
     * baseUrl+"top250?start=1&count=1"
     * @param start
     * @param count
     * @return
     */
    @GET("top250")
    Observable<MovieResult> getTopMovie(@Query("start") int start, @Query("count") int count);

    @FormUrlEncoded
    @POST("/x3/weather")
    Observable<String> getWeather(@Field("cityId") String cityId, @Field("key") String key);

}