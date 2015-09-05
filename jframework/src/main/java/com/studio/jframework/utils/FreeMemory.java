package com.studio.jframework.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

public class FreeMemory {
    private static FreeMemory INSTANCE;
    private Context mContext;
    private ActivityManager mManager = null;

    public FreeMemory(Context paramContext) {
        this.mContext = paramContext;
        this.mManager = ((ActivityManager) this.mContext.getSystemService(Context.ACTIVITY_SERVICE));
    }

    public static FreeMemory getInstance(Context paramContext) {
        if (INSTANCE == null)
            INSTANCE = new FreeMemory(paramContext);
        return INSTANCE;
    }

    public void freeMemory() {
        List localList = this.mManager.getRunningAppProcesses();
        Iterator localIterator = localList.iterator();
        while (true) {
            if (!localIterator.hasNext()) {
                return;
            }
            RunningAppProcessInfo localRunningAppProcessInfo = (RunningAppProcessInfo) localIterator.next();
            if ((localRunningAppProcessInfo.processName.equals("system")) || (localRunningAppProcessInfo.processName.equals("com.android.phone")))
                continue;
            if (localRunningAppProcessInfo.processName.equals("com.jecainfo.weican")) {
                Log.e("weijiang.Zeng", "项目进程,不释放...");
                continue;
            }
            this.mManager.killBackgroundProcesses(localRunningAppProcessInfo.processName);
            this.mManager.restartPackage(localRunningAppProcessInfo.processName);
        }
    }
}