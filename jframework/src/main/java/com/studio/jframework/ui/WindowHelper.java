package com.studio.jframework.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Jason
 */
public class WindowHelper {

    @SuppressLint("NewApi")
    public static void lowerStatusBarVisibility(Activity activity){
        Window localWindow = activity.getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        localLayoutParams.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        localWindow.setAttributes(localLayoutParams);
    }

}
