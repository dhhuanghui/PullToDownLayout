package com.dh.pulltodownlayout.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.dh.pulltodownlayout.R;

import java.util.List;

/**
 * Created by huanghui on 2015/3/21.
 * 详情页的banner适配器
 */
public class ImageAdapter extends PagerAdapter {
    private LayoutInflater mInflater;
    private List<Integer> imageItems = null;

    public ImageAdapter(Activity context, List<Integer> list) {
        mInflater = LayoutInflater.from(context);
        this.imageItems = list;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return imageItems.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (View) arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.item_big_image, null);
        final ImageView imageView = view.findViewById(R.id.pic_item);
        imageView.setImageResource(imageItems.get(position));
        container.addView(view);
        return view;
    }

}

