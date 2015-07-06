package com.studio.jframework.base;

import android.app.Application;

import com.studio.jframework.utils.PackageUtils;

/**
 * A common application instance that wraps some usefully methods
 *
 * @author Jason
 */
abstract public class CommonApplication extends Application {

    public static final String TAG = "CommonApplication";

    private static CommonApplication INSTANCE;
    private int pid;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    public static CommonApplication getInstance() {
        return INSTANCE;
    }

    /**
     * Put your package name in this method and return
     *
     * @return Package name of your application
     */
    abstract protected String yourPackageName();

    /**
     * Multi processes application has multi {@link Application}
     * <p>In case of initializing third party sdk several times, you may call this method
     * in onCreate to judge whether it's the main process created or not
     *
     * @return True if this application instance was created in the main process, false indicate
     * that the this application instance was created by a new process
     */
    protected boolean isMainProcessCreated() {
        pid = android.os.Process.myPid();
        String appName = PackageUtils.getAppName(INSTANCE, pid);
        return appName != null && appName.equalsIgnoreCase(yourPackageName());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
