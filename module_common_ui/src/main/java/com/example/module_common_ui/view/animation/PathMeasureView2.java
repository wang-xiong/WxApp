package com.example.module_common_ui.view.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wangxiong on 2018/8/23.
 */

public class PathMeasureView2 extends View {


    private int mLineWidth;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private Path mPathCircle;
    private Path mPathCircleDst;
    private float mCirclePercent;
    private int mResultType = RESULT_WRONG;

    /**
     * 正确动画 错误动画
     */
    public static final int RESULT_RIGHT = 1;
    public static final int RESULT_WRONG = 2;

    public PathMeasureView2(Context context) {
        this(context, null);
    }

    public PathMeasureView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasureView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        //setClickable(true)设置此属性才可以监听onTouchEvent， ACTION_MOVE事件
        setClickable(true);
    }


    private void init() {
        mLineWidth = 9;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setColor(Color.GREEN);

        mPathCircle = new Path();
        mPathCircleDst = new Path();

        mPathMeasure = new PathMeasure();
    }


    private int mWidth;
    private int mHeight;

    /**
     * 固定写死了宽高，可重新手动调配
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        //setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mResultType == RESULT_RIGHT) {
            mPaint.setColor(Color.GREEN);
        } else {
            mPaint.setColor(Color.RED);
        }

        mPathCircle.addCircle(mWidth / 2, mWidth / 2, mWidth / 2 - mLineWidth, Path.Direction.CW);
        mPathMeasure.setPath(mPathCircle, false);
        mPathCircleDst.reset();
        //避免4.4之前硬件加速bug
        mPathCircleDst.lineTo(0, 0);
        mPathMeasure.getSegment(0, mCirclePercent * mPathMeasure.getLength(), mPathCircleDst, true);
        canvas.drawPath(mPathCircleDst, mPaint);

    }

    private float downX;
    private float downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float XDistance = event.getX() - downX;
                float YDistance = event.getY() - downY;

                mCirclePercent = event.getY()/mHeight;
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

}
