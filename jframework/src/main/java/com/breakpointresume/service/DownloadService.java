package com.breakpointresume.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.breakpointresume.model.DownloadFileInfo;
import com.studio.jframework.utils.LogUtils;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jason
 */
public class DownloadService extends Service {

    public static final String DOWNLOAD_PATH = "";

    public static final String TAG = "DownloadService";
    public static final String FILE_INFO = "file_info";

    public static final String ACTION_START = "action_start";
    public static final String ACTION_STOP = "action_stop";

    public static final int MSG_TASK = 1;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ACTION_START.equals(intent.getAction())) {
            DownloadFileInfo fileInfo = intent.getParcelableExtra(FILE_INFO);
            LogUtils.i(TAG, ACTION_START);
        }
        if (ACTION_STOP.equals(intent.getAction())) {
            DownloadFileInfo fileInfo = intent.getParcelableExtra(FILE_INFO);
            LogUtils.i(TAG, ACTION_STOP);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_TASK:
                    DownloadFileInfo fileInfo = (DownloadFileInfo) msg.obj;
                    LogUtils.i(TAG, fileInfo.toString());
                    break;
            }
        }
    };

    class WorkerThread extends Thread {

        private DownloadFileInfo mFileInfo = null;

        public WorkerThread(DownloadFileInfo fileInfo) {
            mFileInfo = fileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile randomAccessFile = null;
            try {
                URL url = new URL(mFileInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                int length = -1;
                if (conn.getResponseCode() == HttpStatus.SC_OK) {
                    length = conn.getContentLength();
                }
                if (length <= 0) {
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, mFileInfo.getFileName());
                //rwd: readable, writable, deletable
                randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.setLength(length);
                mFileInfo.setLength(length);
                handler.obtainMessage(MSG_TASK, mFileInfo).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.disconnect();
                    }
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
