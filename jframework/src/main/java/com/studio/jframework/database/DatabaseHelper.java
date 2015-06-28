package com.studio.jframework.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jason
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "download_task.db";
    private static final int DB_VERSION = 1;
    private static final String SQL_CREATE = "create table";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
                break;
            case 2:
                break;
        }
    }
}
