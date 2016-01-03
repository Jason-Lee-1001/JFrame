package com.studio.jframework.network.base;

import android.content.Context;

import com.loopj.android.http.RequestParams;

/**
 * Created by Jason
 */
public abstract class BaseRequester {

    public static final String PARSE_JSON_EXCEPTION = "parseJsonError";
    public static final String NETWORK_ERROR = "networkError";

    protected NetworkCallback mCallback;

    public BaseRequester(NetworkCallback callback) {
        mCallback = callback;
    }

    public abstract void postRequest(final Context context, String url, RequestParams params, final String method);
}
