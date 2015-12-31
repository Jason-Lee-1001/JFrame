package com.studio.jframework.animation.view;

import android.app.Activity;
import android.view.View;

/**
 * Created by Jason
 */
@Deprecated
public class ViewAnimationHelper {

    public static void setScale(Activity context, View view, float rate, long duration) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
        view.setScaleX(rate);
        view.setScaleY(rate);
    }

}
