package com.example.app_jetpack.LiveData;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class LiveDataActivity extends AppCompatActivity {

    private NetWorkState netWorkState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        netWorkState = new NetWorkState(getApplicationContext());
        netWorkState.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Toast.makeText(LiveDataActivity.this, aBoolean ? "网络连接成功" : "网络连接失败", Toast.LENGTH_LONG).show();
            }
        });
        MutableLiveData<String> mutableLiveData = new MutableLiveData<>();
    }
}
