package com.studio.jframework.network.impl;

import android.content.Context;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jason
 * 类中的部分方法需要根据实际项目接口定义解析方式
 */
public class RequestHelper {

    public static final String STATUS = "status";
    public static final String CODE = "code";
    public static final String MSG = "msg";
    public static final String DATA = "data";
    public static final String LIST = "list";

    /**
     * 打印完整的请求地址
     *
     * @param url    Api地址
     * @param params 请求参数
     * @return
     */
    public static String parseApi(String url, RequestParams params) {
        StringBuilder requestUrl = new StringBuilder(url);
        requestUrl.append("?").append(params.toString());
        return requestUrl.toString();
    }

    /**
     * 处理事务埋点
     *
     * @param context Context
     * @param method  调用的接口名
     */
    public static synchronized void resolveEvent(Context context, String method) {

    }

    /**
     * 处理正常响应的code值
     *
     * @param context Context
     * @param code    服务器返回的错误码 例如4006
     */
    public static synchronized void resolveResponseCode(Context context, int code) {

    }

    /**
     * 处理连接异常的http status code
     *
     * @param context Context
     * @param code    Http status code 例如404，502等
     */
    public static synchronized void resolveHttpStatusCode(Context context, int code) {

    }

    /**
     * 获取status码
     *
     * @param object 服务器返回的完整JSONObject
     * @return 解析的状态码
     * @throws JSONException
     */
    public static synchronized int parseStatus(JSONObject object) throws JSONException {
        return object.getInt(STATUS);
    }

    /**
     * 获取错误码code
     *
     * @param object 服务器返回的完整JSONObject
     * @return 解析的错误码
     * @throws JSONException
     */
    public static synchronized int parseCode(JSONObject object) throws JSONException {
        return object.getInt(CODE);
    }

    /**
     * 获取data JSONObject
     *
     * @param object 服务器返回的完整JSONObject
     * @return data代表的JSONObject
     */
    public static synchronized JSONObject parseData(JSONObject object) {
        return object.optJSONObject(DATA);
    }

    /**
     * 获取list JSONArray
     *
     * @param object 服务器返回的完整JSONObject
     * @return list所代表的JSONArray
     */
    public static synchronized JSONArray parseList(JSONObject object) {
        return parseData(object).optJSONArray(LIST);
    }

    /**
     * 获取msg字符串
     *
     * @param object 服务器返回的完整JSONObject
     * @return msg字符串
     * @throws JSONException
     */
    public static synchronized String parseMsg(JSONObject object) throws JSONException {
        return parseData(object).getString(MSG);
    }

}
