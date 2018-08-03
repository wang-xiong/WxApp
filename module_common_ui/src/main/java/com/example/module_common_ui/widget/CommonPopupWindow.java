package com.example.module_common_ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by wangxiong on 2018/7/25.
 */

public class CommonPopupWindow extends PopupWindow {
    private final PopupController controller;

    private CommonPopupWindow(Context context) {
        this.controller = new PopupController(context, this);
    }

    @Override
    public int getWidth() {
        return controller.mPopupView.getMeasuredWidth();
    }

    @Override
    public int getHeight() {
        return controller.mPopupView.getMeasuredHeight();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        controller.setBackGroundLevel(1.0f);
    }

    public static class Builder {
        private Context mContext;
        private PopupController.PopupParams params;

        public Builder(Context context) {
            this.mContext = context;
            this.params = new PopupController.PopupParams();
        }

        public Builder setView(int resLayoutId) {
            params.mLayoutResId = resLayoutId;
            params.mContentView = null;
            return  this;
        }

        public Builder setView(View contentView) {
            params.mLayoutResId = 0;
            params.mContentView = contentView;
            return  this;
        }

        public Builder setWidthAndHeight(int width, int height) {
            params.mWidth = width;
            params.mHeight = height;
            return this;
        }

        public Builder setBackGroundAlpha(float alpha) {
            params.isShowBg = true;
            params.bgAlpha = alpha;
            return this;
        }

        public Builder setOutsideTouchable(boolean touchable) {
            params.isTouchable = touchable;
            return this;
        }

        public Builder setAnimationStyle(int animationStyle) {
            params.isShowAnim = true;
            params.animationStyle = animationStyle;
            return this;
        }

        public CommonPopupWindow create() {
            CommonPopupWindow popupWindow = new CommonPopupWindow(mContext);
            params.apply(popupWindow.controller);
            measureWidthAndHeight(popupWindow.controller.mPopupView);
            return popupWindow;
        }

        public static void measureWidthAndHeight(View view) {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
        }
    }

    static class PopupController{
        private Context context;
        private PopupWindow popupWindow;
        private View mPopupView;

        public PopupController(Context context, PopupWindow popupWindow) {
            this.context = context;
            this.popupWindow = popupWindow;
        }

        private void setView(int layoutResId) {
            mPopupView  = LayoutInflater.from(context).inflate(layoutResId, null);
            popupWindow.setContentView(mPopupView);
        }

        private void setView(View mContentView) {
            mPopupView = mContentView;
            popupWindow.setContentView(mPopupView);
        }

        private void setWidthAndHeight(int width, int height) {
            if (width == 0 || height == 0) {
                //如果没设置宽高，默认是WRAP_CONTENT
                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                popupWindow.setWidth(width);
                popupWindow.setHeight(height);
            }
        }

        private void setOutsideTouchable(boolean touchable) {
            popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置透明背景
            popupWindow.setOutsideTouchable(touchable);//设置outside可点击
            popupWindow.setFocusable(touchable);
        }

        private void setBackGroundLevel(float level) {
            Window window = ((Activity) context).getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.alpha = level;
            window.setAttributes(params);
        }

        private void setAnimationStyle(int animationStyle) {
            popupWindow.setAnimationStyle(animationStyle);
        }

        static class PopupParams {
            public View mContentView;
            public int mLayoutResId;
            public int mWidth, mHeight;
            public boolean isTouchable = true;
            public boolean isShowBg;
            public float bgAlpha;
            public boolean isShowAnim;
            public int animationStyle;

            public void apply(PopupController controller) {
                if (mContentView != null) {
                    controller.setView(mContentView);
                } else if (mLayoutResId != 0) {
                    controller.setView(mLayoutResId);
                } else {
                    throw new IllegalArgumentException("PopupView's contentView is null");
                }
                controller.setWidthAndHeight(mWidth, mHeight);
                controller.setOutsideTouchable(isTouchable);
                if (isShowBg) {
                    //设置背景
                    controller.setBackGroundLevel(bgAlpha);
                }
                if (isShowAnim) {
                    controller.setAnimationStyle(animationStyle);
                }
            }
        }

    }
}
