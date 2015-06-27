package com.studio.jframework.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jason
 * <p/>
 */
public class PreferencesUtils {

    private static PreferencesUtils INSTANCE;
    private Context mContext;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private String fileName = "AppPref";

    private PreferencesUtils(Context context){
        mContext = context.getApplicationContext();
        preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public static PreferencesUtils getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new PreferencesUtils(context);
        }
        return INSTANCE;
    }

    public SharedPreferences.Editor putString(String key, String value){
        if(editor == null){
            editor = preferences.edit();
        }
        return editor.putString(key, value);
    }

    public SharedPreferences.Editor putInt(String key, int value){
        if(editor == null){
            editor = preferences.edit();
        }
        return editor.putInt(key, value);
    }

    public SharedPreferences.Editor putLong(String key, long value){
        if(editor == null){
            editor = preferences.edit();
        }
        return editor.putLong(key, value);
    }

    public SharedPreferences.Editor putFloat(String key, float value){
        if(editor == null){
            editor = preferences.edit();
        }
        return editor.putFloat(key, value);
    }

    public SharedPreferences.Editor putBoolean(String key, boolean value){
        if(editor == null){
            editor = preferences.edit();
        }
        return editor.putBoolean(key, value);
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
        this.preferences = mContext.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    public String getString(String key, String defValue){
        return preferences.getString(key, defValue);
    }

    public int getInt(String key, int defValue){
        return preferences.getInt(key, defValue);
    }

    public long getLong(String key, long defValue){
        return preferences.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue){
        return preferences.getFloat(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue){
        return preferences.getBoolean(key, defValue);
    }

}
