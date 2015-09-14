package com.studio.jframework.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.studio.jframework.network.Task;
import com.studio.jframework.utils.ExitAppUtils;

/**
 * Created by Jason
 */
public abstract class BaseService extends Service {

    @Override
    public abstract IBinder onBind(Intent intent);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ExitAppUtils.getInstance().addService(this);
        return super.onStartCommand(intent, flags, startId);
    }

    public synchronized static void addTask(Task task){
//        task.execute();
    }

    @Override
    public void onDestroy() {
        ExitAppUtils.getInstance().removeService(this);
        super.onDestroy();
    }
}
