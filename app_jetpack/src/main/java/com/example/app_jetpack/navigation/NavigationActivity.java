package com.example.app_jetpack.navigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.app_jetpack.R;

import androidx.navigation.Navigation;

public class NavigationActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.fragment_navigation).navigateUp();
    }
}
