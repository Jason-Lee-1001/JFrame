package com.studio.jframework.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.studio.jframework.network.Task;

/**
 * Created by Jason
 */
public abstract class BaseService extends Service {

    @Override
    public abstract IBinder onBind(Intent intent);

    public static void addTask(Task task){
//        task.execute();
    }
}
