package com.example.module_common_ui.view.textview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by wangxiong on 2018/8/28.
 */

@SuppressLint("AppCompatCustomView")
public class ScrollerTextView1 extends TextView {
    /**
     * 实现滑动的几种方法
     * 1、layout方法：调用layout方法重新定位
     */


    private int lastX;
    private int lastY;

    public ScrollerTextView1(Context context) {
        this(context, null);
    }

    public ScrollerTextView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerTextView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                layout(getLeft()+ offsetX, getTop()+offsetY, getRight() + offsetX, getBottom() + offsetY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }
}
