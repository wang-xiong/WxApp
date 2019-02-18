package com.example.app_mvvm_livedata.test2.demo;

import com.example.app_mvvm_livedata.test2.BaseRemoteDataSource;
import com.example.app_mvvm_livedata.test2.BaseViewModel;
import com.example.app_mvvm_livedata.test2.RequestCallback;

public class WeatherDataSource extends BaseRemoteDataSource implements IWeatherDataSource{

    public WeatherDataSource(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void queryWeather(String cityName, RequestCallback<Weather> requestCallback) {
        execute(getService(ApiServer.class).queryWeather(cityName), requestCallback);
    }
}
