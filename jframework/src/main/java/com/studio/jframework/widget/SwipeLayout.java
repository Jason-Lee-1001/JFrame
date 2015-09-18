package com.studio.jframework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by Jason
 */
public class SwipeLayout extends ScrollView {

    private LinearLayout mWrapper;
    private ViewGroup mFirstLayout;
    private ViewGroup mSecondLayout;

    private int mHeight;

    private boolean mEnable = true;
    private boolean mOnce = false;
    private boolean mIsOpen = false;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);

        mHeight = outMetrics.heightPixels;

        final ViewTreeObserver viewTreeObserver = getViewTreeObserver();

        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                mHeight = getMeasuredHeight();
                mFirstLayout.getLayoutParams().height = mHeight;
                mSecondLayout.getLayoutParams().height = mHeight;
                return true;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!mOnce) {

            mWrapper = (LinearLayout) getChildAt(0);
            mFirstLayout = (ViewGroup) mWrapper.getChildAt(0);
            mSecondLayout = (ViewGroup) mWrapper.getChildAt(1);

            mFirstLayout.getLayoutParams().height = mHeight;
            mSecondLayout.getLayoutParams().height = mHeight;
            mOnce = true;

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(0, 0);
        }
    }

    public void setEnableScrolling(boolean enable) {
        this.mEnable = enable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mEnable) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_UP:
                    int scrollY = getScrollY();

                    if (scrollY >= mHeight / 4) {
                        this.smoothScrollTo(0, mHeight);
                        mIsOpen = false;
                    } else {
                        this.smoothScrollTo(0, 0);
                        mIsOpen = true;
                    }
                    return true;
            }
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    public void openMenu() {
        if (mIsOpen) {
            return;
        }
        this.smoothScrollTo(0, 0);
        mIsOpen = true;
    }

    public void closeMenu() {
        if (!mIsOpen) {
            return;
        }
        this.smoothScrollTo(0, mHeight);
        mIsOpen = false;
    }

    public void toggle() {
        if (mIsOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }
}
