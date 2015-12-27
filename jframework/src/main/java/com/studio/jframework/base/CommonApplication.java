package com.studio.jframework.base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.studio.jframework.R;
import com.studio.jframework.network.base.AsyncRequestCreator;
import com.studio.jframework.utils.CrashHandler;
import com.studio.jframework.utils.ExitAppUtils;
import com.studio.jframework.utils.LogUtils;
import com.studio.jframework.utils.PackageUtils;

/**
 * 子类要添加getInstance方法方便App调用，CommonApplication中已经初始化网络请求，图片加载，奔溃日志的初始化
 * 其他的操作请在init方法中实现
 * Created by Jason
 */
abstract public class CommonApplication extends Application {

    private boolean mIsDebug;

    @Override
    public void onCreate() {
        super.onCreate();
        onApplicationCreate();
        mIsDebug = isInDebugMode();
        //为log设置开关
        LogUtils.setEnable(mIsDebug);
        if (getPackageName().equals(PackageUtils.getCurrentProcessName(this))) {
            AsyncRequestCreator.initRequestSettings(0, 1000, false, 5000, 5000);
            onMainProcessCreate();
            CrashHandler.getInstance().init(this, "." + getPackageName(), getString(R.string.crash_occur_toast), mIsDebug, new CrashHandler.ExceptionOperator() {
                @Override
                public void onExceptionThrows() {
                    ExitAppUtils.getInstance().exit();
                }
            });
            init();
            Fresco.initialize(this);
        }
    }

    /**
     * 实现此方法返回BuildConfig.DEBUG的值，用于打印日志和创建crash log
     *
     * @return 返回true表示在debug环境，否则是正式环境
     */
    protected abstract boolean isInDebugMode();

    /**
     * 主进程创建时调用，App生命周期只调用一次
     */
    protected abstract void onApplicationCreate();

    /**
     * Application类onCreate调用时调用
     */
    protected abstract void onMainProcessCreate();

    /**
     * 在主进程创建时调用一次，用于处理初始化的一些操作，如创建文件夹，创建GlobalObject
     */
    protected abstract void init();

    /**
     * 在扫描到crash log时会被调用
     *
     * @param crashInfo 错误日志内容
     */
    protected abstract void onDiscoverCrashLog(String crashInfo);

}
