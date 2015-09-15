package com.jumook.weibo.callback;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.openapi.models.User;

/**
 * 获取微博用户信息回调
 * Created by Administrator on 2015-09-02.
 */
public interface OnWbUserInfoListener {
    void onSuccess(User user);

    void onError(WeiboException e);
}
