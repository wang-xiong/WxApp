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
public class ScrollerTextView extends TextView {
    /**
     * 实现滑动的几种方法
     * 2、scrollTo 与 scrollBy
     * 重点：这两个方法移动的不是view，而是移动view的content，因此在移动的view的父view去使用它
     */

    private Scroller scroller;
    private float lastX;
    private float lastY;

    public ScrollerTextView(Context context) {
        this(context, null);
    }

    public ScrollerTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        scroller = new Scroller(context);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetX = x - lastX;
                float offsetY = y - lastY;

                //重点传入相反的数
                ((View)getParent()).scrollBy((int)-offsetX, (int)-offsetY);
                break;
            case MotionEvent.ACTION_UP:
                View viewGroup = (View) getParent();

                //这里使用了 viewGroup.getScrollX() 和 viewGroup.getScrollY() 作为起始坐标，
                //ScrollY 和 ScrollX 记录了使用 scrollBy 进行偏移的量
                //所以使用他们就等于是使用了现在的坐标作为起始坐标，目的坐标为他们的负数，就是偏移量为0的位置，
                //也是view在没有移动之前的位置
                scroller.startScroll(viewGroup.getScrollX(), viewGroup.getScrollY()
                        , -viewGroup.getScrollX(), -viewGroup.getScrollY(), 800);

                //刷新view，这里很重要，如果不执行，下面的 computeScroll 方法就不会执行
                //computeScroll 方法是由 onDraw 方法调用的，而刷新 View 会调用 onDraw。
                invalidate();

        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            View viewGroup = (View) getParent();

            //使用 scrollTo 方法进行移动，参数是从 scroller 的 getCurrX 以及 getCurrY 方法得到的，
            // 这两个参数每次在执行 computeScrollOffset 之后都会改变，会越来越接近目的坐标。

            // 再次刷新 view 也等于是在循环执行此方法 直到 computeScrollOffset 判断到达目的坐标为止，
            // 循环次数和每次移动的坐标距离相关，每次移动的坐标距离又跟目的坐标的距离和动画时长有关
            //通常距离越长，动画时间越长，循环次数越多

            viewGroup.scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }
}
