package com.example.module_common_ui.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.module_common_ui.R;

public class RecyclerViewFooter extends LinearLayout {
    public final static int STATE_IDLE = 0; // 正常状态
    public final static int STATE_READY = 1; // 准备刷新状态
    public final static int STATE_REFRESH = 2; // 正在刷新状态
    private int mState = STATE_IDLE; //当前状态

    private LinearLayout contentView;
    private TextView hintView;
    private ProgressBar progressBar;

    public RecyclerViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public RecyclerViewFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RecyclerViewFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.pullrefrefh_recyclerview_footer, this);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        setLayoutParams(layoutParams);
        contentView = findViewById(R.id.footer_content);
        hintView = findViewById(R.id.pull_footer_hint_tv);
        progressBar = findViewById(R.id.pull_footer_pb);
    }

    /**
     * 设置状态
     *
     * @param state
     */
    public void setState(int state) {
        if (mState == state) {
            return;
        }

        if (state == STATE_READY) {
            hintView.setText("松开载入更多");
            hintView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else if (state == STATE_REFRESH) {
            hintView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            hintView.setText("查看更多");
            hintView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
        mState = state;
    }

    /**
     * 设置距离下边的BottomMargin
     *
     * @param height
     */
    public void setBottomMargin(int height) {
        if (height < 0) {
            return;
        }
        LinearLayout.LayoutParams lp = (LayoutParams) contentView.getLayoutParams();
        lp.bottomMargin = height;
        contentView.setLayoutParams(lp);

    }

    /**
     * 获取BottomMargin
     *
     * @return
     */
    public int getBottomMargin() {
        LinearLayout.LayoutParams lp = (LayoutParams) contentView.getLayoutParams();
        return lp.bottomMargin;
    }


    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        LayoutParams lp = (LayoutParams) contentView.getLayoutParams();
        lp.height = 0;
        contentView.setLayoutParams(lp);
    }

    /**
     * show footer
     */
    public void show() {
        LayoutParams lp = (LayoutParams) contentView.getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        contentView.setLayoutParams(lp);
    }
}
