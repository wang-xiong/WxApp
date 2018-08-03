package com.example.homemodule.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.component_base.ARouter.RouterUrl;
import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.homemodule.R;

@Route(path = RouterUrl.PATH_MODULE_A_TOOLBAR)
public class ToolbarActivity extends BaseMvcActivity implements Toolbar.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_home_activity_toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);


        //toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("");
        //toolbar.setSubtitle("子标题");

        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

}
