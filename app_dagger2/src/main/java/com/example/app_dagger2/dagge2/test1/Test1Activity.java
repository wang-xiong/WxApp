package com.example.app_dagger2.dagge2.test1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.app_dagger2.DaggerCommonComponent;
import com.example.app_dagger2.DaggerSecondComponent;
import com.example.app_dagger2.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Test1Activity extends AppCompatActivity implements ICommonView {

    @BindView(R.id.dagger2_test)
    Button button;

    @Inject
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        ButterKnife.bind(this);

        SecondComponent secondComponent = DaggerSecondComponent.create();
        DaggerCommonComponent.builder()
                .secondComponent(secondComponent)
                .commonModule(new CommonModule(this))
                .build()
                .inject(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick(R.id.dagger2_test)
    void login() {
        presenter.login(new User());
    }
}
