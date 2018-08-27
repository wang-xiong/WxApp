package com.example.module_common_ui.view.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wangxiong on 2018/8/23.
 */

public class LineView extends View {
    private final Point startPoint1 = new Point(100, 300);
    private final Point startPoint2 = new Point(300, 300);
    private final Point startPoint3 = new Point(500, 100);
    private final Point startPoint4 = new Point(700, 300);
    private final Point startPoint5 = new Point(900, 300);

    private Point pathPoint1;
    private Point pathPoint2;
    private Point pathPoint3;
    private Point pathPoint4;



    public Point getPathPoint1() {
        return pathPoint1;
    }

    public void setPathPoint1(Point pathPoint1) {
        this.pathPoint1 = pathPoint1;
        invalidate();
    }

    public Point getPathPoint2() {
        return pathPoint2;
    }

    public void setPathPoint2(Point pathPoint2) {
        this.pathPoint2 = pathPoint2;
        invalidate();
    }

    public Point getPathPoint3() {
        return pathPoint3;
    }

    public void setPathPoint3(Point pathPoint3) {
        this.pathPoint3 = pathPoint3;
        invalidate();
    }

    public Point getPathPoint4() {
        return pathPoint4;
    }

    public void setPathPoint4(Point pathPoint4) {
        this.pathPoint4 = pathPoint4;
        invalidate();
    }

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);

        Path path = new Path();

        if (isAnimationStarted) {
            path.moveTo(startPoint1.getX(), startPoint1.getY());
            drawLineTo(path, startPoint1, pathPoint1, startPoint1);
            drawLineTo(path, pathPoint1, pathPoint2, startPoint2);
            drawLineTo(path, pathPoint2, pathPoint3, startPoint3);
            drawLineTo(path, pathPoint3, pathPoint4, startPoint4);
            canvas.drawPath(path, paint);
        }
    }

    /**
     * @param path
     * @param lastPathPoint    上一条路径上的点
     * @param nextPathPoint    下一条路径上的点
     * @param lastPathEndPoint 上一条路径的终点
     */
    private void drawLineTo(Path path, Point lastPathPoint, Point nextPathPoint, Point lastPathEndPoint) {
        if (lastPathPoint == null || nextPathPoint == null) {
            return;
        }
        if (lastPathPoint.getX() == lastPathEndPoint.getX() && lastPathPoint.getY() == lastPathEndPoint.getY()) {
            path.lineTo(nextPathPoint.getX(), nextPathPoint.getY());
        }
    }

    private boolean isAnimationStarted;

    public void startAnimation() {
        pathPoint1 = null;
        pathPoint2 = null;
        pathPoint3 = null;
        pathPoint4 = null;
        ObjectAnimator animatorPoint1 = getPointAnimation("pathPoint1", startPoint1, startPoint2);
        ObjectAnimator animatorPoint2 = getPointAnimation("pathPoint2", startPoint2, startPoint3);
        ObjectAnimator animatorPoint3 = getPointAnimation("pathPoint3", startPoint3, startPoint4);
        ObjectAnimator animatorPoint4 = getPointAnimation("pathPoint4", startPoint4, startPoint5);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.playSequentially(animatorPoint1, animatorPoint2, animatorPoint3, animatorPoint4);
        animatorSet.start();

        isAnimationStarted = true;
    }

    public ObjectAnimator getPointAnimation(String property, Point starPoint, Point endPoint) {
        ObjectAnimator animator = ObjectAnimator.ofObject(this, property, new Point.PointEvaluator(), starPoint, endPoint);
        animator.setDuration(5000);
        return animator;
    }


    private void start() {
        Point startPoint1 = new Point(100, 100);
        Point endPoint1 = new Point(300, 300);

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new Point.PointEvaluator(), startPoint1, endPoint1);
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pathPoint1 = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();

        Point startPoint2 = new Point(300, 300);
        Point endPoint2 = new Point(500, 100);
        ValueAnimator valueAnimator2 = ValueAnimator.ofObject(new Point.PointEvaluator(), startPoint2, endPoint2);
        valueAnimator2.setDuration(5000);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pathPoint2 = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueAnimator2.setStartDelay(5000);
        valueAnimator2.start();
    }
}

