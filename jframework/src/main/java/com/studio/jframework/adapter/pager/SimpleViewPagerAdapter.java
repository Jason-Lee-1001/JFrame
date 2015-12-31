package com.studio.jframework.adapter.pager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Jason
 * 用于简单的ViewPager的数据显示
 */
abstract public class SimpleViewPagerAdapter<T> extends PagerAdapter {

    private List<View> mViews;

    public SimpleViewPagerAdapter(Context context, List<T> data) {
        mViews = inflateContent(data);
    }

    /**
     * 将数据绑定到View中, 将放回ViewPager中所有View的List集合
     *
     * @param data 准备用于显示到ViewPager中的数据源
     * @return List装载的所有ViewPager的View
     */
    abstract public List<View> inflateContent(List<T> data);

    @Override
    public int getCount() {
        return mViews == null ? 0 : mViews.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }
}