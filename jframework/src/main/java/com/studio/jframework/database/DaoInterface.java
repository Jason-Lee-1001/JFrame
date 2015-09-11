package com.studio.jframework.database;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Jason
 */
public interface DaoInterface {

    public <T> boolean insert(T t);
    public <T> long delete(T t);
    public Cursor query(int id);
    public boolean update(ContentValues cValues);

}
