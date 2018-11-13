package com.example.app_mvvm.viewmodel;

import android.databinding.ObservableInt;
import android.os.AsyncTask;
import android.view.View;

import com.example.app_mvvm.model.Person;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainViewModel {

    public ObservableInt mProgressBarVisible = new ObservableInt(View.GONE);

    public ObservableInt mErrorViewVisible = new ObservableInt(View.GONE);

    public ObservableInt mRecyclerViewVisible = new ObservableInt(View.GONE);

    private MainViewModelListener listener;

    public MainViewModel(MainViewModelListener listener) {
        this.listener = listener;
    }

    public void loadData() {
        DataAsyncTask dataAsyncTask = new DataAsyncTask(this);
        dataAsyncTask.execute();
    }

    private void initView() {
        mProgressBarVisible.set(View.VISIBLE);
        mErrorViewVisible.set(View.GONE);
        mRecyclerViewVisible.set(View.GONE);
    }

    private void handlerResult(List<Person> dataList) {
        mProgressBarVisible.set(View.GONE);
        if (dataList == null) {
            mErrorViewVisible.set(View.VISIBLE);
            mRecyclerViewVisible.set(View.GONE);
        } else {
            mErrorViewVisible.set(View.GONE);
            mRecyclerViewVisible.set(View.VISIBLE);
            if (listener != null) {
                listener.showRecyclerView(dataList);
            }
        }
    }


    public interface MainViewModelListener {
        void showRecyclerView(List<Person> dataList);
    }

    public static class DataAsyncTask extends AsyncTask<Void , Void, List<Person>> {

        private WeakReference<MainViewModel> weakReference;

        public DataAsyncTask(MainViewModel mainViewModel) {
            weakReference = new WeakReference<>(mainViewModel);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainViewModel mainViewModel = weakReference.get();
            if (mainViewModel != null) {
                mainViewModel.initView();
            }
        }

        @Override
        protected List<Person> doInBackground(Void... voids) {
            return initData();
        }

        @Override
        protected void onPostExecute(List<Person> dataList) {
            super.onPostExecute(dataList);
            MainViewModel mainViewModel = weakReference.get();
            if (mainViewModel != null) {
               mainViewModel.handlerResult(dataList);
            }

        }

        private List<Person> initData() {
            ArrayList<Person> dataList = new ArrayList<>();
            for (int i = 0 ; i<30; i++) {
                dataList.add(new Person("zhangsan", "hello word mvvm"));
            }
            try {
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //return  null;
            return dataList;
        }
    }
}
