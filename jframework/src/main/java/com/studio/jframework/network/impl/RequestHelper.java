package com.studio.jframework.network.impl;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.loopj.android.http.RequestParams;

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
     * @return 完整的Api路径
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
     * @param object 服务器返回的完整JsonObject
     * @return 解析的状态码
     */
    public static synchronized int parseStatus(JsonObject object) {
        return object.get(STATUS).getAsInt();
    }

    /**
     * 获取错误码code
     *
     * @param object 服务器返回的完整JsonObject
     * @return 解析的错误码
     */
    public static synchronized int parseCode(JsonObject object) {
        return object.get(CODE).getAsInt();
    }

    /**
     * 获取data JsonObject
     *
     * @param object 服务器返回的完整JsonObject
     * @return data代表的JSONObject
     */
    public static synchronized JsonObject parseData(JsonObject object) {
        return object.get(DATA).getAsJsonObject();
    }

    /**
     * 获取list JsonArray
     *
     * @param object 服务器返回的完整JsonObject
     * @return list所代表的JsonArray
     */
    public static synchronized JsonArray parseList(JsonObject object) {
        return parseData(object).get(LIST).getAsJsonArray();
    }

    /**
     * 获取msg字符串
     *
     * @param object 服务器返回的完整JsonObject
     * @return msg字符串
     */
    public static synchronized String parseMsg(JsonObject object) {
        return parseData(object).get(MSG).getAsString();
    }

}
