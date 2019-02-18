package com.example.app_mvvm_livedata.test2.demo;

import com.example.app_mvvm_livedata.test2.RequestCallback;

public interface IWeatherDataSource {

    void queryWeather(String cityName, RequestCallback<Weather> requestCallback);
}
