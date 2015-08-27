package com.studio.jframework.adapter.pager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Jason
 */
abstract public class SimpleViewPagerAdapter<T> extends PagerAdapter {

    private List<View> mViews;

    public SimpleViewPagerAdapter(Context context, List<T> data) {
        mViews = inflateContent(data);
    }

    /**
     * Bind data to the view, and return a list of all the page's views
     *
     * @param data A collection of the data that is going to be inflated in the view
     * @return A list of all the page's views
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