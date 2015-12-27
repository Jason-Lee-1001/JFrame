package com.example.administrator.staticapp;

import com.studio.jframework.base.CommonApplication;
import com.studio.jframework.base.GlobalObject;

/**
 * Created by Jason
 */
public class MyApp extends CommonApplication{

    public static final String TAG = MyApp.class.getSimpleName();

    /**
     * 全局参数容器，包括屏幕像素，公共参数，
     */
    protected GlobalObject mGlobalObject;
    private static MyApp INSTANCE;

    public static MyApp getInstance(){
        return INSTANCE;
    }

    /**
     * 实现此方法返回BuildConfig.DEBUG的值，用于打印日志和创建crash log
     *
     * @return 返回true表示在debug环境，否则是正式环境
     */
    @Override
    protected boolean isInDebugMode() {
        return BuildConfig.DEBUG;
    }

    /**
     * 主进程创建时调用，App生命周期只调用一次
     */
    @Override
    protected void onApplicationCreate() {
        INSTANCE = this;
    }

    /**
     * Application类onCreate调用时调用
     */
    @Override
    protected void onMainProcessCreate() {

    }


    /**
     * 在主进程创建时调用一次，用于处理初始化的一些操作，如创建文件夹，创建GlobalObject
     */
    @Override
    protected void init() {

    }

    /**
     * 在扫描到crash log时会被调用
     *
     * @param crashInfo
     */
    @Override
    protected void onDiscoverCrashLog(String crashInfo) {

    }

}
