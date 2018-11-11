package com.example.module_common_ui.view.shadow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;


public class ShadowLayout extends LinearLayout {

    private Paint mPaint;

    public ShadowLayout(Context context) {
        this(context, null);
        init();
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    int mBackgroundColor = Color.WHITE;
    int mShadowRadius = 8;
    int mRadius = 9;
    int mDx = 0;
    int mDy = 3;

    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();

        mPaint.setColor(mBackgroundColor);
        mPaint.setAntiAlias(true);

        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mRectF = new RectF();
        setWillNotDraw(false);

    }

    private RectF mRectF;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制阴影，param1：模糊半径；param2：x轴大小：param3：y轴大小；param4：阴影颜色
        mPaint.setShadowLayer(dip2px(getContext(), mShadowRadius), dip2px(getContext(), mDx), dip2px(getContext(), mDy), Color.argb(90, 80, 00, 00));
        mRectF.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        //mRectF.set(0, 0, getWidth(), getHeight());

        canvas.drawRoundRect(mRectF, dip2px(getContext(), mRadius), dip2px(getContext(), mRadius), mPaint);
    }

    public static int dip2px(Context context, float dipValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5f);
        } catch (Exception e) {
        }
        return 0;
    }
}

