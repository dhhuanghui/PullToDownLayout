package com.dh.pulltodownlayout.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.dh.pulltodownlayout.R;
import com.dh.pulltodownlayout.interfaces.IUpdateCallback;
import com.dh.pulltodownlayout.interfaces.OnScrollListener;
import com.dh.pulltodownlayout.utils.ScreenUtils;
import com.dh.pulltodownlayout.view.PullToDownLayout;

public class HomeFragment extends Fragment {

    private PullToDownLayout mPullToDownLayout;
    private ScrollView mScrollView;
    private IUpdateCallback updateCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        mPullToDownLayout = view.findViewById(R.id.layout_content);
        mScrollView = view.findViewById(R.id.scrollView);

        mPullToDownLayout.setScrollView(mScrollView);
        if (updateCallback != null) {
            updateCallback.setDayMessageLayoutIntoPullDownLayout(mPullToDownLayout);
        }
        mPullToDownLayout.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScroll(float scrollTotalOffset, float distanceY) {
                if (scrollTotalOffset == 0) {
                    mPullToDownLayout.setAlpha(1.0f);
                    if (updateCallback != null) {
                        updateCallback.onChangeBottomColor(1.0f);
                    }
                    return;
                }
                int height = ScreenUtils.getScreenHeight(getActivity()) / 2;
                scrollTotalOffset = Math.abs(scrollTotalOffset);
                float alpha = 0;
                if (scrollTotalOffset >= height) {
                    alpha = 0;
                } else {
                    alpha = (1 - (scrollTotalOffset / height));
                }
                mPullToDownLayout.setAlpha(alpha);
                if (updateCallback != null) {
                    updateCallback.onChangeBottomColor(alpha);
                }
            }

            @Override
            public void onHide() {
                mPullToDownLayout.setAlpha(0f);
                if (updateCallback != null) {
                    updateCallback.onChangeBottomColor(0f);
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            updateCallback = (IUpdateCallback) activity;
        } catch (Exception e) {
        }
    }
}
