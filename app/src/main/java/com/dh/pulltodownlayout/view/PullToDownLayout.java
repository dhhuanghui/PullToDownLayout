package com.dh.pulltodownlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.dh.pulltodownlayout.interfaces.OnScrollListener;
import com.dh.pulltodownlayout.utils.ScreenUtils;

/**
 * Created by dh on 2018/9/28.
 */

public class PullToDownLayout extends RelativeLayout {
    //阻尼系数，手指滑动的距离*DAMP_PARAM
    private static final float DAMP_PARAM = 0.5F;
    private static final String TAG = "PullToDownLayout";
    private Scroller mScroller;
    private GestureDetector mGestureDetector;
    // 当前视图是否隐藏
    private boolean isHidden = false;
    private float mStartX;
    private float mStartY;
    private ScrollView mScrollView;
    private DayMsgViewPager mViewPager;
    private float mScrollYSum = 0;
    private OnScrollListener onScrollListener;


    public void setOnScrollListener(OnScrollListener listener) {
        this.onScrollListener = listener;
    }

    public PullToDownLayout(Context context) {
        this(context, null);
    }

    public PullToDownLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToDownLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        mScroller = new Scroller(context);
        mGestureDetector = new GestureDetector(context, new GestureListenerImpl());
        setClickable(true);

    }


    public boolean isHidden() {
        return isHidden;
    }

    //滚动到目标位置
    protected void prepareScroll(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        beginScroll(dx, dy);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取视图高度
//        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //必须执行postInvalidate()从而调用computeScroll()
            //其实,在此调用invalidate();亦可
            postInvalidate();
        }
        super.computeScroll();
    }

    // 显示视图
    public void show() {
        isHidden = false;
        if (onScrollListener != null) {
            onScrollListener.onScroll(0, 0);
        }
        prepareScroll(0, 0);
    }

    // 隐藏视图
    public void hide() {
        prepareScroll(0, -ScreenUtils.getScreenHeight(getContext()));
        isHidden = true;
        //设置alpha为0
        if (onScrollListener != null) {
            onScrollListener.onHide();
        }
    }


    //    public void setStickyNestedLayout(StickyNestedLayout3 stickyNestedLayout) {
//        this.mStickyNestedLayout = stickyNestedLayout;
//    }
    public void setScrollView(ScrollView scrollView) {
        this.mScrollView = scrollView;
    }

    public void setDayMsgViewPager(DayMsgViewPager viewPager) {
        this.mViewPager = viewPager;
    }

    /**
     * 执行逻辑：
     * dispatchTouchEvent分发事件，走到onInterceptTouchEvent方法，判断是否需要拦截，
     * 1、手指按下，走到onInterceptTouchEvent方法中收到MotionEvent.ACTION_DOWN事件，这时候初始化值，
     * 把event给mGestureDetector，return false的意思是不拦截，因此不会执行onTouchEvent的down事件；
     * 2、手指移动，onInterceptTouchEvent的move事件触发，这时候判断是否往下滑并且滑动方法是垂直方向以及页面内部的mStickyNestedLayout是否处于最顶部，
     * 如果是，则return true，说明拦截这个事件，给自己的onTouchEvent去处理，之后的move就不会走到onInterceptTouchEvent的move分支了，只会走onTouchEvent的move分支
     * 3、手指弹起，onInterceptTouchEvent的up事件不执行，onTouchEvent的up事件被执行
     *
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!isHidden) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mScrollYSum = 0;
                    mStartX = event.getX();
                    mStartY = event.getY();
                    mGestureDetector.onTouchEvent(event);
                    if (mViewPager != null) {
                        mViewPager.setVisibility(View.VISIBLE);
                    }
                    return false;
                case MotionEvent.ACTION_MOVE:
                    float nowX = event.getX();
                    float nowY = event.getY();
                    float delX = nowX - mStartX;
                    float delY = nowY - mStartY;
                    int scrollY = mScrollView.getScrollY();
                    if (delY > 0 && Math.abs(delY) > Math.abs(delX) && scrollY == 0) {
                        //这里返回true，说明拦截触摸事件，由当前PullToDownLayout类处理，不往下传递，
                        // 只要这里返回了true，就会执行onTouchEvent方法，不再执行onInterceptTouchEvent方法
                        return true;
                    } else {
                        return false;
                    }
                case MotionEvent.ACTION_UP:
                    return false;
                default:
                    return false;
            }
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isHidden) {
            switch (event.getAction()) {
                // 除了ACTION_UP，其他手势交给GestureDetector
                case MotionEvent.ACTION_DOWN:
                    // 获取收按下时的y轴坐标
                    mStartY = event.getY();
                    return mGestureDetector.onTouchEvent(event);
                case MotionEvent.ACTION_UP:
                    int scrollY = mScrollView.getScrollY();
                    if (scrollY == 0) {
                        if (Math.abs(mScrollYSum) < ScreenUtils.dpToPxInt(getContext(), 100)) {
                            show();
                        } else {
                            hide();
                            postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (mViewPager != null)
                                        mViewPager.bringToFront();
//                                        mLayoutContainer.bringChildToFront(mLayoutDayMessage);
                                }
                            }, 100);
                        }
                    }
                    return true;
                case MotionEvent.ACTION_MOVE:
                    // 获取当前滑动的y轴坐标
                    float curY = event.getY();
                    // 获取移动的y轴距离
                    float deltaY = curY - mStartY;
                    scrollY = mScrollView.getScrollY();
                    // 阻止视图在原来位置时向下滚动
                    if (deltaY > 0 && scrollY == 0) {
                        return mGestureDetector.onTouchEvent(event);
                    } else {
                        return true;
                    }

                default:
                    //其余情况交给GestureDetector手势处理
                    return mGestureDetector.onTouchEvent(event);
            }
        }
        return super.onTouchEvent(event);
    }

    //设置滚动的相对偏移
    protected void beginScroll(int dx, int dy) {
        //第一,二个参数起始位置;第三,四个滚动的偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        //必须执行invalidate()从而调用computeScroll()
        invalidate();
    }

    class GestureListenerImpl implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        //控制拉动幅度:
        //int disY=(int)((distanceY - 0.5)/2);
        //亦可直接调用:
        //smoothScrollBy(0, (int)distanceY);
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mScrollYSum += distanceY * DAMP_PARAM;
//            int disY = (int) ((distanceY - 0.5) / 2);
//            beginScroll(0, disY);
            beginScroll(0, (int) (distanceY * DAMP_PARAM));
            if (onScrollListener != null) {
                onScrollListener.onScroll(mScrollYSum, distanceY * DAMP_PARAM);
            }
            return false;
        }

        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

    }
}
