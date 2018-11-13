package com.example.app_mvp.view.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_mvp.R;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        final String username = getIntent().getStringExtra("user_name");

        TextView textView = findViewById(R.id.user_tip);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserActivity.this, username +" 真可爱，哈哈", Toast.LENGTH_LONG).show();
            }
        });
    }
}
