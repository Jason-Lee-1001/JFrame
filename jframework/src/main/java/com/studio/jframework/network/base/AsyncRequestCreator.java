package com.studio.jframework.network.base;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by Jason
 */
public class AsyncRequestCreator {

    private static AsyncHttpClient mClient = new AsyncHttpClient();

    /**
     * 初始化全局请求类的参数
     *
     * @param retryTimes      重试次数
     * @param retryDuration   重试间隔时间
     * @param enableLog       允许Log日志
     * @param connectTimeout  连接超时时间
     * @param responseTimeout 响应超时时间
     */
    public static void initRequestSettings(int retryTimes, int retryDuration, boolean enableLog,
                                           int connectTimeout, int responseTimeout) {
        mClient.setMaxRetriesAndTimeout(retryTimes, retryDuration);
        mClient.setLoggingEnabled(enableLog);
        mClient.setConnectTimeout(connectTimeout);
        mClient.setResponseTimeout(responseTimeout);
    }

    /**
     * 返回请求类，使用这种方式共用同一个对象
     *
     * @return AsyncHttpClient
     */
    public static AsyncHttpClient getClient() {
        return mClient;
    }

}
