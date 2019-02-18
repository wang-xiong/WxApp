package com.example.app_mvvm_livedata.test2;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class BaseRemoteDataSource {

    private CompositeDisposable compositeDisposable;

    private BaseViewModel baseViewModel;

    public BaseRemoteDataSource(BaseViewModel baseViewModel) {
        this.compositeDisposable = new CompositeDisposable();
        this.baseViewModel = baseViewModel;
    }

    protected <T> T getService(Class<T> clazz) {
        return RetrofitManagement.getInstance().getServer(clazz);
    }

    protected <T> T getService(Class<T> clazz, String host) {
        return RetrofitManagement.getInstance().getServer(clazz, host);
    }

    protected <T> ObservableTransformer<BaseResponseBody<T>, T> applySchedulers() {
        return RetrofitManagement.getInstance().applySchedulers();
    }

    protected <T> void execute(Observable<BaseResponseBody<T>> observable, RequestCallback<T> requestCallback) {
        execute(observable, new BaseDisposableObserver<>(baseViewModel, requestCallback), true);
    }

    protected <T> void execute(Observable<BaseResponseBody<T>> observable, RequestMultiplyCallback<T> callback) {
        execute(observable, new BaseDisposableObserver<>(baseViewModel, callback), true);
    }

    public <T> void executeWithoutDismiss(Observable<BaseResponseBody<T>> observable, DisposableObserver<T> observer) {
        execute(observable, observer, false);
    }

    private <T> void execute(Observable<BaseResponseBody<T>> observable, DisposableObserver<T> observer, boolean isDismiss) {
        Disposable disposable = observable.throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<T>applySchedulers())
                .compose(isDismiss ? this.<T>loadingTransformer() : this.<T>loadingTransformerWithoutDismiss())
                .subscribeWith(observer);

        addDisposable(disposable);
    }

    private void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    private <T> ObservableTransformer<T, T> loadingTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                startLoading();
                            }
                        })
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                dismissLoading();
                            }
                        });
            }
        };
    }

    private <T> ObservableTransformer<T, T> loadingTransformerWithoutDismiss() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                startLoading();
                            }
                        });
            }
        };
    }

    private void startLoading() {
        if (baseViewModel != null) {
            baseViewModel.startLoading();
        }
    }

    private void dismissLoading() {
        if (baseViewModel != null) {
            baseViewModel.dismissLoading();
        }
    }


}
