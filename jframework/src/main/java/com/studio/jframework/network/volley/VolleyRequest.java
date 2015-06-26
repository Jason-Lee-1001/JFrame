package com.studio.jframework.network.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Sub class of StringRequest, get string response with the default retry policy<p/>
 * Timeout:15sec
 * RequestCount:1
 */
public class VolleyRequest extends StringRequest {

    private Map<String, String> params;
    private static final int mTimeOut = 15 * 1000;
    private static final int mRetryTimes = 1;

    public VolleyRequest(String url, Map<String, String> params, Listener<String> listener, ErrorListener errorListener) {
        super(url, listener, errorListener);
        this.params = params;
        setRetryPolicy(new DefaultRetryPolicy(mTimeOut, mRetryTimes, 1f));
    }

    public VolleyRequest(String url, Map<String, String> params, int timeOut, int retryTimes, Listener<String> listener, ErrorListener errorListener) {
        super(url, listener, errorListener);
        this.params = params;
        setRetryPolicy(new DefaultRetryPolicy(timeOut, retryTimes, 1f));
    }

    public VolleyRequest(int method, String url, Map<String, String> params, Listener<String> listener, ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.params = params;
        setRetryPolicy(new DefaultRetryPolicy(mTimeOut, mRetryTimes, 1f));
    }

    public VolleyRequest(int method, String url, Map<String, String> params, int timeOut, int retryTimes, Listener<String> listener, ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.params = params;
        setRetryPolicy(new DefaultRetryPolicy(timeOut, retryTimes, 1f));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (params != null) {
            return params;
        }
        return super.getParams();
    }
}
