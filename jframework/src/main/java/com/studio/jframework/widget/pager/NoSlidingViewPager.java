package com.studio.jframework.widget.pager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoSlidingViewPager extends ViewPager {

    private boolean canSliding;

    public NoSlidingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        canSliding = false;
    }

    public NoSlidingViewPager(Context context) {
        super(context);
        canSliding = false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return canSliding && super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return canSliding && super.onTouchEvent(arg0);
    }

    public boolean isCanSliding() {
        return canSliding;
    }

    /**
     * 设置可以左右滑动	default = false
     *
     * @param canSliding 设置是否允许滑动
     */
    public void setCanSliding(boolean canSliding) {
        this.canSliding = canSliding;
    }
}
