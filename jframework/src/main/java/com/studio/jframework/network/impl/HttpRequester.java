package com.studio.jframework.network.impl;

import android.content.Context;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.studio.jframework.network.base.AsyncRequestCreator;
import com.studio.jframework.network.base.BaseRequester;
import com.studio.jframework.network.base.NetworkCallback;
import com.studio.jframework.utils.LogUtils;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Jason
 */
public class HttpRequester extends BaseRequester {

    public static final String TAG = HttpRequester.class.getSimpleName();

    public HttpRequester(NetworkCallback callback) {
        super(callback);
    }

    @Override
    public void postRequest(final Context context, String url, RequestParams params, final String method) {
        LogUtils.d(TAG, RequestHelper.parseApi(url, params));
        TextHttpResponseHandler handler = new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                mCallback.onStart(method);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String result) {
                //通过判断method，可以在此处处理事件统计
                RequestHelper.resolveEvent(context, method);

                try {
                    JsonObject response = new JsonParser().parse(result).getAsJsonObject();
                    int status = RequestHelper.parseStatus(response);
                    int code = RequestHelper.parseCode(response);
                    if (status == 1) {
                        LogUtils.i(TAG, result);
                        mCallback.onGetWholeObjectSuccess(method, response);
                        if (response.has(RequestHelper.DATA)) {
                            JsonObject dataObject = RequestHelper.parseData(response);
                            mCallback.onGetDataObjectSuccess(method, dataObject);
                            if (dataObject.has(RequestHelper.LIST)) {
                                mCallback.onGetListObjectSuccess(method, RequestHelper.parseList(response));
                            }
                        }
                    } else {
                        LogUtils.w(TAG, result);
                        String msg = RequestHelper.parseMsg(response);
                        //在一下这个类中处理错误码的问题，例如登录超时跳转到登录界面等操作，
                        //强转context为activity可以处理对话框弹出等问题
                        RequestHelper.resolveResponseCode(context, code);
                        mCallback.onFailed(code, method, msg, response);
                    }
                } catch (JsonSyntaxException e) {
                    LogUtils.e(TAG, PARSE_JSON_EXCEPTION);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                LogUtils.e(TAG, responseString);
                //statusCode为0时表示网路不通
                RequestHelper.resolveHttpStatusCode(context, statusCode);
                mCallback.onFailed(statusCode, method, NETWORK_ERROR, null);
            }

            @Override
            public void onFinish() {
                mCallback.onFinish(method);
            }
        };
        AsyncRequestCreator.getClient().post(context, url, params, handler);
    }

}
