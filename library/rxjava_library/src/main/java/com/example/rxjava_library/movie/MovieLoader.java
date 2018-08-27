package com.example.rxjava_library.movie;

import com.example.rxjava_library.base.ObjectLoader;
import com.example.rxjava_library.base.RetrofitServiceManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by wangxiong on 2018/8/13.
 */

public class MovieLoader extends ObjectLoader {

    private MovieService mMovieService;

    public MovieLoader() {
        mMovieService = RetrofitServiceManager.getInstance().create(MovieService.class);
    }


    public Observable<MovieResult> getMovie(int start, int count) {
        Observable<MovieResult> observable = mMovieService.getTopMovie(start, count);
        return observe(observable);
    }
}
