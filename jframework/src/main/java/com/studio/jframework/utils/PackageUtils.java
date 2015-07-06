package com.studio.jframework.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>need to be tested
 * @author Jason
 */
public class PackageUtils {

    /**
     * Check if the service is running
     *
     * @param context Context
     * @param cls     The service class
     * @return Will return true if the service is running, false otherwise
     */
    public static boolean isServiceRunning(Context context, Class<?> cls) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo serviceInfo : services) {
            ComponentName componentName = serviceInfo.service;
            String serviceName = componentName.getClassName();
            if (serviceName.equals(cls.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the current running activity, deprecated in api 21
     * Should specify GET_TASKS permission
     *
     * @param context Context
     * @return Will return null if getting the current activity failed
     */
    public static String getCurrentActivity(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            return activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the app icon drawable with the specify package name
     *
     * @param context     Context
     * @param packageName The package name fo the application
     * @return The drawable of the application
     */
    public static Drawable getAppIcon(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfo = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<>();
        if (packageInfo != null) {
            for (int i = 0; i < packageInfo.size(); i++) {
                String packName = packageInfo.get(i).packageName;
                packageNames.add(packName);
                if (packageNames.contains(packageName)) {
                    return packageInfo.get(i).applicationInfo.loadIcon(packageManager);
                }
            }
        }
        return null;
    }

    /**
     * Check if the specify application was installed or not
     *
     * @param context     Context
     * @param packageName The package name you want to check
     * @return Will return true if the application is installed, false otherwise
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfo = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<>();
        if (packageInfo != null) {
            for (int i = 0; i < packageInfo.size(); i++) {
                String packName = packageInfo.get(i).packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);
    }

    /**
     * Get the version code of the application
     *
     * @param context Context
     * @return Will return the version code if the package name exists
     * will return -1 if the name not found
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    /**
     * Get the version name of the application
     *
     * @param context Context
     * @return The version name if the package name exists
     * will return null if the name not found
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * Get the package name of the application
     *
     * @param context Context
     * @return The package name if the package name exists
     * will return null if the name not found
     */
    public static String getPackageName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static String getAppName(Context context,int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    // Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
                    // info.processName +"  Label: "+c.toString());
                    // processName = c.toString();
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}