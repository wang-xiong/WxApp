package com.example.app_test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class ViewGroup2 extends RelativeLayout {
    private static final String TAG = ViewGroup2.class.getSimpleName();

    public ViewGroup2(Context context) {
        super(context);
    }

    public ViewGroup2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroup2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "dispatchTouchEvent ACTION_DOWN");
                return true;
                //break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "dispatchTouchEvent ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onInterceptTouchEvent ACTION_DOWN");
                //return true;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onInterceptTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onInterceptTouchEvent ACTION_UP");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onTouchEvent ACTION_DOWN");
                //return true;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onTouchEvent ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}

/**
 * 1.ViewGroup
 * public boolean dispatchTouchEvent(MotionEvent event) {
 *         if(onInterceptTouchEvent(event)){//是否拦截
 *             return onTouchEvent(event);
 *         }
 *         //没有拦截
 *         if(child==null){
 *             //没有子控件
 *             return onTouchEvent(event);
 *         }else{
 *              //执行子控件的dispatchTouchEvent
 *             boolean consume= child.dispatchTouchEvent(event);
 *             if(!consume){//子控件没有消费事件，执行当前view的onTouchEvent
 *                 return onTouchEvent(event);
 *             }else{
 *                 return false;
 *             }
 *         }
 * }
 * 1.ViewGroup
 * 对于ACTION_DOWN
 * (dispatchTouchEvent onInterceptTouchEvent onTouchEvent都不消费(都返回false)):
 * 后续事件不下发，执行activity的onTouchEvent
 * dispatchTouchEvent 返回true; onInterceptTouchEvent onTouchEvent 接受不到ACTION_DOWN，但可以接受后续事件
 *
 *
 */
