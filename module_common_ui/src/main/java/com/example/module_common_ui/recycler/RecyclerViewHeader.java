package com.example.module_common_ui.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.module_common_ui.R;

public class RecyclerViewHeader extends LinearLayout {

    public final static int STATE_IDLE = 0; // 正常状态
    public final static int STATE_READY = 1; // 准备刷新状态
    public final static int STATE_REFRESH = 2; // 正在刷新状态
    private int mState = STATE_IDLE; //当前状态
    private TextView headerHit;

    public RecyclerViewHeader(Context context) {
        super(context);
        initView(context);
    }

    public RecyclerViewHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RecyclerViewHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        inflate(context, R.layout.pullrefrefh_recyclerview_header, this);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, 0);
        }
        setLayoutParams(layoutParams);

        headerHit = findViewById(R.id.pull_header_hint_Tv);
        headerHit.setText("下拉刷新");
    }

    public void setState(int state) {
        if (mState == state) {
            return;
        }
        if (state == STATE_REFRESH) {
            headerHit.setTextColor(0xFF2323EE);
        } else {
            headerHit.setTextColor(0xFF999999);
        }
        switch (state) {
            case STATE_IDLE:
                headerHit.setText("下拉刷新");
                break;
            case STATE_READY:
                headerHit.setText("松开刷新数据");
                break;
            case STATE_REFRESH:
                headerHit.setText("正在加载...");
                break;
            default:
                break;
        }
        mState = state;
    }

    public void setVisibleHeight(int height) {
        if (height < 0) {
            height = 0;
        }
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = height;
        setLayoutParams(params);
    }

    public int getVisibleHeight() {
        return getLayoutParams().height;
    }
}
