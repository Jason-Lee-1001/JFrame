package com.studio.jframework.utils;

import android.util.Log;


/**
 * Created by Jason
 * A class which is easy to print log. Including line number and method name
 * Usage:<p/>
 * LogUtils.setEnable(BuildConfig.DEBUG);
 */
public class LogUtils {

    private static LogUtils Singleton;
    private static boolean enableLogger = false;

    private LogUtils() {
    }

    /**
     * The class will print log depend on the value of BuildConfig.DEBUG in your project
     * you may change it during runtime
     *
     * @param enable Enable or disable to print log, default is false;
     */
    public static void setEnable(boolean enable) {
        enableLogger = enable;
    }

    /**
     * Get the instance of this class
     *
     * @return the LogUtil singleton
     */
    private static LogUtils getSingleton() {
        if (Singleton == null) {
            Singleton = new LogUtils();
        }
        return Singleton;
    }

    /**
     * @return the function name
     */
    private String getLogDetail() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName().equals(this.getClass().getName())) {
                continue;
            }
            return "[ ->Line " + st.getLineNumber() + " " + st.getMethodName() + "() ]";
        }
        return null;
    }

    /**
     * The Log Level:i
     *
     * @param TAG Used to identify the source of a log message.
     *            It usually identifies the class or activity where the log call occurs
     * @param msg The message you would like logged
     */
    public static void i(String TAG, String msg) {
        if (enableLogger) {
            String name = getSingleton().getLogDetail();
            if (name != null) {
                Log.i(TAG, name + " -> " + msg);
            } else {
                Log.i(TAG, msg);
            }
        }
    }

    /**
     * The Log Level:d
     *
     * @param TAG Used to identify the source of a log message.
     *            It usually identifies the class or activity where the log call occurs
     * @param msg The message you would like logged
     */
    public static void d(String TAG, String msg) {
        if (enableLogger) {
            String name = getSingleton().getLogDetail();
            if (name != null) {
                Log.d(TAG, name + " -> " + msg);
            } else {
                Log.d(TAG, msg);
            }
        }
    }

    /**
     * The Log Level:v
     *
     * @param TAG Used to identify the source of a log message.
     *            It usually identifies the class or activity where the log call occurs
     * @param msg The message you would like logged
     */
    public static void v(String TAG, String msg) {
        if (enableLogger) {
            String name = getSingleton().getLogDetail();
            if (name != null) {
                Log.v(TAG, name + " -> " + msg);
            } else {
                Log.v(TAG, msg);
            }
        }
    }

    /**
     * The Log Level:w
     *
     * @param TAG Used to identify the source of a log message.
     *            It usually identifies the class or activity where the log call occurs
     * @param msg The message you would like logged
     */
    public static void w(String TAG, String msg) {
        if (enableLogger) {
            String name = getSingleton().getLogDetail();
            if (name != null) {
                Log.w(TAG, name + " -> " + msg);
            } else {
                Log.w(TAG, msg);
            }
        }
    }

    /**
     * The Log Level:e
     *
     * @param TAG Used to identify the source of a log message.
     *            It usually identifies the class or activity where the log call occurs
     * @param msg The message you would like logged
     */
    public static void e(String TAG, String msg) {
        if (enableLogger) {
            String name = getSingleton().getLogDetail();
            if (name != null) {
                Log.e(TAG, name + " -> " + msg);
            } else {
                Log.e(TAG, msg);
            }
        }
    }

    /**
     * The Log Level:e
     *
     * @param TAG Used to identify the source of a log message.
     *            It usually identifies the class or activity where the log call occurs
     * @param ex  An exception to log
     */
    public static void e(String TAG, Exception ex) {
        if (enableLogger) {
            Log.e(TAG, "ERROR", ex);
        }
    }
}


