package com.dh.pulltodownlayout;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebViewFragment;
import android.widget.FrameLayout;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.dh.pulltodownlayout.adapter.ImageAdapter;
import com.dh.pulltodownlayout.fragment.HomeFragment;
import com.dh.pulltodownlayout.fragment.Test2Fragment;
import com.dh.pulltodownlayout.interfaces.IUpdateCallback;
import com.dh.pulltodownlayout.utils.UIViewUtil;
import com.dh.pulltodownlayout.view.DayMsgViewPager;
import com.dh.pulltodownlayout.view.PullToDownLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IUpdateCallback {
    private int mCurrentIndex = Constants.FragmentType.ALMANAC;
    private int mOldIndex = Constants.FragmentType.ALMANAC;
    private Fragment[] fragmentArray = new Fragment[]{null, null, null, null};
    private BottomBarLayout mBottomBarLayout;
    private DayMsgViewPager mViewPager;
    FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomBarLayout = findViewById(R.id.main_tab_group);
        mViewPager = findViewById(R.id.viewPager);
        mFrameLayout = findViewById(R.id.layout_container);

        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int previousPosition, final int currentPosition) {
                if (currentPosition == 0) {
                    mCurrentIndex = Constants.FragmentType.ALMANAC;
                } else if (currentPosition == 1) {
                    mCurrentIndex = Constants.FragmentType.LECTURE;
                } else if (currentPosition == 2) {
                    mCurrentIndex = Constants.FragmentType.CS;
                } else if (currentPosition == 3) {
                    mCurrentIndex = Constants.FragmentType.MY;
                }
                if (mOldIndex == mCurrentIndex) {
                    return;
                }
                if (mCurrentIndex == 0) {
                    UIViewUtil.visible(mViewPager);
                    switchTab();
                } else if (mCurrentIndex == 1) {
                    UIViewUtil.gone(mViewPager);
                    switchTab();
                } else if (mCurrentIndex == 2) {
                    UIViewUtil.gone(mViewPager);
                    switchTab();
                } else if (mCurrentIndex == 3) {
                    UIViewUtil.gone(mViewPager);
                    switchTab();
                }
            }
        });

        mViewPager.setFrameLayout(mFrameLayout);
        mViewPager.setBottomLayout(mBottomBarLayout);
        switchTab();
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.bg_1);
        list.add(R.mipmap.bg_2);
        list.add(R.mipmap.bg_3);
        ImageAdapter mAdapter = new ImageAdapter(this, list);
        mViewPager.setAdapter(mAdapter);
    }

    private void switchTab() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        hideFragment(ft);
        mOldIndex = mCurrentIndex;
        showFragment(ft, mCurrentIndex);
        ft.commitAllowingStateLoss();
    }

    /**
     * 隐藏或者移除上一个Fragment
     * hide()方法只是隐藏了fragment的view并没有将view从viewtree中删除
     *
     * @param ft
     */
    private void hideFragment(FragmentTransaction ft) {
        if (fragmentArray[mOldIndex] == null) {
            return;
        }
        if (fragmentArray[mOldIndex].isAdded()) {
            ft.hide(fragmentArray[mOldIndex]);
        }
    }

    private void showFragment(FragmentTransaction ft, int index) {
        if (fragmentArray[index] != null && fragmentArray[index].isAdded()) {
            ft.show(fragmentArray[index]);
        } else {
            Bundle bundle = new Bundle();
            if (index == Constants.FragmentType.ALMANAC) {
                fragmentArray[index] = new HomeFragment();
            } else if (index == Constants.FragmentType.LECTURE) {
                fragmentArray[index] = new Test2Fragment();
                bundle.putString("content", "test2");
            } else if (index == Constants.FragmentType.MY) {
                fragmentArray[index] = new Test2Fragment();
                bundle.putString("content", "test3");
            } else if (index == Constants.FragmentType.CS) {
                fragmentArray[index] = new Test2Fragment();
                bundle.putString("content", "test4");
            }
            fragmentArray[index].setArguments(bundle);
            ft.add(R.id.layout_container, fragmentArray[index]);
        }
    }

    @Override
    public void onChangeBottomColor(float alpha) {
        mBottomBarLayout.setAlpha(alpha);
    }

    @Override
    public void setDayMessageLayoutIntoPullDownLayout(PullToDownLayout pullDownLayout) {
        if (pullDownLayout != null) {
            pullDownLayout.setDayMsgViewPager(mViewPager);
            mViewPager.setLayoutContent(pullDownLayout);
        }
    }
}
