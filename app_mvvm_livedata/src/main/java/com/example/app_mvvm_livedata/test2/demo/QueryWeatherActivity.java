package com.example.app_mvvm_livedata.test2.demo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app_mvvm_livedata.R;
import com.example.app_mvvm_livedata.test2.BaseActivity;
import com.google.gson.Gson;

import butterknife.BindView;

public class QueryWeatherActivity extends BaseActivity {

    private static final String TAG = "QueryWeatherActivity";

    private WeatherViewModel weatherViewModel;


    @BindView(R.id.et_cityName)
    private EditText et_cityName;

    @BindView(R.id.tv_weather)
    private TextView tv_weather;


    @Override
    protected ViewModel initViewModel() {
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.setLifecycleOwner(this);
        weatherViewModel.getWeatherMutableLiveData().observe(this, new Observer<Weather>() {
            @Override
            public void onChanged(@Nullable Weather weather) {
                if (weather != null) {
                    handlerWeather(weather);
                }
            }
        });
        return weatherViewModel;
    }

    private void handlerWeather(Weather weather) {
        StringBuilder result = new StringBuilder();
        for (Weather.InnerWeather.NearestWeather nearestWeather : weather.getData().getWeather()) {
            result.append("\n\n").append(new Gson().toJson(nearestWeather));
        }
        tv_weather.setText(result.toString());
    }

    public void queryWeather(View view) {
        tv_weather.setText(null);
        weatherViewModel.queryWeather(et_cityName.getText().toString());
    }

}
