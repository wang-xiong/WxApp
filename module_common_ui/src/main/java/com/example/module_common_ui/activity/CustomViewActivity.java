package com.example.module_common_ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.module_common_ui.R;
import com.example.module_common_ui.view.animation.ClockView1;
import com.example.module_common_ui.view.animation.LineView;
import com.example.module_common_ui.view.animation.MyPointView;
import com.example.module_common_ui.view.animation.PathMeasureView;

/**
 * Created by wangxiong on 2018/8/22.
 */

public class CustomViewActivity extends BaseMvcActivity {
    private Button startBtn;
    private Button stopBtn;
    private PathMeasureView pathMeasureView;
    private LineView lineView;
    private ClockView1 clockView1;
    private MyPointView pointView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_ui_activity_mycustomview);
        pathMeasureView = findViewById(R.id.path_measure_view);
        lineView = findViewById(R.id.line_view);
        pointView = findViewById(R.id.point_view);
        clockView1 = findViewById(R.id.clock1_view);

        startBtn = findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pathMeasureView.startValueAnimation();
                lineView.startAnimation();
                clockView1.startAnimation();
                //pointView.startValueAnimation();
                pointView.startObjectAnimation();
                pointView.startObjectAnimation1();
            }
        });
        stopBtn = findViewById(R.id.stop_btn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pathMeasureView.startValueAnimationReset();

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.e("wx", "x:" + event.getX());
                Log.e("wx", "y:" + event.getY());
                event.getY();
                break;
        }
        return super.onTouchEvent(event);
    }
}