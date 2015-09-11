package com.studio.jframework.network.volley;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Map;

/**
 * Created by Jason
 */
public abstract class VolleyBaseRequest {

    protected Context mContext;
    protected String mTag;
    protected Map<String, String> mParams;
    protected String mUrl;
    protected int mRetryCount = 0;

    public VolleyBaseRequest(Context context, Map<String, String> params, String url, String tag, int retryCount) {
        mContext = context;
        mParams = params;
        mTag = tag;
        mUrl = url;
        mRetryCount = retryCount;
    }

    public VolleyBaseRequest(Context context, Map<String, String> params, String url, int retryCount) {
        this(context, params, url, null, retryCount);
    }

    public VolleyBaseRequest(Context context, Map<String, String> params, String url) {
        this(context, params, url, 0);
    }

    public abstract void succeed(String response);

    public abstract void failed(String response);

    public void sendRequest(final Map<String, String> params) {
        this.mParams = params;
        VolleyStringRequest request = new VolleyStringRequest(Request.Method.POST, mUrl, mParams, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                succeed(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                failed(VolleyErrorHelper.getMessage(error));
                if (mRetryCount > 0) {
                    sendRequest(params);
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
