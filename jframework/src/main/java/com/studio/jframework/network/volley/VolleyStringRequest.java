package com.studio.jframework.network.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Sub class of StringRequest, get string response with the default retry policy<p/>
 * Timeout:15sec
 * RequestCount:1
 */
public class VolleyStringRequest extends StringRequest {

    /*To handle Android Volley Timeout you need to use Retry Policy

    Retry Policy

    Volley provides an easy way to implement your RetryPolicy for your requests.
    Volley sets default Socket & Connection Timeout to 5 secs for all requests.
    RetryPolicy is an interface where you need to implement your logic of how you want to retry a particular request when a timeout happens.

    It deals with these three parameters

    Timeout - Specifies Socket Timeout in millis per every retry attempt.
    Number Of Retries - Number of times retry is attempted.
    Back Off Multiplier - A multiplier which is used to determine exponential time set to socket for every retry attempt.
    For ex. If RetryPolicy is created with these values

    Timeout - 3000 secs, Num of Attempt - 2, Back Off Multiplier - 2
    Attempt 1:

    time = time + (time * Back Off Multiplier );
    time = 3000 + 6000 = 9000
    Socket Timeout = time;
    Request dispatched with Socket Timeout of 9 Secs
    Attempt 2:

    time = time + (time * Back Off Multiplier );
    time = 9000 + 18000 = 27000
    Socket Timeout = time;
    Request dispatched with Socket Timeout of 27 Secs
    So at the end of Attempt 2 if still Socket Timeout happenes Volley would throw a TimeoutError in your UI Error response handler.*/

    private Map<String, String> mHeader;
    private Map<String, String> mParams;
    private static final int M_TIME_OUT = 15 * 1000;
    private static final int M_RETRY_TIMES = DefaultRetryPolicy.DEFAULT_MAX_RETRIES;

    private static String mDefaultCharset = HTTP.UTF_8;

    public static void setDefaultCharset(String defaultCharset){
        mDefaultCharset = defaultCharset;
    }

    public VolleyStringRequest(String url, Map<String, String> params, Listener<String> listener, ErrorListener errorListener) {
        super(url, listener, errorListener);
        this.mParams = params;
        setRetryPolicy(new DefaultRetryPolicy(M_TIME_OUT, M_RETRY_TIMES, 1f));
    }

    public VolleyStringRequest(String url, Map<String, String> params, int timeOut, int retryTimes, Listener<String> listener, ErrorListener errorListener) {
        super(url, listener, errorListener);
        this.mParams = params;
        setRetryPolicy(new DefaultRetryPolicy(timeOut, retryTimes, 1f));
    }

    public VolleyStringRequest(int method, String url, Map<String, String> params, Listener<String> listener, ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.mParams = params;
        setRetryPolicy(new DefaultRetryPolicy(M_TIME_OUT, M_RETRY_TIMES, 1f));
    }

    public VolleyStringRequest(int method, String url, Map<String, String> params, int timeOut, int retryTimes, Listener<String> listener, ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.mParams = params;
        setRetryPolicy(new DefaultRetryPolicy(timeOut, retryTimes, 1f));
    }

    public VolleyStringRequest(int method, String url, Map<String, String> header, Map<String, String> params, Listener<String> listener, ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.mHeader = header;
        this.mParams = params;
        setRetryPolicy(new DefaultRetryPolicy(M_TIME_OUT, M_RETRY_TIMES, 1f));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (mHeader != null) {
            return mHeader;
        }
        return super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (mParams != null) {
            return mParams;
        }
        return super.getParams();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    /**
     * Returns the charset specified in the Content-Type of this header,
     * or the HTTP default (ISO-8859-1) if none can be found.
     */
    public static String parseCharset(Map<String, String> headers) {
        String contentType = headers.get(HTTP.CONTENT_TYPE);
        if (contentType != null) {
            String[] params = contentType.split(";");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }

        return mDefaultCharset;
    }
}
