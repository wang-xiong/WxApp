package com.example.module_common_ui.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wangxiong on 2018/8/22.
 */

public class RectView extends View{
    public RectView(Context context) {
        super(context);
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);

        int l = 10;
        int t = 10;
        int r = 410;
        int b = 410;

        int space = 60;

        Rect rect = new Rect(l,t,r,b);
        int count = (r-l)/space;

        float px = l + (r -l)/2;
        float py = t + (b -t)/2;
        for (int i = 1; i<=count ;i++) {
            canvas.save();
            float scale = (float) i/count;
            canvas.scale(scale, scale, px, py);
            canvas.drawRect(rect, paint);
            canvas.restore();
        }

    }
}
