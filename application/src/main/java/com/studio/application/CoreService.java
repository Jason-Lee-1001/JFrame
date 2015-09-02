package com.studio.application;

import android.content.Intent;
import android.os.IBinder;

import com.studio.application.request.GetLyric;
import com.studio.jframework.base.BaseService;

import de.greenrobot.event.EventBus;

/**
 * Created by Jason
 */
public class CoreService extends BaseService {

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onEvent(GetLyric task){
        task.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
