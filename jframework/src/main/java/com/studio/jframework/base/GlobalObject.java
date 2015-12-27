package com.studio.jframework.base;

import android.content.Context;

import com.studio.jframework.utils.SizeUtils;

import java.util.HashMap;

/**
 * Created by Jason
 */
public class GlobalObject {

    /**
     * GlobalObject instance, one and only
     */
    private static GlobalObject INSTANCE;

    public String mAppToken;
    public String mSign;
    public String mSessionId;
    public String mUserId;

    public int mScreenWidth;
    public int mScreenHeight;

    private GlobalObject(Context context){
        mScreenWidth = SizeUtils.getScreenWidth(context);
        mScreenHeight = SizeUtils.getScreenHeight(context);
    }

    public static GlobalObject getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new GlobalObject(context);
        }
        return INSTANCE;
    }

    public HashMap<String, String> getCommonParams(){
        HashMap<String, String> params = new HashMap<>();
        return params;
    }



}
