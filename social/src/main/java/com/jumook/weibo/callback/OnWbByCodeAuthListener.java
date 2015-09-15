package com.jumook.weibo.callback;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * 通过Code获取Token回调
 * Created by Administrator on 2015-09-02.
 */
public interface OnWbByCodeAuthListener {

    void onSuccess(Oauth2AccessToken token);
    void onFail(String msg);
    void onError(WeiboException e);
}
