package com.studio.jframework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.studio.jframework.R;


/**
 * @author Jason
 *         <p/>
 *         ns: xmlns:slidemenu="http://schemas.android.com/apk/res-auto"
 */
public class SlideMenu extends HorizontalScrollView {

    private LinearLayout mWrapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;

    private int mMenuWidth;

    private int mScreenWidth;
    private int mMenuRightPadding = 50;

    private boolean mOnce = false;
    private boolean mIsOpen = false;

    public SlideMenu(Context context) {
        this(context, null);
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //gain my property
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlideMenu, defStyleAttr, 0);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SlideMenu_rightPadding) {
                mMenuRightPadding = a.getDimensionPixelSize(attr,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()));
            }
        }
        //array should be release
        a.recycle();

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);

        mScreenWidth = outMetrics.widthPixels;

        //dp to px
//        mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!mOnce) {

            mWrapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWrapper.getChildAt(0);
            mContent = (ViewGroup) mWrapper.getChildAt(1);

            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenWidth;
            mOnce = true;

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();

                if (scrollX >= mMenuWidth / 2) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    mIsOpen = false;
                } else {
                    this.smoothScrollTo(0, 0);
                    mIsOpen = true;
                }
                return true;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        //---------------become a drawer------------------------

        float scale = l * 1.0f / mMenuWidth;
        //use property animation
//        mMenu.setTranslationX(mMenuWidth * scale);

        //---------------add scale animation---------------------

        float menuScale = 1.0f - 0.3f * scale;
        float menuAlpha = 0.5f + 0.5f * (1 - scale);
        float contentScale = 0.7f + 0.3f * scale;

        mMenu.setTranslationX(mMenuWidth * scale * 0.7f);
        mMenu.setScaleX(menuScale);
        mMenu.setScaleY(menuScale);
        mMenu.setAlpha(menuAlpha);

        mContent.setPivotX(0);
        mContent.setPivotY(mContent.getHeight() / 2);
        mContent.setScaleX(contentScale);
        mContent.setScaleY(contentScale);
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
        this.smoothScrollTo(mMenuWidth, 0);
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
