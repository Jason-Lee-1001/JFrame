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

    public VolleyBaseRequest(Context context, String tag) {
        mContext = context;
        mTag = tag;
    }

    public VolleyBaseRequest(Context context) {
        this(context, null);
    }

    public String getTag() {
        return mTag;
    }

    public abstract void retryRequest();

    public abstract void succeed(String response);

    public abstract void failed(String response);

    public void sendRequest(Map<String, String> params, String url) {
        this.mParams = params;
        this.mUrl = url;
        VolleyStringRequest request = new VolleyStringRequest(Request.Method.POST, mUrl, mParams, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                succeed(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                failed(VolleyErrorHelper.getMessage(error));
            }
        });
        if (!TextUtils.isEmpty(mTag)) {
            VolleyController.getInstance(mContext).addToQueue(request, mTag);
        } else {
            VolleyController.getInstance(mContext).addToQueue(request);
        }
    }
}
