package com.jumook.weibo.callback;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * 用户授权登录回调
 * Created by Administrator on 2015-09-02.
 */
public interface OnWbAuthListener {

    void getToken(Oauth2AccessToken token);

    void getCode(String code);

    void onError(WeiboException e);

    void onCancel();
}
