package com.jumook.weibo.callback;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * 微博分享回调
 * Created by Administrator on 2015-09-02.
 */
public interface OnWbShareListener {

    public static final int SDK_ERROR = 401;
    public static final int WEIBOSHAREAPI_NULL = 402;
    public static final int SHARE_FAIL = 403;

    void newToken(Oauth2AccessToken token);

    void onError(WeiboException e);

    void onCancel();

    void onShareSuccess();

    void onShareFail(int errorCode, String msg);

    void onShareCancel();

}
