package com.example.autoregister_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.base_interface.ComponentManager;
import com.example.model_a.IOther;
import com.example.model_a.OtherManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text = findViewById(R.id.test);
        StringBuilder sb = new StringBuilder();

        sb.append("-----遍历IComponent开始--------\n");
        for(String s : ComponentManager.getComponentNames()) {
            sb.append(s);
            sb.append("\n");
        }

        sb.append("-----遍历IComponent结束--------\n");

        sb.append("-----遍历IOther结束--------\n");
        for (IOther other : new OtherManager().getALL()) {
            sb.append(other.getOtherName());
            sb.append("\n");
        }
        sb.append("-----遍历IOther结束--------\n");

        text.setText(sb.toString());
    }
}
