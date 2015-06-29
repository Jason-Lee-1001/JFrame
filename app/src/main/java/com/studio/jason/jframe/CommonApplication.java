package com.studio.jason.jframe;

import android.app.Application;

/**
 * Created by Jason
 */
public class CommonApplication extends Application {
    private static CommonApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    public static CommonApplication getInstance(){
        return INSTANCE;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
