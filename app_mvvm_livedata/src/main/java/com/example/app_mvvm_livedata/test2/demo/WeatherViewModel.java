package com.example.app_mvvm_livedata.test2.demo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.example.app_mvvm_livedata.test2.BaseViewModel;

public class WeatherViewModel extends BaseViewModel {

    private MutableLiveData<Weather> weatherMutableLiveData;

    private WeatherRepo weatherRepo;

    public WeatherViewModel() {
        weatherMutableLiveData = new MutableLiveData<>();
        weatherRepo = new WeatherRepo(new WeatherDataSource(this));
    }

    public void queryWeather(String cityName) {
        weatherRepo.queryWeather(cityName).observe(lifecycleOwner, new Observer<Weather>() {
            @Override
            public void onChanged(@Nullable Weather weather) {
                weatherMutableLiveData.setValue(weather);
            }
        });
    }

    public MutableLiveData<Weather> getWeatherMutableLiveData() {
        return weatherMutableLiveData;
    }
}
