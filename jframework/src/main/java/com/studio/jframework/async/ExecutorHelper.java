package com.studio.jframework.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>Usage:
 * <p>ExecutorHelper helper = new ExecutorHelper(4);
 * <p>helper.execute(new BackgroundTask(){...});
 * <p>helper.execute(new BackgroundTask(){...});
 * <p/>
 * Created by Jason
 */
public class ExecutorHelper {

    private ExecutorService mThreadPool = null;
    private int mThreadsCount;

    /**
     * 实例化此类并作出初始化操作
     *
     * @param num 线程池中有多少个线程
     */
    public ExecutorHelper(int num) {
        mThreadsCount = num;
        synchronized (ExecutorService.class) {
            if (mThreadPool == null) {
                mThreadPool = Executors.newFixedThreadPool(num);
            }
        }
    }

    /**
     * 获取线程池中的线程数量
     *
     * @return 线程池中的线程数量
     */
    public int getThreadsCount() {
        return this.mThreadsCount;
    }

    /**
     * 获取ExecutorService实例
     *
     * @return ExecutorService
     */
    public ExecutorService getThreadPool() {
        return this.mThreadPool;
    }

    /**
     * 执行传入的BackgroundTask
     *
     * @param task BackgroundTask实例，可使用匿名内部类
     */
    public void execute(BackgroundTask task) {
        this.getThreadPool().execute(new Worker(task));
    }


    /**
     * 取消线程池中的任务
     */
    public void shutDown() {
        mThreadPool.shutdown();
    }

    /**
     * 用作装载后台处理业务的任务类
     */
    public abstract class BackgroundTask {
        /**
         * 处理后台任务的方法体，请注意此方法内部不可以执行UI操作
         */
        public abstract void doInBackground();
    }

    /**
     * 工作线程
     */
    public class Worker implements Runnable {
        private BackgroundTask mTask = null;

        public Worker(BackgroundTask task) {
            mTask = task;
        }

        @Override
        public void run() {
            mTask.doInBackground();
        }
    }
}
