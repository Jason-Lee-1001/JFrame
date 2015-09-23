package com.studio.jframework.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

import java.util.LinkedList;
import java.util.Queue;

final class HandlerPoster extends Handler {
    private final int ASYNC = 0x1;
    private final int SYNC = 0x2;
    private final Queue asyncPool;
    private final Queue syncPool;
    private final int maxMillisInsideHandleMessage;
    private boolean asyncActive;
    private boolean syncActive;

    HandlerPoster(Looper looper, int maxMillisInsideHandleMessage) {
        super(looper);
        this.maxMillisInsideHandleMessage = maxMillisInsideHandleMessage;
        asyncPool = new LinkedList<>();
        syncPool = new LinkedList<>();
    }

    void dispose() {
        this.removeCallbacksAndMessages(null);
        this.asyncPool.clear();
        this.syncPool.clear();
    }

    void async(Runnable runnable) {
        synchronized (asyncPool) {
            asyncPool.offer(runnable);
            if (!asyncActive) {
                asyncActive = true;
                if (!sendMessage(obtainMessage(ASYNC))) {
//                    throw new GeniusException(Could not send handler message);
                }
            }
        }
    }

    void sync(SyncPost post) {
        synchronized (syncPool) {
            syncPool.offer(post);
            if (!syncActive) {
                syncActive = true;
                if (!sendMessage(obtainMessage(SYNC))) {
//                    throw new GeniusException(Could not send handler message);
                }
            }
        }
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what == ASYNC) {
            boolean rescheduled = false;
            try {
                long started = SystemClock.uptimeMillis();
                while (true) {
                    Runnable runnable = (Runnable) asyncPool.poll();
                    if (runnable == null) {
                        synchronized (asyncPool) {
                            // Check again, this time in synchronized
                            runnable = (Runnable) asyncPool.poll();
                            if (runnable == null) {
                                asyncActive = false;
                                return;
                            }
                        }
                    }
                    runnable.run();
                    long timeInMethod = SystemClock.uptimeMillis() - started;
                    if (timeInMethod >= maxMillisInsideHandleMessage) {
                        if (!sendMessage(obtainMessage(ASYNC))) {
//                            throw new GeniusException(Could not send handler message);
                        }
                        rescheduled = true;
                        return;
                    }
                }
            } finally {
                asyncActive = rescheduled;
            }
        } else if (msg.what == SYNC) {
            boolean rescheduled = false;
            try {
                long started = SystemClock.uptimeMillis();
                while (true) {
                    SyncPost post = (SyncPost) syncPool.poll();
                    if (post == null) {
                        synchronized (syncPool) {
                            // Check again, this time in synchronized
                            post = (SyncPost) syncPool.poll();
                            if (post == null) {
                                syncActive = false;
                                return;
                            }
                        }
                    }
                    post.run();
                    long timeInMethod = SystemClock.uptimeMillis() - started;
                    if (timeInMethod >= maxMillisInsideHandleMessage) {
                        if (!sendMessage(obtainMessage(SYNC))) {
//                            throw new GeniusException(Could not send handler message);
                        }
                        rescheduled = true;
                        return;
                    }
                }
            } finally {
                syncActive = rescheduled;
            }
        } else super.handleMessage(msg);
    }
}