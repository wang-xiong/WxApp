package com.example.module_common_ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.component_base.base.mvc.BaseMvcActivity;
import com.example.module_common_ui.R;

public class TestActivity extends BaseMvcActivity implements View.OnTouchListener, View.OnClickListener {
    private static final String TAG = TestActivity.class.getSimpleName();
    private TextView mMyTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_ui_activity_test);

        mMyTextView = findViewById(R.id.my_tv);
        mMyTextView.setOnTouchListener(this);
        //mMyTextView.setOnClickListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        /**
         * 1.返回super,继续分发（见MyTextView）
         * 2.返回true或者false，不分发触摸事件
         */
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "dispatchTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "dispatchTouchEvent ACTION_CANCEL");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "onTouchEvent ACTION_CANCEL");
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        if (R.id.my_tv == v.getId()) {
            Log.e(TAG, "MyTextView onClick");
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /**
         * 1.返回false，继续分发到MyTextView的onTouchEvent方法
         * 2.返回true，不继分发，也不会调用MyTextView的onClick方法
         */
        if (R.id.my_tv == v.getId()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.e(TAG, "MyTextView onTouch ACTION_DOWN");
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.e(TAG, "MyTextView onTouch ACTION_MOVE");
                    break;
                case MotionEvent.ACTION_UP:
                    Log.e(TAG, "MyTextView onTouch ACTION_UP");
                    break;
                case MotionEvent.ACTION_CANCEL:
                    Log.e(TAG, "MyTextView onTouch ACTION_CANCEL");
                    break;
            }
        }
        return false;
    }
}
