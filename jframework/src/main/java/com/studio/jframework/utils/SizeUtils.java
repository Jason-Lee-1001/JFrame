package com.studio.jframework.utils;

import android.content.Context;
import android.view.View;

public class SizeUtils {

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
     * @param dpValue dp unit
     * @return The converted size in pixel
     */
    public static int convertDp2Px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Size tool to convert px to dp
     *
     * @param context Context
     * @param pxValue px unit
     * @return The converted size in pixel
     */
    public static int convertPx2Dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int convertSp2Px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int convertPx2Sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 得到控件宽
     *
     * @param view
     * @return
     */
    public static int getWidgetWidth(View view) {
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
    public static int getWidgetHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }


//
//
//
//    这种方法很简单，就是我们自己来测量
//
//
//    No2：
//
//    ViewTreeObserver vto = imageView.getViewTreeObserver();
//    vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//        public boolean onPreDraw() {
//            vto.removeOnPreDrawListener(this);
//            int height = imageView.getMeasuredHeight();
//            int width = imageView.getMeasuredWidth();
//            return true;
//        }
//    });
//
//    这个方法，我们需要注册一个ViewTreeObserver的监听回调，这个监听回调，就是专门监听绘图的，既然是监听绘图，那么我们自然可以获取测量值了，同时，我们在每次监听前remove前一次的监听，避免重复监听。
//
//
//
//    No3：
//
//    ViewTreeObserver vto = imageView.getViewTreeObserver();
//    vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//        @Override
//        public void onGlobalLayout() {
//            imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//            imageView.getHeight();
//            imageView.getWidth();
//        }
//    });
//
//    这个方法于第2个方法基本相同，但他是全局的布局改变监听器，所以是最推荐使用的。

}
