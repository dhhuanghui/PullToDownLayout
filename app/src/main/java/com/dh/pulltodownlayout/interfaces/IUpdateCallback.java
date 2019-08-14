package com.dh.pulltodownlayout.interfaces;


import com.dh.pulltodownlayout.view.PullToDownLayout;

/**
 * Created by dh on 2018/9/30.
 */

public interface IUpdateCallback {
    /**
     * 滚动过程，更新底部tab栏透明度
     */
    void onChangeBottomColor(float alpha);

    /**
     * 设置PullDownLayout到Viewpager中
     * @param pullDownLayout
     */
    void setDayMessageLayoutIntoPullDownLayout(PullToDownLayout pullDownLayout);
}
