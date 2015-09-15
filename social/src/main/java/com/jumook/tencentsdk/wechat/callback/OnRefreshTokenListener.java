package com.jumook.tencentsdk.wechat.callback;


import com.jumook.tencentsdk.wechat.model.WeChatAccessToken;

/**
 * 刷新AccessToken回调
 * Created by Administrator on 2015-08-31.
 */
public interface OnRefreshTokenListener {
     void onRefreshTokenSuccess(WeChatAccessToken weChatAccessToken);

     void onRefreshTokenError(int code, String msg);
}
