package com.jumook.tencentsdk.qq.callback;

import com.jumook.tencentsdk.qq.model.QQAccessToken;
import com.tencent.tauth.UiError;

/**
 * QQ授权登录回调
 * Created by Administrator on 2015-08-31.
 */
public interface OnQQLoginListener {
    void onComplete(QQAccessToken accessToken);

    void onError(UiError uiError);

    void onCancel();
}
