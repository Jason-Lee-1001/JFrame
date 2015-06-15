package com.studio.jframework.utils;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;

public class SizeUtils {

    private SizeUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * The absolute height of the display in pixels
     *
     * @param context Context
     * @return px(int)
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * The absolute width of the display in pixels
     *
     * @param context Context
     * @return px(int)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * Size tool to convert dp to px
     *
     * @param context Context
     * @param dp      dp unit
     * @return The converted size in pixel
     */
    public static int getPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 得到控件宽
     *
     * @param view
     * @return
     */
    public static int getControlsWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredWidth();
    }

    /**
     * 得到控件高
     *
     * @param view
     * @return
     */
    public static int getControlsHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

}
