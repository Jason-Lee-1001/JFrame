package com.studio.jason.jframe;

import android.app.Application;

import com.studio.jframework.utils.CrashHandler;
import com.studio.jframework.utils.ExitAppUtils;
import com.studio.jframework.utils.LogUtils;

/**
 * Created by Jason
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.setEnable(BuildConfig.DEBUG);
        CrashHandler.getInstance().init(this, null, "渣渣,又崩了!!", new CrashHandler.ExceptionOperator() {
            @Override
            public void onExceptionThrows() {
                ExitAppUtils.getInstance().exit();
            }
        });
        if(CrashHandler.getCrashInfo()!=null){
            uploadCrashLog();
        }
    }

    private void uploadCrashLog() {

    }
}
