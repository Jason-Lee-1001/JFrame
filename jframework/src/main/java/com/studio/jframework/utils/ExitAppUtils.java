package com.studio.jframework.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jason
 * A singleton tool to quit the whole app<p/>
 * Usage:<p/>
 * 1.invoke addActivity() in each activities' onCreate(), this will add the activity in mActivityList<p/>
 * 2.invoke removeActivity() in each activities' onDestroy(), this will remove the activity from mActivityList<p/>
 * 3.if your application contains Service, you need to find other way to terminal the service<p/>
 */
public class ExitAppUtils {

    //The container to store activities
    private List<WeakReference<Activity>> mActivityList = new LinkedList<>();
    private static ExitAppUtils INSTANCE;

    private ExitAppUtils() {
    }

    /**
     * Gain the instance of this class
     *
     * @return The instance of this class
     */
    public static ExitAppUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ExitAppUtils();
        }
        return INSTANCE;
    }

    /**
     * Add the activity to the container<p/>
     * invoke in onCreate()
     *
     * @param activity The activity instance to be stored
     */
    public void addActivity(Activity activity) {
        mActivityList.add(new WeakReference<>(activity));
    }

    /**
     * Remove the activity from the container<p/>
     * invoke in onDestroy()
     *
     * @param activity The activity instance to be removed
     */
    public void removeActivity(Activity activity) {
        mActivityList.remove(new WeakReference<>(activity));
    }

    /**
     * The method will finish all the activities in the container
     */
    public void exit() {
        for (WeakReference<Activity> activity : mActivityList) {
            if (activity.get() != null)
                activity.get().finish();
        }
        System.exit(0);
    }
}
