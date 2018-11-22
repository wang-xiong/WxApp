package com.example.module_common_ui.view.scrollview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;


import com.example.utils_library.log.WLog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangxiong on 2018/8/15.
 */

public class MyHorizontalScrollView extends HorizontalScrollView implements View.OnClickListener {
    private static final String TAG = MyHorizontalScrollView.class.getSimpleName();
    private Context mContext;
    private int mScreenWidth;
    private int mOneScreenCount; //每屏加载的数量
    private int mChildWidth;
    private int mChildHeight;
    private int mFirstIndex; //当前第一个child的下标
    private int mCurrentIndex; //当前最后一个child的下标
    private Map<View, Integer> mViewPos = new HashMap<>();
    private LinearLayout mContainer;  //容器
    private HorizontalScrollViewAdapter mAdapter;
    private CurrentImageChangeListener mChangeListener;
    private OnItemClickListener mClickListener;


    public MyHorizontalScrollView(Context context) {
        this(context, null);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        setOverScrollMode(OVER_SCROLL_NEVER);
        setHorizontalScrollBarEnabled(false);
        mContainer = new LinearLayout(getContext());
        mContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mContainer);


        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
    }

    public void setCurrentImageChangeListener(CurrentImageChangeListener listener) {
        this.mChangeListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }

    public void setAdapter(HorizontalScrollViewAdapter adapter) {
        this.mAdapter = adapter;
        final View firstItemView = adapter.getView(0, null, mContainer);
        mContainer.addView(firstItemView);

        //计算child的宽和高，每屏加载的数量
        if (mChildWidth == 0 && mChildHeight == 0) {
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            firstItemView.measure(widthMeasureSpec, heightMeasureSpec);
            mChildWidth = firstItemView.getMeasuredWidth();
            mChildHeight = firstItemView.getMeasuredHeight();
            mOneScreenCount = mScreenWidth / mChildWidth + 2;
        }
        //初始化第一屏
        initFirstScreenChildren(mOneScreenCount);
    }

    private void initFirstScreenChildren(int count) {
        //数据源必须大于一屏加载数据个数，否则数组会越界
        if (mAdapter.getCount() < count) {
            WLog.i(TAG, "count:" + count);
            return;
        }

        mContainer.removeAllViews();
        mViewPos.clear();
        for (int i = 0; i < count; i++) {
            View view = mAdapter.getView(i, null, mContainer);
            view.setOnClickListener(this);
            mContainer.addView(view);
            mViewPos.put(view, i);
            mCurrentIndex = i;
        }
        if (mChangeListener != null) {
            notifyCurrentImgChanged();
        }
    }

    private void notifyCurrentImgChanged() {
        for (int i = 0; i < mOneScreenCount; i++) {
            mContainer.getChildAt(i).setBackgroundColor(Color.WHITE);
        }
        mChangeListener.onCurrentImgChanged(mFirstIndex, mContainer.getChildAt(0));
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int scrollX = getScrollX();
                WLog.i(TAG, "scrollX:" + scrollX);
                if (scrollX >= mChildWidth) {
                    loadNextChild();
                }
                if (scrollX == 0) {
                    loadPreChild();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void loadNextChild() {
        //如果是最后一个就return
        if (mCurrentIndex == mAdapter.getCount() - 1) {
            return;
        }
        mFirstIndex++;
        mCurrentIndex++;

        scrollTo(0, 0);
        mViewPos.remove(mContainer.getChildAt(0));
        mContainer.removeViewAt(0);


        View nextView = mAdapter.getView(mCurrentIndex, null, mContainer);
        nextView.setOnClickListener(this);
        mContainer.addView(nextView);
        mViewPos.put(nextView, mCurrentIndex);

        if (mChangeListener != null) {
            notifyCurrentImgChanged();
        }


    }

    private void loadPreChild() {
        //如果是第一个就return
        if (mFirstIndex == 0) {
            return;
        }
        mFirstIndex--;
        mCurrentIndex--;


        int oldViewPos = mContainer.getChildCount() - 1;
        mViewPos.remove(mContainer.getChildAt(oldViewPos));
        mContainer.removeViewAt(oldViewPos);

        View view = mAdapter.getView(mFirstIndex, null, mContainer);
        mContainer.addView(view, 0);
        mViewPos.put(view, mFirstIndex);

        scrollTo(mChildWidth, 0);

        if (mChangeListener != null) {
            notifyCurrentImgChanged();
        }

    }

    @Override
    public void onClick(View v) {
        if (mClickListener != null) {
            for (int i = 0; i < mOneScreenCount; i++) {
                mContainer.getChildAt(i).setBackgroundColor(Color.WHITE);
            }
            mClickListener.onClick(v, mViewPos.get(v));
        }
    }

    public interface CurrentImageChangeListener {
        void onCurrentImgChanged(int position, View viewIndicator);
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }
}
