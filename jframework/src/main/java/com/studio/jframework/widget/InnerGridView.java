package com.studio.jframework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * A sub class of {@link GridView} which can be a child view of scrollable view,
 * such as {@link android.widget.ScrollView} and {@link android.widget.ListView}. Just use as
 * normal GridView
 */
public class InnerGridView extends GridView {

    public InnerGridView(Context context) {
        super(context);
    }

    public InnerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
