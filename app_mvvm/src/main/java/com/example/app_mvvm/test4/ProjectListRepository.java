package com.example.app_mvvm.test4;

import com.example.app_mvvm.test3.model.Project;
import com.example.app_mvvm.test3.repository.GitHubService;
import com.example.app_mvvm.test4.base.BaseRepository;
import com.example.app_mvvm.test4.config.Constants;
import com.example.app_mvvm.test4.config.URL;
import com.example.app_mvvm.test4.rx.RxSchedulers;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectListRepository extends BaseRepository {
    private GitHubService gitHubService;


    public ProjectListRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(URL.BASE_URL)
                .build();
        gitHubService = retrofit.create(GitHubService.class);
    }

    public void loadProjectListData(String userId) {
        addDisposable(gitHubService.loadProjectList(userId)
                .compose(RxSchedulers.<List<Project>>io_main())
                .subscribeWith(new DisposableSubscriber<List<Project>>() {
                    @Override
                    public void onNext(List<Project> projects) {
                        sendData(Constants.EVENT_KEY_HOME, projects);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }
}
