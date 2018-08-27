package com.example.module_common_ui.view.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wangxiong on 2018/8/15.
 */

public class MyView extends View {

    //来源https://www.jianshu.com/p/95afeb7c8335
    /**
     * 1、View的位置描述
     * 一个View顺时针四个顶点A、B、C、D
     * Top：子View上边界到父view上边界的距离
     * Left：子View左边界到父view左边界的距离
     * Bottom：子View下边距到父View上边界的距离
     * Right：子View右边界到父view左边界的距离
     * 2、View位置获取方式
     * getTop()        //获取子View右上角距父View顶部的距离
     * getLeft();      //获取子View左上角距父View左侧的距离
     * getBottom();    //获取子View右下角距父View顶部的距离
     * getRight();     //获取子View右下角距父View左侧的距离
     * 3、MotionEvent：get getRaw
     * A:event.getX()、event.getX() 表示触摸点相对其组件所在坐标系的坐标
     * B:event.getRawX()、event.getRawY() 表示触摸点相对屏幕坐标系的坐标
     * 4、角度与弧度，angle，radian
     * 在Android中屏幕坐标系中角度增大方向为顺时针，数学系中为逆时针
     * 5、Android的四种颜色模式：
     * ARGB8888, ARGB4444, RGB565, Alpha8
     *
     */

    /**
     * 1、MeasureSpec
     * 测量规格= 测量模式+测量大小 总共32位，前两位是测量模式
     * 测量模式分为：UNSPECIFIED、EXACTLY、AT_MOST
     * UNSPECIFIED:父View不约束子View的大小，例如ListView ScrollView
     * EXACTLY:父View为子View指定了一个确切的大小，子View必须在改尺寸内，例如match_parent 100dp,
     * AT_MOST:父View为子View指定了一个最大尺寸，例如wrap_content
     * 2、MeasureSpec值的计算
     * 子View的MeasureSpec是更加子View的布局参数LayoutParams和父View的MeasureSpec，计算方法getChildMeasureSpec
     * 然后view的MeasureSpec决定view的尺寸
     */


    /**
     * 1、measure过程
     * 单一View：只测量自身一个View
     * ViewGroup:对ViewGroup视图中的所有View进行测量，再合并
     */

    /**
     * 2、Layout
     * 作用：计算View四个顶点的位置
     * layout完成父View的位置测量 调用setFrame
     * onLayout确定子View在父容器的位置
     * getMeasuredWidth measure过程时setMeasureDimension()赋值
     * getWidth layout过程时layout（）四个参数计算的值
     * 非人为设置的情况下，View的最终宽/高（getWidth() / getHeight()）
     * 与 View的测量宽/高 （getMeasuredWidth() /  getMeasuredHeight()）永远是相等
     */

    /**
     * 3、Draw
     * draw()、drawBackground（）、onDraw()、dispatchDraw()、onDrawScrollBars(canvas)
     * 绘制自身View,绘制自身View背景，绘制自身View内容，绘制子View、绘制装饰
     */

    /**
     * 1、int mScrollX = getScrollX();
     * mScrollX表示的是view的左边缘到view的内容的左边缘的水平距离
     * mScrollY表示的是view的上边缘与view内容的上边缘的竖直方向的距离。
     * mScrollX 代表当前View左上角在父View的左边界的相对值
     * 0表示view左边界与父类左边界重合，正数代表左移，负数代表右移
     * 2、scrollTo(x,y);
     * 表示绝对位置，即参数传入的就是mScrollX，和mScrollY
     * 3、scrollBy(x, y);
     * 调用->scrollTo(mScrollX + x, mScrollY + y);
     * 即表示的是相对距离
     */


    //如果是Java中代码new的则调用第一个构造函数
    public MyView(Context context) {
        super(context);
    }

    // 如果View是在.xml里声明的，则调用第二个构造函数
    // 自定义属性是从AttributeSet参数传进来的
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    // 不会自动调用
    // 一般是在第二个构造函数里主动调用
    // 如View有style属性时
    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //API21之后才使用
    // 不会自动调用
    // 一般是在第二个构造函数里主动调用
    // 如View有style属性时
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int mScrollX = getScrollX();
        scrollTo(0, 0);
        scrollBy(0, 0);
        return super.onTouchEvent(event);
    }

    /**
     * Paint
     * Canvas
     */
    private void paint() {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);  //设置是否抗锯齿，设置后会使边缘更清楚
//        Paint.Style.FILL_AND_STROKE 填充且描边
//        Paint.Style.STROKE 描边
//        Paint.Style.FILL 填充
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE); //设置填充样式
        mPaint.setColor(Color.GREEN); //设置画笔颜色
        mPaint.setStrokeWidth(2);  //设置画笔宽度
        mPaint.setShadowLayer(10, 15, 15, Color.RED); //设置阴影

        Canvas canvas = new Canvas();

        //1、画集合图形
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawLine(100, 100, 600, 600, mPaint);

        float[] pts = {50, 100, 100, 200, 200, 300, 300, 400};
        mPaint.setColor(Color.RED);
        canvas.drawLines(pts, mPaint);
        //两条线（50，100）到（100，200）（200，300）到（300，400）

        mPaint.setColor(Color.BLUE);
        canvas.drawLines(pts, 1, 4, mPaint);
        //去掉第一个数50，取之后的4个数即100,100,200,200

        canvas.drawPoint(100, 100, mPaint);

        //画矩形RectF是浮点型，Rect是整形
        RectF rectF = new RectF(100, 20, 500, 400);
        canvas.drawRect(rectF, mPaint);

        //画圆角矩形
        canvas.drawRoundRect(rectF, 20, 20, mPaint);


        //画圆形
        canvas.drawCircle(100, 100, 40, mPaint);


        //画椭圆
        canvas.drawOval(rectF, mPaint);

        //画圆弧，生成椭圆的矩形、开始的角度，绘制的角度(顺时针绘制），是否有弧的两边，以圆心画数学坐标轴，逆时针为负，顺时针为正
        canvas.drawArc(rectF, 30, 30, false, mPaint);


        //路劲
        Path path = new Path();

        //默认从原点开始，画直线
        path.lineTo(200, 200);
        path.lineTo(400, 0);

        //移动下一次操作的起始点，不影响之前的操作，影响之后的操作
        path.moveTo(400, 100);
        path.lineTo(500, 100);

        //改变上一次操作的位置，相当与重置上一次moveTo，影响之前的操作，影响之后的操作
        path.setLastPoint(500, 200);
        canvas.drawPath(path, mPaint);

        //close连接最后一个点和最初一个点，形成一个闭合的图形，如果无法形成闭合图形则什么不操作
        path.moveTo(100, 100);
        path.lineTo(500, 100);
        path.lineTo(300, 400);
        path.close();
        canvas.drawPath(path, mPaint);


        //添加基本图形 Path.Direction.CW //顺时针 Path.Direction.CCW //逆时针
//        //圆形
//        addCircle(float x, float y, float radius, Path.Direction dir)
//        //椭圆
//        addOval(RectF oval, Path.Direction dir)
//        addOval(float left, float top, float right, float bottom, Path.Direction dir)
//        //矩形
//        addRect(RectF rect, Path.Direction dir)
//        addRect(float left, float top, float right, float bottom, Path.Direction dir)
//        //圆角矩形
//        addRoundRect(RectF rect, float rx, float ry, Path.Direction dir)
//        addRoundRect(float left, float top, float right, float bottom, float rx, float ry, Path.Direction dir)
//        addRoundRect(RectF rect, float[] radii, Path.Direction dir)
//        addRoundRect(float left, float top, float right, float bottom, float[] radii, Path.Direction dir)

        //画圆
        path.addCircle(300, 300, 200, Path.Direction.CW);//（300,300）点表示圆心坐标，200 表示半径长度
        canvas.drawPath(path, mPaint);

        //将两个路径合并到一起
        Path path1 = new Path();
        path.addRect(100, 100, 400, 300, Path.Direction.CW);
        Path src = new Path();
        src.addCircle(300, 300, 100, Path.Direction.CW);
        path1.addPath(src, 0, 100);

        canvas.drawPath(path, mPaint);


        //文字
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(80);
//设置绘图样式 为填充
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("我是一颗小小的石头", 100, 100, mPaint);

//设置绘图样式 为描边
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawText("我是一颗小小的石头", 100, 300, mPaint);

//设置绘图样式 为填充且描边
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText("我是一颗小小的石头", 100, 500, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(80);
//设置对齐方式  左对齐
        mPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("小小的石头", 500, 100, mPaint);//点（500,100）在文本的左边

//设置对齐方式  中间对齐
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("小小的石头", 500, 200, mPaint);//点（500,100）在文本的中间

//设置对齐方式  右对齐
        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("小小的石头", 500, 300, mPaint);//点（500,100）在文本的右边

//        1、每次调用canvas.draw**XXXX系列函数来绘图进，都会产生一个全新的Canvas画布**。
//        2、如果在DrawXXX前，调用平移、旋转等函数来对Canvas进行了操作，那么这个操作是不可逆的！每次产生的画布的最新位置都是这些操作后的位置。（关于Save()、Restore()的画布可逆问题的后面再讲）
//        3、在Canvas与屏幕合成时，超出屏幕范围的图像是不会显示出来的。
    }
}
