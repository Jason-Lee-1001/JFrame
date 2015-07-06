package com.studio.jframework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * A sub class of {@link ListView} which can be a child view of scrollable view,
 * such as {@link android.widget.ScrollView} and {@link ListView}. Just use as
 * normal ListView
 */
public class InnerListView extends ListView {

    public InnerListView(Context context) {
        super(context);
    }

    public InnerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}