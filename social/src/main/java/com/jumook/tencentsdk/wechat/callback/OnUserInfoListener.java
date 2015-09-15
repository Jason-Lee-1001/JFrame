package com.jumook.tencentsdk.wechat.callback;

import com.jumook.tencentsdk.wechat.model.WeChatUserInfo;

/**
 * 用户信息回调
 * Created by Administrator on 2015-08-31.
 */
public interface OnUserInfoListener {
     void onUserInfoSuccess(WeChatUserInfo userInfo);

     void onUserInfoError(int code, String msg);
}
