package com.studio.jframework.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Jason
 *
 * need to be fix
 */
public class FileUtils {

    public static final String TAG = "FileUtils";
    private static String appFolder;

    public FileUtils() {
    }

    /**
     * Judge that the external storage is available or not
     *
     * @return True if the external storage is available, false otherwise
     */
    public boolean isExternalStorageAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * Get the public storage folder path
     *
     * @param type The type of storage directory to return. Should be one of DIRECTORY_MUSIC,
     *             DIRECTORY_PODCASTS, DIRECTORY_RINGTONES, DIRECTORY_ALARMS,
     *             DIRECTORY_NOTIFICATIONS, DIRECTORY_PICTURES, DIRECTORY_MOVIES,
     *             DIRECTORY_DOWNLOADS, or DIRECTORY_DCIM. May not be null.
     * @return The File path for the directory. Note that this directory may not yet
     * exist, so you must make sure it exists before using it such as with File.mkdirs().
     * For example: storage/emulated/0/Movies
     */
    public String getPublicDirectory(String type) {
        return Environment.getExternalStoragePublicDirectory(type).getAbsolutePath();
    }

    /**
     * Get the external cache folder path
     *
     * @param context Context
     * @return The external cache folder of the package, for example
     * For example: storage/emulated/0/Android/data/"packageName"/cache
     */
    public String getExternalCacheDir(Context context) {
        if (isExternalStorageAvailable()) {
            return context.getExternalCacheDir().getAbsolutePath();
        }
        return null;
    }

    /**
     * Get the main folder of the app
     *
     * @return The path the app to store files
     */
    public String getAppFolder() {
        return appFolder;
    }

    /**
     * Set the path of main folder of the app
     *
     * @param appFolder The path you want to store files
     */
    public void setAppFolder(String appFolder) {
        FileUtils.appFolder = appFolder;
    }

    /**
     * Construct the mainFolder of the application
     * Such as storage/emulated/0/Jumook
     *
     * @return
     */
    public boolean makeMainFolder() {
        File mainPath = new File(appFolder);
        if (!mainPath.exists()) {
            return mainPath.mkdirs();
        }
        return false;
    }

    public boolean saveBitmap(String key, Bitmap bitmap) {
        if (key == null || bitmap == null || appFolder == null) {
            LogUtils.e(TAG, "save bitmap failed");
            return false;
        }
        if (!TextUtils.isEmpty(key) && isExternalStorageAvailable()) {
            File dir = new File(appFolder);
            File f = new File(dir, key);
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                return true;
            } catch (IOException e) {
                LogUtils.e(TAG, "save bitmap failed, io exception");
            }
        }
        return false;
    }

    public Bitmap readBitmap(String key) {
        Bitmap bitmap = null;
        if (!TextUtils.isEmpty(key) && isExternalStorageAvailable() && appFolder != null) {
            File bitmapFile = new File(appFolder + File.separator + key);
            if (bitmapFile.exists() && !bitmapFile.isDirectory()) {
                FileInputStream fis;
                try {
                    fis = new FileInputStream(bitmapFile);
                    bitmap = BitmapFactory.decodeStream(fis);
                    fis.close();
                } catch (IOException e) {
                    LogUtils.e(TAG, "read bitmap failed, io exception");
                }
            }
        }
        return bitmap;
    }

    public String getEncryptKey(String url) {
        return MD5Utils.get32bitsMD5(url, MD5Utils.ENCRYPT_METHOD_1);
    }
}
