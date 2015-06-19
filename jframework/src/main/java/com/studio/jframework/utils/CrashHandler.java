package com.studio.jframework.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by Jason
 *
 * Catch the uncaught exception<p/>
 * Usage:<p/>
 * in Application, method onCreate;<p/>
 * CrashHandler crashHandler = CrashHandler.getInstance();<p/>
 * crashHandler.init(getApplicationContext(),"/crash/", "Application crashed, exiting...", new ExceptionOperator(){...});<p/>
 * add permission: <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/><p/>
 */
public class CrashHandler implements UncaughtExceptionHandler {

    //Default path to save crash file
    private static String crashFilePath = "/Crash/";

    //The singleton of this class
    private static CrashHandler INSTANCE;

    //The message for toast to show
    public String showMessage = null;
    private Context mContext;
    private UncaughtExceptionHandler mDefaultHandler;
    private Map<String, String> crashInfo = new HashMap<>();
    private ExceptionOperator exceptionOperator;

    private CrashHandler() {
    }

    /**
     * Gain the instance of this class
     *
     * @return The instance of this class
     */
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    /**
     * Initialization of this crash handler
     *
     * @param context     Context
     * @param filePath    C log file directory under root path
     * @param showMessage The message for toast when crash happens
     * @param operator    The operator to tidy up the work after the crash
     */
    public void init(Context context, String filePath, String showMessage, ExceptionOperator operator) {
        exceptionOperator = operator;
        if (crashFilePath != null && !filePath.equals("")) {
            crashFilePath = filePath;
        }
        if (showMessage != null && !showMessage.equals("")) {
            this.showMessage = showMessage;
        }
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * The callback method that catch the exception, you can handle the
     * exception here
     */
    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        boolean isHandle = handleException(exception);
        if (!isHandle && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, exception);
        } else {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            exceptionOperator.onExceptionThrows();
        }
    }

    private boolean handleException(Throwable exception) {
        if (exception == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                if (showMessage != null) {
                    Toast.makeText(mContext, showMessage, Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
            }
        }.start();
        collectDeviceInfo(mContext);
        saveCrashInfo2File(exception);
        return true;
    }

    /**
     * Collect Device info
     *
     * @param context Context
     */
    @SuppressLint("SimpleDateFormat")
    public void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                crashInfo.put("versionName", versionName);
                crashInfo.put("versionCode", versionCode);
                crashInfo.put("currentTime", formatter.format(new Date(System.currentTimeMillis())));
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                crashInfo.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Save Info to files
     *
     * @param ex the exception this class throws
     * @return save crash info to file successfully will return true, false otherwise
     */
    private boolean saveCrashInfo2File(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : crashInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + crashFilePath;
                String filePath = path + "crash.log";
                File dir = new File(path);
                if (!dir.exists()) {
                    if (dir.mkdirs()) {
                        FileOutputStream fos = new FileOutputStream(filePath);
                        fos.write(sb.toString().getBytes());
                        fos.close();
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gain crash info from file if exist
     *
     * @return Crash info, will return null if file not exist
     */
    public static String getCrashInfo() {
        String info = null;
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + crashFilePath + "crash.log";
        File f = new File(filePath);
        if (f.exists()) {
            StringBuilder log = new StringBuilder();
            try {
                FileReader reader = new FileReader(f);
                BufferedReader bReader = new BufferedReader(reader);
                String buff;
                while ((buff = bReader.readLine()) != null) {
                    log.append(buff);
                }
                bReader.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            info = log.toString();
            if (TextUtils.isEmpty(info)) {
                return null;
            }
        }
        return info;
    }

    public interface ExceptionOperator {
        /**
         * The callback for you to tidy up the job, such as save the instance<p/>
         * or exit the whole application
         */
        void onExceptionThrows();
    }

}
