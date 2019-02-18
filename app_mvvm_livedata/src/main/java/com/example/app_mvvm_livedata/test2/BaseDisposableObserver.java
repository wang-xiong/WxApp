package com.example.app_mvvm_livedata.test2;

import android.widget.Toast;

import io.reactivex.observers.DisposableObserver;

public class BaseDisposableObserver<T> extends DisposableObserver<T> {
    private BaseViewModel baseViewModel;

    private RequestCallback<T> requestCallback;

    public BaseDisposableObserver(BaseViewModel baseViewModel) {
        this.baseViewModel = baseViewModel;
    }

    public BaseDisposableObserver(BaseViewModel baseViewModel, RequestCallback<T> requestCallback) {
        this.baseViewModel = baseViewModel;
        this.requestCallback = requestCallback;
    }


    @Override
    public void onNext(T t) {
        if (requestCallback != null) {
            requestCallback.onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
        if (requestCallback instanceof RequestMultiplyCallback) {
            RequestMultiplyCallback callback = (RequestMultiplyCallback) requestCallback;
            if (t instanceof BaseException) {
                callback.onFail((BaseException) t);
            } else {
                callback.onFail(new BaseException(HttpCode.CODE_UNKNOWN, t.getMessage()));
            }
        } else {
            if (baseViewModel != null) {
                baseViewModel.showToast(t.getMessage());
            } else {
                Toast.makeText(ContextHolder.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onComplete() {

    }
}
