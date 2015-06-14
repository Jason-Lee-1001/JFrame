package com.studio.jframework.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

/**
 * Created by Jason
 */
public class FileUtils {

    private static String appFolder;

    public FileUtils(){}

    /**
     * Judge that the external storage is available or not
     * @return True if the external storage is available, false otherwise
     */
    public boolean isExternalStorageAvailable(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * Get the public storage folder path
     * @param type The type of storage directory to return. Should be one of DIRECTORY_MUSIC,
     *             DIRECTORY_PODCASTS, DIRECTORY_RINGTONES, DIRECTORY_ALARMS,
     *             DIRECTORY_NOTIFICATIONS, DIRECTORY_PICTURES, DIRECTORY_MOVIES,
     *             DIRECTORY_DOWNLOADS, or DIRECTORY_DCIM. May not be null.
     * @return The File path for the directory. Note that this directory may not yet
     * exist, so you must make sure it exists before using it such as with File.mkdirs().
     * For example: storage/emulated/0/Movies
     */
    public String getPublicDirectory(String type){
        return Environment.getExternalStoragePublicDirectory(type).getAbsolutePath();
    }

    /**
     * Get the external cache folder path
     * @param context Context
     * @return The external cache folder of the package, for example
     * For example: storage/emulated/0/Android/data/"packageName"/cache
     */
    public String getExternalCacheDir(Context context){
        if(isExternalStorageAvailable()){
            return context.getExternalCacheDir().getAbsolutePath();
        }
        return null;
    }



    /**
     * Get the main folder of the app
     * @return The path the app to store files
     */
    public static String getAppFolder() {
        return appFolder;
    }

    /**
     * Set the path of main folder of the app
     * @param appFolder The path you want to store files
     */
    public static void setAppFolder(String appFolder) {
        FileUtils.appFolder = appFolder;
    }

    public void makeMainFolder(){

    }

    public void saveBitmap(String url, Bitmap bitmap){

    }

    public Bitmap readBitmap(String url){
        return null;
    }
}
