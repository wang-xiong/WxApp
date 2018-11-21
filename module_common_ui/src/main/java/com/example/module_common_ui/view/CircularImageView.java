package com.example.module_common_ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.example.utils_library.BitmapUtil;


/**
 * Created by wangxiong on 2018/7/26.
 */

public class CircularImageView extends AppCompatImageView {
    // 设置外圈的宽度
    private int mBorderWidth = 5;
    private int mViewWidth;
    private int mViewHeight;
    private Paint mBorderPaint;

    public CircularImageView(Context context) {
        this(context, null);
    }

    public CircularImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化背景画笔
        mBorderPaint = new Paint();
        mBorderPaint.setColor(Color.WHITE);
        mBorderPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mViewWidth = width - (mBorderWidth * 2);
        mViewHeight = height - (mBorderWidth * 2);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //去掉父类的绘制，否则有重影
        //super.onDraw(canvas);
        Bitmap bitmap = loadBitmap();
        if (bitmap != null) {
            int circleCenter = mViewWidth / 2;
            int min = Math.min(mViewWidth, mViewHeight);
            bitmap = Bitmap.createScaledBitmap(bitmap, min, min, false);
            canvas.drawCircle(circleCenter + mBorderWidth, circleCenter + mBorderWidth, circleCenter + mBorderWidth, mBorderPaint);
            canvas.drawBitmap(BitmapUtil.createCircularBitmap(bitmap, min), mBorderWidth, mBorderWidth, null);
        }
    }

    private Bitmap loadBitmap() {
        Drawable drawable = this.getDrawable();
        if (drawable != null) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth()
                    , drawable.getIntrinsicHeight()
                    , drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        }
        return null;
    }

}
