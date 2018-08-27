package com.example.rxjava_library.test;


import com.example.rxjava_library.movie.MovieResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by wangxiong on 2018/8/10.
 */

public interface RequestServices {

    Call<RetrofitTestResult> getCall();

    @GET("top250")
    Call<MovieResult> getTop250(@Query("start") int start, @Query("count") int count);

    @FormUrlEncoded
    @POST("top250")
    Call<MovieResult> getTopByPost250(@Field("start") int start, @Query("count") int count);

    @GET("top250")
    Observable<MovieResult> getTop250ByRxJava(@Query("start") int start, @Query("count") int count);

}