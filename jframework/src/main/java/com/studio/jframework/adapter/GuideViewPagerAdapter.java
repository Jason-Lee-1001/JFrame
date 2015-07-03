package com.studio.jframework.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-07-01.
 */
public class GuideViewPagerAdapter extends PagerAdapter {

    private ArrayList<View> list_view;

    public GuideViewPagerAdapter(ArrayList<View> list) {
        this.list_view = list;
    }

    /**
     * 获取当前界面的个数
     *
     * @return list_view.size
     */
    @Override
    public int getCount() {
        if (this.list_view != null) {
            return list_view.size();
        }
        return 0;
    }

    /**
     * 初始化position位置的界面
     */
    @Override
    public Object instantiateItem(View view, int position) {
        ((ViewPager) view).addView(list_view.get(position), 0);
        return list_view.get(position);
    }

    /**
     * 判断是否生成界面
     *
     * @param view
     * @param o
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object o) {
        return (view == o);
    }

    /**
     * 销毁position位置的界面
     */
    @Override
    public void destroyItem(View view, int position, Object arg2) {
        ((ViewPager) view).removeView(list_view.get(position));
    }

}
