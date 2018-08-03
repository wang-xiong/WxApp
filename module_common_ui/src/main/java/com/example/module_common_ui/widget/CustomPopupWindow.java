package com.example.module_common_ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 自定义popupWidow,利用build模式封装。
 * Created by wangxiong on 2018/7/25.
 */

public class CustomPopupWindow implements PopupWindow.OnDismissListener{
    private Context mContext;
    private int mResLayoutId;
    private View mRootView;
    private int mWidth;
    private int mHeight;
    private PopupWindow mPopupWindow;

    //default属性
    private boolean mFocusable = true;
    private boolean mOutsideTouchable = true;
    private boolean mClippingEnabled = true;
    private boolean mTouchable = true;
    private boolean mIgnoreCheekPress = false;
    private int mAnimationStyle = -1;
    private int mInputMode = -1;
    private int mSoftInputMode = -1;

    /**
     * 弹出PopWindow 背景是否变暗，默认不会变暗。
     */
    private boolean mIsBackgroundDark = false;
    private float mBackgroundDarkValue = 0;// 背景变暗的值，0 - 1
    private static final float DEFAULT_ALPHA = 0.7f;


    private PopupWindow.OnDismissListener mOnDismissListener;
    private View.OnTouchListener mOnTouchListener;


    private CustomPopupWindow(Context context) {
        this.mContext = context;
    }

    @Override
    public void onDismiss() {
        dismiss();
    }

    public boolean isShowing() {
        if (mPopupWindow != null) {
            return mPopupWindow.isShowing();
        }
        return false;
    }

    public void showAsDropDown(View anchor, int xOff, int yOff){
        if(mPopupWindow!=null){
            mPopupWindow.showAsDropDown(anchor,xOff,yOff);
        }
    }

    public void showAsDropDown(View anchor){
        if(mPopupWindow!=null){
            mPopupWindow.showAsDropDown(anchor);
        }
    }

    /**
     * 显示在父控件
     * @param parent 父控件
     * @param gravity 相对父控件的位置
     * @param x the popup's y location offset
     * @param y the popup's y location offset
     */
    public void showAtLocation(View parent, int gravity, int x, int y) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(parent, gravity, x, y);
        }
    }

    public void dismiss() {
        if(mOnDismissListener != null) {
            mOnDismissListener.onDismiss();
        }

        if(mIsBackgroundDark) {
            Activity activity = (Activity) mRootView.getContext();
            Window window = activity.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = 1.0f;
            window.setAttributes(lp);
        }

        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    private PopupWindow build() {
        if (mRootView == null) {
            mRootView = LayoutInflater.from(mContext).inflate(mResLayoutId, null);
        }

        //获得当前window
        Activity activity = (Activity) mRootView.getContext();
        if(activity != null && mIsBackgroundDark) {
            final  float alpha = (mBackgroundDarkValue > 0 && mBackgroundDarkValue < 1) ? mBackgroundDarkValue : DEFAULT_ALPHA;
            Window window = activity.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = alpha;
            window.setAttributes(lp);
        }

        if(mWidth != 0 && mHeight!=0 ){
            mPopupWindow = new PopupWindow(mRootView,mWidth,mHeight);
        }else{
            mPopupWindow = new PopupWindow(mRootView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        mPopupWindow.setFocusable(mFocusable);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(mOutsideTouchable);

        mPopupWindow.setClippingEnabled(mClippingEnabled);

        mPopupWindow.setTouchable(mTouchable);

        if(mIgnoreCheekPress){
            mPopupWindow.setIgnoreCheekPress();
        }

        if(mAnimationStyle!=-1){
            mPopupWindow.setAnimationStyle(mAnimationStyle);
        }

        if(mInputMode!=-1){
            mPopupWindow.setInputMethodMode(mInputMode);
        }
        if(mSoftInputMode!=-1){
            mPopupWindow.setSoftInputMode(mSoftInputMode);
        }
        if(mOnDismissListener!=null){
            mPopupWindow.setOnDismissListener(mOnDismissListener);
        }
        if(mOnTouchListener!=null){
            mPopupWindow.setTouchInterceptor(mOnTouchListener);
        }

        if(mWidth == 0 || mHeight == 0){
            //如果外面没有设置宽高的情况下，计算宽高并赋值
            mPopupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            mWidth = mPopupWindow.getContentView().getMeasuredWidth();
            mHeight = mPopupWindow.getContentView().getMeasuredHeight();
        }

        mPopupWindow.setOnDismissListener(this);
        mPopupWindow.update();

        return mPopupWindow;
    }

    public static class PopupWindowBuilder {
        private CustomPopupWindow mCustomPopupWindow;

        public PopupWindowBuilder(Context context) {
            this.mCustomPopupWindow = new CustomPopupWindow(context);
        }

        public PopupWindowBuilder size(int width, int height) {
            mCustomPopupWindow.mWidth = width;
            mCustomPopupWindow.mHeight = height;
            return this;
        }

        public PopupWindowBuilder setView(int resLayoutId){
            mCustomPopupWindow.mResLayoutId = resLayoutId;
            mCustomPopupWindow.mRootView = null;
            return this;
        }

        public PopupWindowBuilder setView(View view){
            mCustomPopupWindow.mRootView = view;
            mCustomPopupWindow.mResLayoutId = -1;
            return this;
        }

        public PopupWindowBuilder setFocusable(boolean focusable){
            mCustomPopupWindow.mFocusable = focusable;
            return this;
        }

        public PopupWindowBuilder setOutsideTouchable(boolean outsideTouchable){
            mCustomPopupWindow.mOutsideTouchable = outsideTouchable;
            return this;
        }


        public PopupWindowBuilder setClippingEnable(boolean enable){
            mCustomPopupWindow.mClippingEnabled =enable;
            return this;
        }

        public PopupWindowBuilder setIgnoreCheekPress(boolean ignoreCheekPress){
            mCustomPopupWindow.mIgnoreCheekPress = ignoreCheekPress;
            return this;
        }

        /**
         * 设置弹窗动画
         * @param animationStyle
         * @return
         */
        public PopupWindowBuilder setAnimationStyle(int animationStyle){
            mCustomPopupWindow.mAnimationStyle = animationStyle;
            return this;
        }


        public PopupWindowBuilder setInputMethodMode(int mode){
            mCustomPopupWindow.mInputMode = mode;
            return this;
        }


        public PopupWindowBuilder setSoftInputMode(int softInputMode){
            mCustomPopupWindow.mSoftInputMode = softInputMode;
            return this;
        }


        public PopupWindowBuilder setTouchable(boolean touchable){
            mCustomPopupWindow.mTouchable = touchable;
            return this;
        }

        public PopupWindowBuilder setOnDissmissListener(PopupWindow.OnDismissListener onDissmissListener){
            mCustomPopupWindow.mOnDismissListener = onDissmissListener;
            return this;
        }


        public PopupWindowBuilder setTouchInterceptor(View.OnTouchListener touchInterceptor){
            mCustomPopupWindow.mOnTouchListener = touchInterceptor;
            return this;
        }

        /**
         * 设置背景变暗是否可用
         * @param isDark
         * @return
         */
        public PopupWindowBuilder setBackgroundDark(boolean isDark){
            mCustomPopupWindow.mIsBackgroundDark = isDark;
            return this;
        }

        /**
         * 设置北京变暗的值
         * @param darkValue
         * @return
         */
        public PopupWindowBuilder setBgDarkAlpha(float darkValue){
            mCustomPopupWindow.mBackgroundDarkValue = darkValue;
            return this;
        }

        public CustomPopupWindow create() {
            mCustomPopupWindow.build();
            return mCustomPopupWindow;
        }
    }
}
