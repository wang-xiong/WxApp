package com.example.app_mvp.view.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_mvp.R;
import com.example.app_mvp.present.main.MainBasePresentImpl;
import com.example.app_mvp.view.user.UserActivity;

public class MainActivity extends AppCompatActivity  implements MainBaseView , View.OnClickListener {

    private EditText mUserName;
    private EditText mPassword;
    private Button mLoginBtn;

    private MainBasePresentImpl mPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPreset();
    }

    private void initView() {
        mUserName = findViewById(R.id.user_name);
        mPassword = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.login);
        mLoginBtn.setOnClickListener(this);
    }

    private void initPreset() {
        mPresent = new MainBasePresentImpl();
        mPresent.attachView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresent.detachView();
    }

    @Override
    public void showEmptyTip(String tip) {
        Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorView() {
        Toast.makeText(this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessView(String userName) {
        if (!TextUtils.isEmpty(userName)) {
            Intent intent = new Intent(this, UserActivity.class);
            intent.putExtra("user_name", userName);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.login:
                mPresent.login(mUserName.getText().toString(), mPassword.getText().toString());
                break;
        }
    }
}
