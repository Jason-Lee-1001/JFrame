package com.studio.application.request;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.studio.jframework.database.DatabaseHelper;
import com.studio.jframework.network.Task;
import com.studio.jframework.utils.LogUtils;

import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Jason
 */
public class GetLyric extends Task {

    public GetLyric(Context context, String type, String tag, Map<String, String> params, int retryCount, boolean cache) {
        super(context, type, tag, params, retryCount, cache);
    }

    @Override
    public void succeed(String response) {
        LogUtils.d("GetLyric", response);
        Bundle bundle = new Bundle();
        bundle.putString("GetLyric", response);
        EventBus.getDefault().post(bundle);
        DatabaseHelper helper = new DatabaseHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("create table "+getType()+" if not exist");
    }

    @Override
    public void failed(String response) {

    }
}
