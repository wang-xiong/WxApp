package com.example.module_common_ui.view.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wangxiong on 2018/8/23.
 */

public class MyPointView extends View {
    private static final float RADIUS = 70f;
    private String color;
    private Paint mPaint;
    private Point mCurrentPoint;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public Point getMCurrentPoint() {
        return mCurrentPoint;
    }

    public void setMCurrentPoint(Point mCurrentPoint) {
        this.mCurrentPoint = mCurrentPoint;
        invalidate();
    }

    public MyPointView(Context context) {
        this(context, null);
    }

    public MyPointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurrentPoint != null) {
            canvas.drawCircle(mCurrentPoint.getX(), mCurrentPoint.getY(), RADIUS, mPaint);
        }

    }

    public void startValueAnimation() {
        Point startPoint = new Point(80, 80);
        Point endPoint = new Point(800, 800);

        ValueAnimator animator = ValueAnimator.ofObject(new Point.PointEvaluator(), startPoint, endPoint);
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    public void startObjectAnimation() {
        //1、设置属性get set方法(方法名必须是属性名，且首字母大写)
        //2、根据属性实现估值器
        //3、属性的set（）方法 对 属性的改变 必须通过某种方法反映出来
        Point startPoint = new Point(80, 80);
        Point endPoint = new Point(800, 800);

        ObjectAnimator animator = ObjectAnimator.ofObject(this, "mCurrentPoint", new Point.PointEvaluator(), startPoint, endPoint);
        animator.setDuration(5000);
        animator.start();
    }

    public void startObjectAnimation1() {
        ObjectAnimator anim = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(),
                "#0000FF", "#FF0000");
        // 设置自定义View对象、背景颜色属性值 & 颜色估值器
        // 本质逻辑：
        // 步骤1：根据颜色估值器不断 改变 值
        // 步骤2：调用set（）设置背景颜色的属性值（实际上是通过画笔进行颜色设置）
        // 步骤3：调用invalidate()刷新视图，即调用onDraw（）重新绘制，从而实现动画效果

        anim.setDuration(8000);
        anim.start();

    }



    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

}
