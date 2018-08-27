package com.example.module_common_ui.view.animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by wangxiong on 2018/8/23.
 * <p>
 * PathMeasure()： 构造方法 ，实例化一个对象
 * PathMeasure(Path path,boolean isClosed)：传入Path对象和是否闭合，path对象不能为空
 * getLength()：获取当前轮廓、外形的总长度， 如果没有设置Path对象，返回0
 * getSegment(float startD,float stopD,Path dst,boolean startWithMoveTo)：
 * 调用这个方法，我们可以获取到指定范围内的一段轮廓，存入到dst参数中。
 * 所以，这个方法传入的参数分别为长度起始值、结束值、装这一段路径的Path对象、
 * 是否MoveTo。
 * 另外，这个方法返回值为Boolean类型，如果getLength为0的话，返回false，或者startD > stopD，同样返回false。
 * setPath(Path path , boolean isClosed)：给当前PathMeasure对象设置Path
 * nextContour()：移动到下一个轮廓
 */

public class PathMeasureView extends View implements ValueAnimator.AnimatorUpdateListener {
    private Paint mPaint;

    private Path mPathCircle;
    private Path mPathCircleDst;
    private Path mPathRight;
    private Path mPathRightDst;
    private Path mPathWrong1;
    private Path mPathWrong2;
    private Path mPathWrong1Dst;
    private Path mPathWrong2Dst;

    private PathMeasure mPathMeasure;

    /**
     * 线宽
     */
    private int mLineWidth;


    private ValueAnimator mCircleAnimator;
    private ValueAnimator mCircleAnimatorReset;
    private ValueAnimator mRightAnimator;
    private ValueAnimator mWrong1Animator;
    private ValueAnimator mWrong2Animator;

    /**
     * 当前绘制进度占总Path长度百分比
     */
    private float mCirclePercent;
    private float mRightPercent;
    private float mWrong1Percent;
    private float mWrong2Percent;

    /**
     * 正确动画 错误动画
     */
    public static final int RESULT_RIGHT = 1;
    public static final int RESULT_WRONG = 2;

    /**
     * 当前结果类型
     */
    private int mResultType = RESULT_WRONG;

    public PathMeasureView(Context context) {
        this(context, null);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    /**
     * 固定写死了宽高，可重新手动调配
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(dp2px(50), dp2px(50));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mResultType == RESULT_RIGHT) {
            mPaint.setColor(Color.GREEN);
        } else {
            mPaint.setColor(Color.RED);
        }

        mPathCircle.addCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - mLineWidth, Path.Direction.CW);
        mPathMeasure.setPath(mPathCircle, false);
        mPathCircleDst.reset();
        //避免4.4之前硬件加速bug
        mPathCircleDst.lineTo(0,0);
        mPathMeasure.getSegment(0, mCirclePercent * mPathMeasure.getLength(), mPathCircleDst, true);
        canvas.drawPath(mPathCircleDst, mPaint);

        if (mResultType == RESULT_RIGHT) {
            mPathRight.moveTo(getWidth() / 4, getHeight() / 2);
            mPathRight.lineTo(getWidth() / 2, getHeight() / 4 * 3);
            mPathRight.lineTo(getWidth() / 4 * 3, getWidth() / 4);
            if (mCirclePercent == 1) {
                mPathMeasure.nextContour();
                mPathMeasure.setPath(mPathRight, false);

                mPathRightDst.reset();
                mPathRightDst.lineTo(0,0);
                mPathMeasure.getSegment(0, mRightPercent * mPathMeasure.getLength(), mPathRightDst, true);
                canvas.drawPath(mPathRightDst, mPaint);
            }
        } else {
            mPathWrong1.moveTo(getWidth() / 4 * 3, getWidth() / 4);
            mPathWrong1.lineTo(getWidth() / 4, getWidth() / 4 * 3);

            mPathWrong2.moveTo(getWidth() / 4, getWidth() / 4);
            mPathWrong2.lineTo(getWidth() / 4 * 3, getWidth() / 4 * 3);

            if (mCirclePercent == 1) {
                mPathMeasure.nextContour();
                mPathMeasure.setPath(mPathWrong1, false);

                mPathWrong1Dst.reset();
                mPathWrong1Dst.lineTo(0,0);
                mPathMeasure.getSegment(0, mWrong1Percent * mPathMeasure.getLength(), mPathWrong1Dst, true);
                canvas.drawPath(mPathWrong1Dst, mPaint);
            }
            if (mWrong1Percent == 1) {
                mPathMeasure.nextContour();
                mPathMeasure.setPath(mPathWrong2, false);

                mPathWrong2Dst.reset();
                mPathWrong2Dst.lineTo(0,0);
                mPathMeasure.getSegment(0, mWrong2Percent * mPathMeasure.getLength(), mPathWrong2Dst, true);
                canvas.drawPath(mPathWrong2Dst, mPaint);
            }
        }
    }

    private void init() {
        mLineWidth = 9;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setColor(Color.GREEN);

        initPath();
    }

    private int dp2px(int dp) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (scale * dp + 0.5f);
    }


    private void initPath() {
        mPathCircle = new Path();
        mPathCircleDst = new Path();
        mPathRight = new Path();
        mPathRightDst = new Path();
        mPathWrong1 = new Path();
        mPathWrong2 = new Path();
        mPathWrong1Dst = new Path();
        mPathWrong2Dst = new Path();

        mPathMeasure = new PathMeasure();

        mCircleAnimator = ValueAnimator.ofFloat(0, 1);
        //设置时长为1000ms
        mCircleAnimator.setDuration(3000);

        //设置动画监听
        mCircleAnimator.addUpdateListener(this);

        mRightAnimator = ValueAnimator.ofFloat(0, 1);
        mRightAnimator.setDuration(500);
        mRightAnimator.addUpdateListener(this);

        mWrong1Animator = ValueAnimator.ofFloat(0, 1);
        mWrong1Animator.setDuration(300);
        mWrong1Animator.addUpdateListener(this);
        mWrong2Animator = ValueAnimator.ofFloat(0, 1);
        mWrong2Animator.setDuration(300);
        mWrong2Animator.addUpdateListener(this);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        if (animation.equals(mCircleAnimator)) {
            mCirclePercent = (float) animation.getAnimatedValue();
            invalidate();
            if (mCirclePercent == 1) {
                if (mResultType == RESULT_RIGHT)
                    mRightAnimator.start();
                else
                    mWrong1Animator.start();
            }
        }
        //正确时 对勾动画
        else if (animation.equals(mRightAnimator)) {
            mRightPercent = (float) animation.getAnimatedValue();
            invalidate();
        }
        //错误时 右侧动画
        else if (animation.equals(mWrong1Animator)) {
            mWrong1Percent = (float) animation.getAnimatedValue();
            invalidate();
            if (mWrong1Percent == 1) {
                mWrong2Animator.start();
            }
        }
        //错误时 左侧动画
        else if (animation.equals(mWrong2Animator)) {
            mWrong2Percent = (float) animation.getAnimatedValue();
            invalidate();
        }

        if (animation.equals(mCircleAnimatorReset)) {
            mCirclePercent = (float) animation.getAnimatedValue();
            invalidate();
        }
    }

    public void startValueAnimation() {
        if (mCircleAnimator.isRunning()) {
            return;
        } else if (mCircleAnimator.isPaused()) {
            mCircleAnimator.resume();
        } else {
            mCirclePercent =  0;
            mRightPercent = 0;
            mWrong1Percent = 0;
            mWrong2Percent = 0;
            mCircleAnimator.start();
        }
    }

    public void startValueAnimationReset() {
        mCircleAnimator.pause();
        mCircleAnimatorReset = ValueAnimator.ofFloat(1*mCirclePercent, 0);
        mCircleAnimatorReset.setDuration((long) (3000*mCirclePercent));
        mCircleAnimatorReset.addUpdateListener(this);
        mCircleAnimatorReset.start();

    }
}
