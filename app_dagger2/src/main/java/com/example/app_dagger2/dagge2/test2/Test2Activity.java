package com.example.app_dagger2.dagge2.test2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.app_dagger2.R;
import com.example.app_dagger2.dagge2.test1.ICommonView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Test2Activity extends AppCompatActivity implements ICommonView {

    @BindView(R.id.dagger2_test)
    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        ButterKnife.bind(this);


        DaggerMyParentComponent.builder().build().provideSubcomponent().inject(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick(R.id.dagger2_test)
    void login() {

    }
}
