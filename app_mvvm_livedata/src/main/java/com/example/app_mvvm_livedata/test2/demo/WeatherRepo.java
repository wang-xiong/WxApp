package com.example.app_mvvm_livedata.test2.demo;

import android.arch.lifecycle.MutableLiveData;

import com.example.app_mvvm_livedata.test2.BaseRepo;
import com.example.app_mvvm_livedata.test2.RequestCallback;

public class WeatherRepo extends BaseRepo<IWeatherDataSource> {

    public WeatherRepo(IWeatherDataSource remoteDataSource) {
        super(remoteDataSource);
    }

    public MutableLiveData<Weather> queryWeather(String cityName) {
        final MutableLiveData<Weather> weatherMutableLiveData = new MutableLiveData<>();
        remoteDataSource.queryWeather(cityName, new RequestCallback<Weather>() {
            @Override
            public void onSuccess(Weather weather) {
                weatherMutableLiveData.setValue(weather);
            }
        });
        return weatherMutableLiveData;
    }
}
