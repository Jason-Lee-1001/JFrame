package com.studio.jframework.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Jason
 */
public class PreferencesUtils {

    private static PreferencesUtils INSTANCE;
    private Context mContext;
    private SharedPreferences mPreference;
    private SharedPreferences.Editor editor;

    private String fileName;

    private PreferencesUtils(Context context, String fileName) {
        this.fileName = fileName;
        mContext = context.getApplicationContext();
        mPreference = context.getSharedPreferences(this.fileName, Context.MODE_PRIVATE);
        editor = mPreference.edit();
    }

    public static PreferencesUtils getInstance(Context context, String fileName) {
        if (INSTANCE == null) {
            INSTANCE = new PreferencesUtils(context, fileName);
        }
        return INSTANCE;
    }

    public SharedPreferences.Editor putString(String key, String value) {
        return editor.putString(key, value);
    }

    public SharedPreferences.Editor putInt(String key, int value) {
        return editor.putInt(key, value);
    }

    public SharedPreferences.Editor putLong(String key, long value) {
        return editor.putLong(key, value);
    }

    public SharedPreferences.Editor putFloat(String key, float value) {
        return editor.putFloat(key, value);
    }

    public SharedPreferences.Editor putBoolean(String key, boolean value) {
        return editor.putBoolean(key, value);
    }

    public void apply(){
        this.editor.apply();
    }

    public String getString(String key, String defValue) {
        return mPreference.getString(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return mPreference.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return mPreference.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return mPreference.getFloat(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPreference.getBoolean(key, defValue);
    }

}
