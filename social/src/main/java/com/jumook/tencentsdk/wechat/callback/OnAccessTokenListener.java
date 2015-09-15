package com.jumook.tencentsdk.wechat.callback;

import com.jumook.tencentsdk.wechat.model.WeChatAccessToken;

/**
 * AccessToken回调
 * Created by Administrator on 2015-08-31.
 */
public interface OnAccessTokenListener {
    void onAccessTokenSuccess(WeChatAccessToken weChatAccessToken);

    void onAccessTokenError(int code, String msg);


}
