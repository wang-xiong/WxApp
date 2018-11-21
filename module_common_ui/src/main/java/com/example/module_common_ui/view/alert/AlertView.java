package com.example.module_common_ui.view.alert;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.module_common_ui.R;
import com.example.utils_library.AnimationUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangxiong on 2018/7/30.
 */

public class AlertView {
    public static final int HORIZONTAL_BUTTONS_MAX_COUNT = 2;
    public static final int CANCEL_POSITION = -1;//点击取消按钮返回 －1，其他按钮从0开始算



    private WeakReference<Context> mContextWeak;
    private Style style;
    private String title;
    private String msg;
    private String cancel;
    private String[] destructive;
    private String[] others;
    protected OnItemClickListener onItemClickListener;

    private List<String> mDestructive;
    private List<String> mOthers;
    private List<String> mData;

    //Activity的根View
    private ViewGroup mDecorView;
    //AlertView的根View
    private ViewGroup mRootView;
    //AlertView的contentView
    private ViewGroup mContentContainer;
    //窗口headerView
    private ViewGroup mHeaderView;

    private int gravity = Gravity.CENTER;
    private Animation outAnim;
    private Animation inAnim;

    private boolean isShowing;
    private OnDismissListener onDismissListener;

    public enum Style {
        ActionSheet,
        Alert
    }

    protected AlertView(Builder builder) {
        initData(builder);
        initView();
        initAnimation();
    }

    private void initData(Builder builder) {
        this.mContextWeak = new WeakReference<>(builder.context);
        this.style = builder.style;
        this.title = builder.title;
        this.msg =  builder.msg;
        this.cancel =  builder.cancel;
        this.destructive = builder.destructive;
        this.others =builder.others;
        this.onItemClickListener = builder.onItemClickListener;
        mData = new ArrayList<>();
        if (destructive !=  null) {
            this.mDestructive = Arrays.asList(destructive);
            mData.addAll(mDestructive);
        }

        if (others != null) {
            this.mOthers = Arrays.asList(others);
            mData.addAll(mOthers);
        }

        if (cancel != null) {
            if (style == Style.Alert && mData.size()< HORIZONTAL_BUTTONS_MAX_COUNT) {
                mData.add(0, cancel);
            }
        }
    }

    private void initView() {
        Context context = mContextWeak.get();
        if (context == null) {
            return;
        }
        mDecorView  = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        mRootView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.common_ui_layout_alert_view,  mDecorView, false);
        mRootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mContentContainer = mRootView.findViewById(R.id.content_container);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT
                , FrameLayout.LayoutParams.WRAP_CONTENT);
        int marginLeft = context.getResources().getDimensionPixelSize(R.dimen.common_ui_margin_left);
        switch (style) {
            case ActionSheet:
                gravity = Gravity.BOTTOM;
                params.gravity = gravity;
                params.setMargins(marginLeft,0, marginLeft, 0);
                mContentContainer.setLayoutParams(params);
                initActionSheetView(context);
                break;

            case Alert:
                gravity = Gravity.CENTER;
                params.gravity = gravity;
                params.setMargins(marginLeft,0,marginLeft,0);
                mContentContainer.setLayoutParams(params);
                initAlertView(context);
                break;
        }

    }

    private void initActionSheetView(Context context) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.common_ui_layout_alertview_actionsheet, mContentContainer, true);
        initHeaderView(view);
        initListView(view, context);

        TextView tvAlertCancel = view.findViewById(R.id.tvAlertCancel);
        if(cancel != null){
            tvAlertCancel.setVisibility(View.VISIBLE);
            tvAlertCancel.setText(cancel);
            tvAlertCancel.setOnClickListener(new OnTextClickListener(CANCEL_POSITION));
        }
    }

    private void initAlertView(Context context) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_alertview_alert, mContentContainer, true);
        initHeaderView(viewGroup);


        if (mData.size()<= HORIZONTAL_BUTTONS_MAX_COUNT) {
            ViewStub viewStub = mContentContainer.findViewById(R.id.viewStubHorizontal);
            viewStub.inflate();

            LinearLayout loAlertButtons = mContentContainer.findViewById(R.id.loAlertButtons);
            int position = 0;
            for (int i = 0; i < mData.size(); i ++) {
                //如果不是第一个按钮
                if (i != 0){
                    //添加上按钮之间的分割线
                    View divider = new View(context);
                    divider.setBackgroundColor(context.getResources().getColor(R.color.bgColor_divier));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)context.getResources().getDimension(R.dimen.size_divier), LinearLayout.LayoutParams.MATCH_PARENT);
                    loAlertButtons.addView(divider,params);
                }
                View itemView = LayoutInflater.from(context).inflate(R.layout.item_alertbutton, null);
                TextView tvAlert = itemView.findViewById(R.id.tvAlert);
                tvAlert.setClickable(true);

                //设置点击效果
                if(mData.size() == 1){
                    tvAlert.setBackgroundResource(R.drawable.bg_alertbutton_bottom);
                }
                else if(i == 0){//设置最左边的按钮效果
                    tvAlert.setBackgroundResource(R.drawable.bg_alertbutton_left);
                }
                else if(i == mData.size() - 1){//设置最右边的按钮效果
                    tvAlert.setBackgroundResource(R.drawable.bg_alertbutton_right);
                }
                String data = mData.get(i);
                tvAlert.setText(data);

                //取消按钮的样式
                if (data == cancel){
                    tvAlert.setTypeface(Typeface.DEFAULT_BOLD);
                    tvAlert.setTextColor(context.getResources().getColor(R.color.textColor_alert_button_cancel));
                    tvAlert.setOnClickListener(new OnTextClickListener(CANCEL_POSITION));
                    position = position - 1;
                }
                //高亮按钮的样式
                else if (mDestructive!= null && mDestructive.contains(data)){
                    tvAlert.setTextColor(context.getResources().getColor(R.color.textColor_alert_button_destructive));
                }

                tvAlert.setOnClickListener(new OnTextClickListener(position));
                position++;
                loAlertButtons.addView(itemView,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            }
        } else {
            ViewStub viewStub = (ViewStub) mContentContainer.findViewById(R.id.viewStubVertical);
            viewStub.inflate();
            initListView(viewGroup, context);
        }
    }

    private void initHeaderView(ViewGroup viewGroup){
        mHeaderView = viewGroup.findViewById(R.id.loAlertHeader);
        //标题和消息
        TextView tvAlertTitle =  viewGroup.findViewById(R.id.tvAlertTitle);
        TextView tvAlertMsg =  viewGroup.findViewById(R.id.tvAlertMsg);
        if(title != null) {
            tvAlertTitle.setText(title);
        }else{
            tvAlertTitle.setVisibility(View.GONE);
        }
        if(msg != null) {
            tvAlertMsg.setText(msg);
        }else{
            tvAlertMsg.setVisibility(View.GONE);
        }
    }

    private void initListView(ViewGroup view, Context context) {
        ListView alertButtonListView = view.findViewById(R.id.alertButtonListView);
        if(cancel != null && style == Style.Alert){
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_alertbutton, null);
            TextView tvAlert = itemView.findViewById(R.id.tvAlert);
            tvAlert.setText(cancel);
            tvAlert.setClickable(true);
            tvAlert.setTypeface(Typeface.DEFAULT_BOLD);
            tvAlert.setTextColor(context.getResources().getColor(R.color.textColor_alert_button_cancel));
            tvAlert.setBackgroundResource(R.drawable.bg_alertbutton_bottom);
            tvAlert.setOnClickListener(new OnTextClickListener(CANCEL_POSITION));

            alertButtonListView.addFooterView(itemView);
        }

        AlertViewAdapter adapter = new AlertViewAdapter(mData, mDestructive);
        alertButtonListView.setAdapter(adapter);
        alertButtonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(onItemClickListener != null) {
                    onItemClickListener.onItemClick(AlertView.this, position);
                    dismiss();
                }
            }
        });

    }

    private void initAnimation() {
        inAnim = getInAnimation();
        outAnim = getOutAnimation();
    }

    public Animation getInAnimation() {
        Context context = mContextWeak.get();
        if(context == null) {
            return null;
        }

        int res = AnimationUtil.getAnimationResource(this.gravity, true);
        return AnimationUtils.loadAnimation(context, res);
    }

    public Animation getOutAnimation() {
        Context context = mContextWeak.get();
        if(context == null) {
            return null;
        }

        int res = AnimationUtil.getAnimationResource(this.gravity, false);
        return AnimationUtils.loadAnimation(context, res);
    }

    public AlertView setCancelable(boolean isCancelable) {
        View view = mRootView.findViewById(R.id.outmost_container);

        if (isCancelable) {
            view.setOnTouchListener(onCancelableTouchListener);
        }
        else{
            view.setOnTouchListener(null);
        }
        return this;
    }

    public AlertView addExtView(View extView){
        mHeaderView.addView(extView);
        return this;
    }

    /**
     * 添加这个View到Activity的根视图
     */
    public void show() {
        if (isShowing()) {
            return;
        }
        isShowing = true;
        mDecorView.addView(mRootView);
        mContentContainer.startAnimation(inAnim);
    }

    public boolean isShowing() {
        return mRootView.getParent() != null && isShowing;
    }

    public void dismiss() {
        if (!isShowing()) {
           return;
        }
        //消失动画
        outAnim.setAnimationListener(outAnimListener);
        mContentContainer.startAnimation(outAnim);
    }

    private void dismissImmediately() {
        mDecorView.removeView(mRootView);
        isShowing = false;
        if(onDismissListener != null){
            onDismissListener.onDismiss(this);
        }

    }

    public AlertView setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    public static class Builder {
        private Context context;
        private Style style;
        private String title;
        private String msg;
        private String cancel;
        private String[] destructive;
        private String[] others;
        private OnItemClickListener onItemClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setStyle (Style style) {
            this.style = style;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder setCancel(String cancel) {
            this.cancel = cancel;
            return this;
        }

        public Builder setDestructive(String[] destructive) {
            this.destructive = destructive;
            return this;
        }

        public Builder setOthers(String[] others) {
            this.others = others;
            return this;
        }

        public Builder setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public AlertView build() {
            return new AlertView(this);
        }
    }

    class OnTextClickListener implements View.OnClickListener {
        private int position;

        public OnTextClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(AlertView.this, position);
            }
            dismiss();
        }
    }

    private Animation.AnimationListener outAnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            dismissImmediately();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismiss();
            }
            return false;
        }
    };
}
