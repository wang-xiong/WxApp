package com.example.component_base.Utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by wangxiong on 2018/7/13.
 */

public class BitmapUtil {

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            return bitmapDrawable.getBitmap();
        }
        return null;
    }

    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    public static Bitmap createCircularBitmap(Bitmap source, int width) {
        if (source == null) {
            return  null;
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(width/2, width/2, width/2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source,0,0,paint);
        return bitmap;
    }
}
