package com.dh.pulltodownlayout.interfaces;

/**
 * Created by dh on 2018/9/30.
 */

public interface OnScrollListener {
    /**
     * @param scrollTotalOffset 手指滚动的总位移，向上滑为正数，向下滑为负数
     * @param distanceY         每次滑动的Y方向上的距离
     */
    void onScroll(float scrollTotalOffset, float distanceY);

    void onHide();
}
