package com.breakpointresume.dao;

import com.breakpointresume.model.ThreadInfo;

import java.util.List;

/**
 * Created by Jason
 */
public interface ThreadDao {
    public void insertThread(ThreadInfo info);
    public void deleteThread(String url, int thread_id);
    public void updateThread(String url, int thread_id, int finished);
    public List<ThreadInfo> queryThreads(String url);
    public boolean isExists(String url, int thread_id);
}
