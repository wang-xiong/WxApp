package com.example.app_mvvm_livedata;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeRepository {
    private final MutableLiveData<List<Project>> data = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isShowLoading = new MutableLiveData<>();

    private GitHubService gitHubService;

    public HomeRepository(Application application) {
        gitHubService = HttpHelper.getInstance().createService(GitHubService.class);
    }

    public LiveData<Boolean> getIsShowLoading() {
        return isShowLoading;
    }

    private Call<List<Project>> call;

    public LiveData<List<Project>> getProjectList(String userId) {
        isShowLoading.setValue(true);
        call = gitHubService.getProjectList(userId);
        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    //请求失败
                    isShowLoading.setValue(false);
                }

            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                //data.setValue(null);
                if (call.isCanceled()) {
                    //调用call.cancel主动取消的请求
                } else {
                    isShowLoading.setValue(false);
                }
            }
        });
        return data;
    }

    private DisposableObserver disposable;

    public void loadProjectListDataByRx(String userId) {
        disposable = gitHubService.loadProjectList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Project>>() {
                    @Override
                    protected void onStart() {
                        isShowLoading.setValue(true);
                    }

                    @Override
                    public void onNext(List<Project> projects) {
                        data.setValue(projects);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isShowLoading.setValue(false);
                    }

                    @Override
                    public void onComplete() {
                        isShowLoading.setValue(false);
                    }
                });
    }

    public void clear() {
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}
