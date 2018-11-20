package com.example.rxjava_library.movie;

import android.app.Activity;
import android.os.Bundle;

import com.example.component_base.Utils.WLog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wangxiong on 2018/8/13.
 */

public class MovieActivity extends Activity {
    private static final String TAG = MovieActivity.class.getSimpleName();
    private MovieLoader mMovieLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieLoader = new MovieLoader();
    }

    public void getMovieList() {
        mMovieLoader.getMovie(0, 10).subscribe(new Observer<MovieResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                WLog.i(TAG, "onSubscribe");
            }

            @Override
            public void onNext(MovieResult movieResult) {
                WLog.i(TAG, "onNext");
            }

            @Override
            public void onError(Throwable e) {
                WLog.i(TAG, "onError");
            }

            @Override
            public void onComplete() {
                WLog.i(TAG, "onComplete");
            }
        });
    }
}
