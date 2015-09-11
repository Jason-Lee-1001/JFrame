package com.studio.jframework.network;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.studio.jframework.network.volley.VolleyController;
import com.studio.jframework.network.volley.VolleyErrorHelper;
import com.studio.jframework.network.volley.VolleyStringRequest;
import com.studio.jframework.utils.LogUtils;

import java.util.Map;

/**
 * Created by Jason
 */
public abstract class Task {

    public static final String TAG = "Task";

    protected Context mContext;
    private String mType;
    private String mTag;
    private Map<String, String> mParams;
    private int mRetryCount = 0;
    private boolean mCache = false;

    public Task(Context context, String type, String tag, Map<String, String> params, int retryCount, boolean cache) {
        mContext = context;
        mType = type;
        mTag = tag;
        mParams = params;
        mRetryCount = retryCount;
        mCache = cache;
    }

    public Task(Context context, String type, String tag, Map<String, String> params) {
        mContext = context;
        mType = type;
        mTag = tag;
        mParams = params;
    }

    public String getType() {
        return mType;
    }

    public String getTag() {
        return mTag;
    }

    public int getRetryCount() {
        return mRetryCount;
    }

    public boolean isCache() {
        return mCache;
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    public abstract void succeed(String response);

    public abstract void failed(String response);

    public void execute() {
        if (!TextUtils.isEmpty(mTag)) {
            LogUtils.d(mTag, mType);
        }
        if (mParams != null) {
            LogUtils.d(mTag, mParams.toString());
        }
        VolleyStringRequest request = new VolleyStringRequest(Request.Method.POST, mType, mParams, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                succeed(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                failed(VolleyErrorHelper.getMessage(error));
                if (mRetryCount > 0) {
                    execute();
                    mRetryCount--;
                }
            }
        });
        if (!TextUtils.isEmpty(mTag)) {
            VolleyController.getInstance(mContext).addToQueue(request, mTag);
        } else {
            VolleyController.getInstance(mContext).addToQueue(request);
        }
    }

}
