package com.studio.jframework.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.studio.jframework.R;
import com.studio.jframework.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Usage:
 *
 * Intent intent = new Intent();
 * intent.putExtra(SMALL_ICON_ID, R.drawable.icon);
 * intent.putExtra(TICKER, "Downloading");
 * intent.putExtra(MESSAGE, "Downloading");
 * intent.putExtra(DOWNLOAD_URL, "http://aswit.com/sock.apk");
 * intent.setClass(this, BaseDownloadService.class);
 * startService(intent);
 *
 * Will broadcast download completed when finished
 * Created by Jason
 */
public abstract class BaseDownloadService extends IntentService {

    public static final String DOWNLOAD_URL = "download_url";
    public static final String SMALL_ICON_ID = "small_icon_id";
    public static final String TICKER = "ticker";
    public static final String MESSAGE = "message";

    public static final String BROADCAST_ACTION = "com.studio.jframework.download_completed";
    public static final String FILE_PATH = "download_file_path";

    private String mDownloadUrl;
    private String mMessage;
    private int mFileLength;
    private int mDownloadLength;

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    private Handler handler = new Handler();
    private RemoteViews mRemoteView;

    public BaseDownloadService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mDownloadUrl = intent.getStringExtra(DOWNLOAD_URL);
        int iconId = intent.getIntExtra(SMALL_ICON_ID, 0);
        String ticker = intent.getStringExtra(TICKER);
        mMessage = intent.getStringExtra(MESSAGE);

        if (FileUtils.isExternalStorageAvailable()) {
            File dirs = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download");
            if (!dirs.exists()) {
                dirs.mkdir();
            }
            File file = new File(dirs, "file.apk");

            mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(this);
            mRemoteView = new RemoteViews(getPackageName(), R.layout.download_remote_view);
            mRemoteView.setProgressBar(R.id.remote_progress_bar, 100, 0, false);
            if (!TextUtils.isEmpty(mMessage)) {
                mRemoteView.setTextViewText(R.id.message, mMessage);
            }
            mBuilder.setContent(mRemoteView);
            if (iconId != 0) {
                mBuilder.setSmallIcon(iconId);
            }
            mBuilder.setWhen((System.currentTimeMillis()));
            mBuilder.setOngoing(true);
            if (!TextUtils.isEmpty(ticker)) {
                mBuilder.setTicker(ticker);
            }
            mNotificationManager.notify(0, mBuilder.build());

            downloadFile(mDownloadUrl, file);
            mNotificationManager.cancel(0);
            Intent sendIntent = new Intent(BROADCAST_ACTION);
            sendIntent.putExtra(FILE_PATH, file.getPath());
            sendBroadcast(sendIntent);
        }
    }

    private void downloadFile(String downloadUrl, File file) {
        FileOutputStream fos = null;
        InputStream inputStream = null;
        try {
            fos = new FileOutputStream(file);
            URL url = new URL(downloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(3000);
            mFileLength = Integer.valueOf(connection.getHeaderField("Content-Length"));
            inputStream = connection.getInputStream();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                handler.post(run);
                byte[] buffer = new byte[8192];
                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    mDownloadLength = mDownloadLength + len;
                }
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Runnable run = new Runnable() {
        public void run() {
            int currentProgress = mDownloadLength * 100 / mFileLength;
            mRemoteView.setProgressBar(R.id.remote_progress_bar, 100, currentProgress, false);
            mBuilder.setContent(mRemoteView);
            mNotificationManager.notify(0, mBuilder.build());
            handler.postDelayed(run, 1000);
        }
    };
}
