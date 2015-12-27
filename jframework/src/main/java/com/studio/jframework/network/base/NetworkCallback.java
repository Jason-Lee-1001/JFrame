package com.studio.jframework.network.base;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jason
 */
public interface NetworkCallback {

    public void onStart(String method);

    public void onGetWholeObjectSuccess(String method, JSONObject wholeObject);

    public void onGetDataObjectSuccess(String method, JSONObject dataObject);

    public void onGetListObjectSuccess(String method, JSONArray listArray);

    public void onFailed(int code, String method, String msg, JSONObject object);

    public void onFinish(String method);

}
