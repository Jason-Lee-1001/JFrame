package com.studio.jframework.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

import java.util.Iterator;
import java.util.List;

public class FreeMemory {

    private static FreeMemory INSTANCE;
    private Context mContext;
    private ActivityManager mManager = null;

    public FreeMemory(Context context) {
        this.mContext = context;
        this.mManager = ((ActivityManager) this.mContext.getSystemService(Context.ACTIVITY_SERVICE));
    }

    public static FreeMemory getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new FreeMemory(context);
        return INSTANCE;
    }

    public void freeMemory(String packageToKeep) {
        List localList = this.mManager.getRunningAppProcesses();
        Iterator localIterator = localList.iterator();
        while (true) {
            if (!localIterator.hasNext()) {
                return;
            }
            RunningAppProcessInfo localRunningAppProcessInfo = (RunningAppProcessInfo) localIterator.next();
            if ((localRunningAppProcessInfo.processName.equals("system")) || (localRunningAppProcessInfo.processName.equals("com.android.phone")))
                continue;
            if (localRunningAppProcessInfo.processName.equals(packageToKeep)) {
                continue;
            }
            this.mManager.killBackgroundProcesses(localRunningAppProcessInfo.processName);
            this.mManager.restartPackage(localRunningAppProcessInfo.processName);
        }
    }
}