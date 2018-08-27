package com.example.module_common_ui.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by wangxiong on 2018/8/22.
 */

public class ClockView extends View {
    private static final int HOUR_LINE_HEIGHT = 35;
    private static final int MINUTE_LINE_HEIGHT = 25;
    private DrawFilter mDrawFilter;
    private Paint mCirclePaint;
    private int mCenterX;
    private int mCenterY;
    private int mCenterRadius;

    private Paint mLinePaint;
    private Paint mTextPaint;

    // 圆环线宽度
    private int mCircleLineWidth;
    // 直线刻度线宽度
    private int mHourLineWidth, mMinuteLineWidth;

    // 时针长度
    private int mHourLineHeight;
    // 分针长度
    private int mMinuteLineHeight;

    // 刻度线的左、上位置
    private int mLineLeft, mLineTop;

    // 刻度线的下边位置
    private int mLineBottom;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        mCircleLineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics());
        mHourLineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                getResources().getDisplayMetrics());
        mMinuteLineWidth = mHourLineWidth / 2;

        mHourLineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                HOUR_LINE_HEIGHT,
                getResources().getDisplayMetrics());
        mMinuteLineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                MINUTE_LINE_HEIGHT,
                getResources().getDisplayMetrics());
        initPaint();
    }

    private void initPaint() {
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mCircleLineWidth);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿标志。
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mLinePaint.setStrokeWidth(mHourLineWidth);

        mTextPaint = new Paint();
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(60);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStrokeWidth(5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("wx", "onMeasure");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("wx", "onDraw");
        super.onDraw(canvas);
        canvas.setDrawFilter(mDrawFilter);//抗锯齿标志,与给Paint设置一样
        drawCircle(canvas);
        drawLines(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("wx", "onSizeChanged");
        mCenterX = w/2;
        mCenterY = h/2;
        mCenterRadius = Math.min(mCenterX, mCenterY) - mCircleLineWidth/2;
    }

    private void drawLines(Canvas canvas) {
        for (int degree = 0; degree <= 360; degree++) {
            if (degree % 30 == 0) {
                mLineLeft = mCenterX - mHourLineWidth/2;
                mLineTop = mCenterY - mCenterRadius;
                mLineBottom = mLineTop + mHourLineHeight;
                mLinePaint.setStrokeWidth(mHourLineWidth);
            } else {
                mLineLeft = mCenterX - mMinuteLineWidth/2;
                mLineTop = mCenterY - mCenterRadius;
                mLineBottom = mLineTop + mMinuteLineHeight;
                mLinePaint.setStrokeWidth(mMinuteLineWidth);
            }

            if (degree % 6 == 0) {
                //保存当前画布的状态
                canvas.save();
                //以圆心为坐标开始旋转
                canvas.rotate(degree, mCenterX, mCenterY);
                //绘制直线
                canvas.drawLine(mLineLeft, mLineTop, mLineLeft, mLineBottom, mLinePaint);
                //恢复画布到上一次状态以便下次旋转绘制
                canvas.restore();
            }
        }
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY, mCenterRadius, mCirclePaint);
        canvas.drawText("时钟", mCenterX, mCenterY, mTextPaint);
    }

    //https://blog.csdn.net/moira33/article/details/79111343#paint%E7%9A%84%E5%9F%BA%E6%9C%AC%E8%AE%BE%E7%BD%AE%E5%87%BD%E6%95%B0
}
