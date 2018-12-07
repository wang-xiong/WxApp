package com.example.rxjava_library.movie;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.util.Log;


import com.example.rxjava_library.base.RetrofitServiceManager;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangxiong on 2018/8/13.
 */

public class MovieActivity extends Activity implements RetrofitServiceManager.RetrofitServiceListener<MovieService, MovieResult> {
    private static final String TAG = MovieActivity.class.getSimpleName();
    private MovieLoader mMovieLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieLoader = new MovieLoader();
    }

    protected Map<String, String> mUrlParams = new ArrayMap<>();
    protected Map<String, String> mFormParams = new ArrayMap<>();


    public void getMovieList() {
        //方法一
        RetrofitServiceManager.getInstance().request(MovieService.class, this);

        //方法二
        RetrofitServiceManager.getInstance().create(MovieService.class)
                .getAll(mUrlParams, mFormParams)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResult>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(MovieResult movieResult) {
                        Log.i(TAG, "onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete");
                    }
                });

        //方法三
        mMovieLoader.getMovie(0, 10).subscribe(new Observer<MovieResult>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe");
            }

            @Override
            public void onNext(MovieResult movieResult) {
                Log.i(TAG, "onNext");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
            }
        });
    }

    public Map<String, String> getUrlParams() {
        return mUrlParams;
    }

    public Map<String, String> getFormParams() {
        return mFormParams;
    }


    @Override
    public Observable<MovieResult> getObservable(MovieService movieService) {
        return movieService.getAll(getUrlParams(), getFormParams());
    }

    @Override
    public Observer<MovieResult> getObserver() {
        return new Observer<MovieResult>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe");
            }

            @Override
            public void onNext(MovieResult movieResult) {
                Log.i(TAG, "onNext");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
            }
        };
    }
}
