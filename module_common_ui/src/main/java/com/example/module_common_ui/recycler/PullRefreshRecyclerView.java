package com.example.module_common_ui.recycler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

public class PullRefreshRecyclerView extends RecyclerView {

    private float lastY = -1; // 滑动事件坐标记录
    public final static int SCROLL_DURATION = 400; // 刷新头回弹的时间长度，时间长则回弹慢
    private final static float OFFSET_RADIO = 1.8f; // 下拉阻尼系数，使得下拉时呈现出弹簧效果
    public final static int MESSAGE_SHOW_DURATION = 2000; //提示消息显示时间

    private static final int PULL_REFRESH_FOOTER_HEIGHT = 100; //上拉加载的距离，默认50px
    private static final int PULL_REFRESH_HEADER_HEIGHT = 100; //下拉刷新的距离，默认50px

    private boolean mEnableAutoLoading = false; //是否设置自动加载更多，暂未实现

    /**
     * 是否可以上拉  默认可以
     */
    private boolean mEnablePullLoad = true;
    /**
     * 是否可以下拉   默认可以
     */
    private boolean mEnablePullRefresh = true;
    /**
     * 是否正在加载
     */
    private boolean mPullLoading = false;
    /**
     * 是否正在刷新
     */
    private boolean mPullRefreshing = false;
    /**
     * 区分上拉和下拉
     */
    private int mScrollBack;
    private final static int SCROLL_HEADER = 0;
    private final static int SCROLL_FOOTER = 1;
    //滚动类
    private Scroller mScroller;

    //头布局控件
    private RecyclerViewHeader mHeaderView;
    //尾控件
    private RecyclerViewFooter mFooterView;
    //消息提示类
    private MessageRelativeLayout mParent;
    //adapter的装饰类
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    public PullRefreshRecyclerView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public PullRefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PullRefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        mHeaderView = new RecyclerViewHeader(context);
        mFooterView = new RecyclerViewFooter(context);
    }


    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        super.setAdapter(mHeaderAndFooterWrapper);

        mHeaderAndFooterWrapper.addHeaderView(mHeaderView);
        mHeaderAndFooterWrapper.addFooterView(mFooterView);

        if (getParent() instanceof MessageRelativeLayout) {
            mParent = (MessageRelativeLayout) getParent();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (lastY == -1) {
            lastY = e.getRawY();
        }
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceY = e.getRawY() - lastY;
                lastY = e.getRawY();

                //第一个条目完全显示   //头部高度大于0   deltaY大于0  向下移动
                if ((layoutManager.findFirstCompletelyVisibleItemPosition() == 0 || layoutManager.findFirstCompletelyVisibleItemPosition() == 1)
                        && (mHeaderView.getVisibleHeight() > 0 || distanceY > 0)) {
                    //更新头部高度
                    if (mEnablePullRefresh) {
                        updateHeaderHeight(distanceY / OFFSET_RADIO);
                    }
                } else if (isSlideToBottom() && (mFooterView.getBottomMargin() > 0 || distanceY < 0)) {
                    //已经到达底部,改变状态或者自动加载
                    updateFooterHeight(-distanceY / OFFSET_RADIO);
                } else if (distanceY > 0) {
                    updateFooterHeight(-distanceY / OFFSET_RADIO);
                }
                break;
            case MotionEvent.ACTION_UP:
                lastY = -1;
                if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0 || layoutManager.findFirstCompletelyVisibleItemPosition() == 1) {
                    //变为刷新状态
                    if (mEnablePullRefresh && mHeaderView.getVisibleHeight() > PULL_REFRESH_HEADER_HEIGHT) {
                        mPullRefreshing = true;
                        mHeaderView.setState(RecyclerViewHeader.STATE_REFRESH);
                        //回调事件
                        if (mOnRefreshListener != null) {
                            mOnRefreshListener.onRefresh();
                        }
                    }
                    resetHeaderHeight();

                } else if (isSlideToBottom()) {
                    if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_REFRESH_FOOTER_HEIGHT && !mPullLoading) {
                        mPullLoading = true;
                        mFooterView.setState(RecyclerViewFooter.STATE_REFRESH);
                        if (mOnRefreshListener != null) {
                            mOnRefreshListener.onLoadMore();
                        }

                    }
                    resetFooterHeight();
                } else {
                    resetHeaderHeight();
                }
        }
        return super.onTouchEvent(e);
    }

    /**
     * 更新尾部加载
     *
     * @param distance
     */
    private void updateFooterHeight(float distance) {
        int height = mFooterView.getBottomMargin() + (int) distance;
        mFooterView.setBottomMargin(height);
        if (mEnablePullLoad && !mPullLoading) {
            if (height > PULL_REFRESH_FOOTER_HEIGHT) {
                //改变状态
                mFooterView.setState(RecyclerViewFooter.STATE_READY);
            } else {
                mFooterView.setState(RecyclerViewFooter.STATE_IDLE);
            }
        }

    }

    /**
     * 更新头部刷新
     *
     * @param distance
     */
    private void updateHeaderHeight(float distance) {
        // 设置头部高度,原先的高度加上
        if (!mPullRefreshing) {
            //下拉高度到达可以刷新的位置
            mHeaderView.setVisibleHeight((int) distance + mHeaderView.getVisibleHeight());
            if (mHeaderView.getVisibleHeight() > PULL_REFRESH_HEADER_HEIGHT) {
                mHeaderView.setState(RecyclerViewHeader.STATE_READY);
            } else {
                mHeaderView.setState(RecyclerViewHeader.STATE_IDLE);
            }

        } else {
            if (distance < 0) {
                stopRefresh();
            }
        }
        //移动到顶部
        //smoothScrollBy(0, 0);
    }

    /**
     * 重置头部高度
     */
    private void resetHeaderHeight() {
        int height = mHeaderView.getVisibleHeight();
        // 如果=0  是不可见的 直接返回
        if (height == 0)
            return;

        if (mPullRefreshing && height <= PULL_REFRESH_HEADER_HEIGHT) {
            return;
        }

        int finalHeight = 0;

        if (mPullRefreshing && height > PULL_REFRESH_HEADER_HEIGHT) {
            finalHeight = PULL_REFRESH_HEADER_HEIGHT;
        }
        if (mParent != null) {
            if (mHeaderView.getVisibleHeight() == mParent.getHeaderMessageViewHeight()) {
                finalHeight = mParent.getHeaderMessageViewHeight();
            }
        }

        mScrollBack = SCROLL_HEADER;//设置标识
        mScroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);
        // 触发计算滚动
        invalidate();
    }

    /**
     * 重置尾部高度
     */
    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLL_FOOTER;//设置标识
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);
            invalidate();
        }
    }

    /**
     * 停止刷新
     */
    public void stopRefresh() {
        mScrollBack = SCROLL_HEADER;//设置标识
        int obligateHeight;
        if (mParent != null) {
            obligateHeight = mParent.getHeaderMessageViewHeight();
        } else {
            obligateHeight = 0;
        }
        int height = mHeaderView.getVisibleHeight();
        if (mPullRefreshing) {
            //是否复位

            mPullRefreshing = false;

            //显示更新了多少条消息
            if (mParent != null) {
                mParent.showMessage();
            }
            mScroller.startScroll(0, height, 0, obligateHeight - height, SCROLL_DURATION);
            // 触发计算滚动
            invalidate();

            //延时执行复位移动
            if (mParent != null) {
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(1, MESSAGE_SHOW_DURATION);
            }
        }

    }

    /**
     * 停止加载
     */
    public void stopLoadMore() {
        if (mPullLoading) {
            mPullLoading = false;
            mFooterView.setState(RecyclerViewFooter.STATE_IDLE);
        }
    }

    /**
     * 消息
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mHeaderView.getVisibleHeight() == mParent.getHeaderMessageViewHeight()) {
//                resetHeaderHeight();
                mScroller.startScroll(0, mHeaderView.getVisibleHeight(), 0, -mHeaderView.getVisibleHeight(), SCROLL_DURATION);
                postInvalidate();
            }

            mParent.hideMessage();
        }
    };

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLL_HEADER) {
                mHeaderView.setVisibleHeight(mScroller.getCurrY());
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
        }
        super.computeScroll();
    }

    private OnRefreshListener mOnRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    /**
     * 刷新接口,
     */
    public interface OnRefreshListener {
        void onRefresh();

        void onLoadMore();
    }

    /**
     * 判断是否到底
     *
     * @return
     */
    private boolean isSlideToBottom() {
        return computeVerticalScrollExtent() + computeVerticalScrollOffset() >= computeVerticalScrollRange();
    }
}


