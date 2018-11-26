package com.example.app_test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MyView extends LinearLayout {

    public MyView(Context context) {
        super(context);
        initView();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private Button button;

    private void initView() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        button = (Button) getChildAt(0);
        setClickable(true);
    }

    float lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float distantY = event.getRawY() - lastY;
                lastY = event.getRawY();
                ViewGroup.LayoutParams layoutParams = button.getLayoutParams();
                layoutParams.height = layoutParams.height + (int)distantY;
                button.setLayoutParams(layoutParams);
                break;

        }
        return super.onTouchEvent(event);
    }
}
