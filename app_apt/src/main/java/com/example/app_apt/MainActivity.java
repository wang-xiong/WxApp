package com.example.app_apt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.apt_annotation.BindView;
import com.example.apt_library.BindViewTools;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.apt_tv)
    public TextView mTextView;
    @BindView(R.id.apt_btn)
    public Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        BindViewTools.bind(this);
        mTextView.setText("bind TextView success");
        mButton.setText("bind Button success");
    }
}
