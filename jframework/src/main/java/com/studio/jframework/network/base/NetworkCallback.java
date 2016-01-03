package com.studio.jframework.network.base;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by Jason
 */
public interface NetworkCallback {

    void onStart(String method);

    void onGetWholeObjectSuccess(String method, JsonObject wholeObject);

    void onGetDataObjectSuccess(String method, JsonObject dataObject);

    void onGetListObjectSuccess(String method, JsonArray listArray);

    void onFailed(int code, String method, String msg, JsonObject object);

    void onFinish(String method);

}
