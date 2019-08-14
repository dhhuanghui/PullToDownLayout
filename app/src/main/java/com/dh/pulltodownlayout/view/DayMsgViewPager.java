package com.dh.pulltodownlayout.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.dh.pulltodownlayout.utils.Util;


public class DayMsgViewPager extends ViewPager {
    private static final String TAG = "DayMsgViewPager";
    private PullToDownLayout mLayoutContent;
    private float mStartX;
    private float mStartY;
    /**
     * MainActivity中的布局容器
     */
    private FrameLayout mFragmentLayout;
    private View mBottomView;
    private View mLine;
    /**
     * Y方向与x方向的差值最小满足值
     */
    private int mOffset;
    private boolean isProcessing;

    public DayMsgViewPager(Context context) {
        this(context, null);
    }

    public DayMsgViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        mOffset = Util.dpToPxInt(context, 80);
    }


    public void setLayoutContent(PullToDownLayout layoutContent) {
        this.mLayoutContent = layoutContent;
    }

    public void setFrameLayout(FrameLayout frameLayout) {
        this.mFragmentLayout = frameLayout;
    }

    public void setBottomLayout(View view) {
        mBottomView = view;
    }

    public void setLine(View line) {
        this.mLine = line;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = ev.getX();
                mStartY = ev.getY();
                isProcessing = false;
                return super.onInterceptTouchEvent(ev);
            case MotionEvent.ACTION_MOVE:
                float nowX = ev.getX();
                float nowY = ev.getY();
                float delX = nowX - mStartX;
                float delY = nowY - mStartY;
                //判断为向上滑动并且HomeFragment中的PullDownLayout是隐藏状态，则拦截touch事件，交由自己的onTouchEvent处理
                if (delY < 0 && (Math.abs(delY) - Math.abs(delX)) > mOffset && mLayoutContent.isHidden()) {
                    return true;
                } else {
                    return super.onInterceptTouchEvent(ev);
                }
            case MotionEvent.ACTION_UP:
                return super.onInterceptTouchEvent(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isProcessing) {
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = ev.getX();
                mStartY = ev.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float nowX = ev.getX();
                float nowY = ev.getY();
                float delX = nowX - mStartX;
                float delY = nowY - mStartY;
                //判断为向上滑动并且HomeFragment中的PullDownLayout是隐藏状态，
                // 则显示出HomeFragment布局
                if (delY < 0 && (Math.abs(delY) - Math.abs(delX)) > mOffset && mLayoutContent.isHidden()) {
                    if (mFragmentLayout != null) {
                        mFragmentLayout.bringToFront();
                    }
                    if (mBottomView != null) {
                        mBottomView.bringToFront();
                    }
                    if (mLine != null) {
                        mLine.bringToFront();
                    }
                    isProcessing = true;
                    mLayoutContent.show();
                    return true;
                } else {
                    return super.onTouchEvent(ev);
                }
            case MotionEvent.ACTION_UP:
                return super.onTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }
}
