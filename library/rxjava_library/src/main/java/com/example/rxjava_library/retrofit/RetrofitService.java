package com.example.rxjava_library.retrofit;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by wangxiong on 2018/8/13.
 */

/**
 * 一、介绍
 * 1.Retrofit 是一个 RESTful 的HTTP网络请求框架的封装。
 * 2.网络请求是使用OKHttp完成
 * 二、使用
 * 1.添加Retrofit依赖，添加网络权限
 * 2.数据返回的Model类
 * 3.网络请求的接口
 * 4.Retrofit实例
 *    Retrofit retrofit = new Retrofit.Builder()
 *      .baseUrl(Constant.TEST_URL_BASE)
 *      .addConverterFactory(GsonConverterFactory.create())
 *      .build();
 * 5.创建请求接口实例
 *    RequestServices requestServices = retrofit.create(RequestServices.class);
 *    Call<RetrofitTestResult> call = requestServices.getCall();
 * 6.发送网络请求（异步或者同步）
 *    call.enqueue(new Callback<RetrofitTestResult>() {
 *          @Override public void onResponse(Call<RetrofitTestResult> call, Response<RetrofitTestResult> response) {
 *                  response.body().show();
 *          }
 *          @Override public void onFailure(Call<RetrofitTestResult> call, Throwable t) {
 *          }
 *    });
 * 7.处理返回数据
 */

public interface RetrofitService {
    //baseUrl= "http://www.baidu.com/";

    //一、GET请求的写法

    /**
     * 1、GET请求，方法中无参数
     * url=http://www.baidu.com/book/search
     * 方法中无参数
     *
     * @return
     */
    @GET("book/search")
    //这个url和baseUrl组成新的地址用于访问
    Call<ResponseBody> getString();

    //转成RxJava
    @GET("book/search")
    //这个url和baseUrl组成新的地址用于访问
    Observable<ResponseBody> getString1();

    /**
     * 2、GET请求，方法中指定@Path参数和@Query参数
     * url=http://www.baidu.com/article/list/1?page=1
     *
     * @param type
     * @param page
     * @return
     * @Path用于替换url地址中{和}所括的部分。
     * @Query将在url地址中追加类似“page=1”的字符串，形成提交给服务端的请求参数
     */
    @GET("article/list/{type}")
    Call<ResponseBody> getString(@Path("type") String type, @Query("page") int page);

    /**
     * 3、GET请求，提交表单数据。方法中定义@QueryMap参数。
     *
     * @param map
     * @return
     * @QueryMap参数将在url地址中追加类似 “type=text&count=30&page=1”的字符串
     */
    @GET("article/list")
    Call<ResponseBody> getString(@QueryMap Map<String, String> map);

    /**
     * 4、GET请求，方法中无参数。但在@Url里定义完整URL路径，这种情况下BaseUrl会被忽略。
     *
     * @return
     */
    @GET("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2299165671,2554860548&fm=116&gp=0.jpg")
    Call<ResponseBody> getString2();


    //Post请求

    /**
     * 将user转换成http请求的请求体
     *
     * @param user
     * @return
     */
    @POST("user/new")
    Call<User> getUser(@Body User user);


    /**
     * 传表单数据
     *
     * @param firstName
     * @param lastName
     * @return
     */
    @FormUrlEncoded
    @POST("usewr/update")
    Call<User> updateUser(@Field("firstName") String firstName, @Field("lastName") String lastName);

    //添加请求头部
    @POST("user")
    Call<User> getUser(@Header("Authorization") String authorization);

    @Headers("Cache-Control: max-age=640000")
    @POST("user")
    Call<User> getUser1();

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @POST("user")
    Call<User> getUser2();

    class User {

    }
}
