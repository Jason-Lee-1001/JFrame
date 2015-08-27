package com.studio.jframework.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>Usage:
 * <p>ExecutorHelper helper = new ExecutorHelper(4);
 * <p>helper.execute(new BackgroundTask(){...});
 * <p>helper.execute(new BackgroundTask(){...});
 *
 * @author Jason
 */
public class ExecutorHelper {

    private ExecutorService mThreadPool = null;
    private int mThreadsCount;

    /**
     * Initialize the thread pool and specify the thread counts
     *
     * @param num Indicate the number of threads you want in the thread pool
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
     * Get how many threads in the pool
     *
     * @return The size of the thread pool
     */
    public int getThreadsCount() {
        return this.mThreadsCount;
    }

    /**
     * Get the ExecutorService instance
     *
     * @return The ExecutorService instance
     */
    public ExecutorService getThreadPool() {
        return this.mThreadPool;
    }

    /**
     * Get a thread from the pool and execute the task
     *
     * @param task The BackgroundTask instance, just new it
     */
    public void execute(BackgroundTask task) {
        this.getThreadPool().execute(new Worker(task));
    }


    /**
     * Cancel the pending task in the pool
     */
    public void shutDown() {
        mThreadPool.shutdown();
    }

    public interface BackgroundTask {
        /**
         * Please mind that you cannot touch the UI thread in this method
         */
        void doInBackground();
    }

    /**
     * The worker thread
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
