package com.studio.jason.jframe;

import android.content.Context;

import com.studio.jframework.network.volley.VolleyBaseRequest;

/**
 * Created by Jason
 */
public class MyRequest extends VolleyBaseRequest {

    private int retryCount = 0;

    public MyRequest(Context context, String tag) {
        super(context, tag);
    }

    public MyRequest(Context context) {
        super(context);
    }

    @Override
    public void retryRequest() {
        if (retryCount >= 3)
            return;
        sendRequest(mParams, mUrl);
        retryCount++;
    }

    @Override
    public void succeed(String response) {

    }

    @Override
    public void failed(String response) {
        retryRequest();
    }
}
