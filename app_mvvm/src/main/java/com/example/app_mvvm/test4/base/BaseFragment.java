package com.example.app_mvvm.test4.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_mvvm.test4.LiveBus;
import com.example.app_mvvm.test4.util.TUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFragment<T extends BaseViewModel> extends Fragment {
    private List<String> eventKeys = new ArrayList<>();
    protected T mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResId(), null, false);
        mViewModel = ViewModelProviders.of(this).get(TUtil.<T>getInstance(this, 0));
        initView(rootView, savedInstanceState);
        dataObserver();
        return rootView;
    }

    protected abstract int getLayoutResId();

    protected abstract void initView(View rootView, Bundle savedInstanceState);

    protected abstract void dataObserver();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (eventKeys != null && eventKeys.size() > 0) {
            for (int i = 0; i < eventKeys.size(); i++) {
                LiveBus.getDefault().clear(eventKeys.get(i));
            }
        }
    }


    protected <M> MutableLiveData<M> registerObserver(String eventKey, String tag) {
        String key = mergeEventKey(eventKey, tag);
        eventKeys.add(key);
        return LiveBus.getDefault().subscribe(eventKey, tag);
    }

    private String mergeEventKey(String eventKey, String tag) {
        if (!TextUtils.isEmpty(tag)) {
            return eventKey + tag;
        } else {
            return eventKey;
        }
    }
}
