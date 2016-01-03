package com.studio.jframework.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A usefully tool to operate file, each FileUtils stands for one folder' operation
 *
 * Created by Jason
 */
public class FileUtils {

    public static final String TAG = FileUtils.class.getSimpleName();
    /**
     * The name of the folder to be operated
     */
    private String folderName;

    /**
     * The path of the main folder under external storage root
     */
    private String folderPath;
    private int mode;
    private Context mContext;

    public static final int EXTERNAL_CACHE = 1;
    public static final int NORMAL_FOLDER = 2;


    public FileUtils(Context context, int mode, String folderName) throws IllegalArgumentException {
        if (mode != 1 && mode != 2) {
            throw new IllegalArgumentException("Mode should be 1 or 2");
        }
        mContext = context;
        this.mode = mode;
        this.folderName = folderName;
        this.createAppMainFolder();
    }

    /**
     * Judge that the external storage is available or not
     *
     * @return True if the external storage is available, false otherwise
     */
    public static boolean isExternalStorageAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
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
    public static String getPublicDirectory(String type) {
        return Environment.getExternalStoragePublicDirectory(type).getAbsolutePath();
    }

    /**
     * Get the external cache folder path
     *
     * @param context Context
     * @return The external cache folder of the package, for example
     * For example: storage/emulated/0/Android/data/"packageName"/cache
     */
    public static String getExternalCacheDir(Context context) {
        if (isExternalStorageAvailable()) {
            return context.getExternalCacheDir().getAbsolutePath();
        }
        return null;
    }

    /**
     * Get the internal cache folder path
     *
     * @param context Context
     * @return The internal cache folder of the package, for example
     * For example: data/data/"packageName"/cache
     */
    public static String getInternalCacheDir(Context context) {
        return context.getCacheDir().getAbsolutePath();
    }

    /**
     * Set the path of main folder of the app under the root of external path
     *
     * @return True if the folder is created successfully, false otherwise
     */
    public boolean createAppMainFolder() {
        if (folderName == null || folderName.equals("")) {
            LogUtils.e(TAG, "Create folder failed, folderName should not be empty in constructor");
            return false;
        }
        if (isExternalStorageAvailable()) {
            folderPath = mode == 2 ? Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + folderName + File.separator :
                    getExternalCacheDir(mContext) + File.separator + folderName + File.separator;
            LogUtils.i(TAG, folderPath);
            File file = new File(folderPath);
            return file.exists() || file.mkdirs();
        } else {
            return false;
        }
    }

    /**
     * Get the main folder of the app
     *
     * @return The path the app to store files,
     * will return null if the external storage is not available
     */
    public String getAppFolderPath() {
        if (mode == NORMAL_FOLDER && isExternalStorageAvailable() && folderPath != null) {
            return folderPath;
        }
        return null;
    }

    /**
     * Create a sub folder, if mode is EXTERNAL_CACHE, will create a new folder under cache folder
     * if mode is NORMAL_FOLDER, will create a folder under the main app folder
     *
     * @param subFolderName The folder name
     * @return True if the folder is created successfully, false otherwise
     */
    public boolean createSubFolder(String subFolderName) {
        switch (mode) {
            case EXTERNAL_CACHE:
                File cacheDir = new File(getExternalCacheDir(mContext) + File.separator + subFolderName + File.separator);
                if (!cacheDir.exists()) {
                    return cacheDir.mkdirs();
                }
            case NORMAL_FOLDER:
                File normalDir = new File(folderPath + File.separator + subFolderName + File.separator);
                if (!normalDir.exists()) {
                    return normalDir.mkdirs();
                }
        }
        LogUtils.e(TAG, "create sub folder failed");
        return false;
    }

    /**
     * Save bitmap to external storage
     *
     * @param key    The name of the file
     * @param bitmap The bitmap to be stored
     * @return True if successfully, false otherwise
     */
    public boolean saveBitmap(String key, Bitmap bitmap) {
        if (key == null || bitmap == null || folderPath == null) {
            LogUtils.e(TAG, "save bitmap failed");
            return false;
        }
        if (isExternalStorageAvailable()) {
            File dir = new File(folderPath);
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

    /**
     * Read a bitmap from local storage
     *
     * @param key The name of the file
     * @return The bitmap instance, will return null if the file not found
     */
    public Bitmap readBitmap(String key) {
        Bitmap bitmap = null;
        if (isExternalStorageAvailable() && folderPath != null) {
            File bitmapFile = new File(folderPath + key);
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
}
