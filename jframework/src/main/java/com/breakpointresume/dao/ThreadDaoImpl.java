package com.breakpointresume.dao;

import android.database.sqlite.SQLiteDatabase;

import com.studio.jframework.database.DatabaseHelper;
import com.breakpointresume.model.ThreadInfo;

import java.util.List;

/**
 * Created by Jason
 */
public class ThreadDaoImpl implements ThreadDao {

    private DatabaseHelper mHelper = null;

    @Override
    public void insertThread(ThreadInfo info) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
//        db.insert()
//        or db.exeSQL();
    }

    @Override
    public void deleteThread(String url, int thread_id) {

    }

    @Override
    public void updateThread(String url, int thread_id, int finished) {

    }

    @Override
    public List<ThreadInfo> queryThreads(String url) {
        return null;
    }

    @Override
    public boolean isExists(String url, int thread_id) {
        return false;
    }
}
