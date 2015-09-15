package com.jumook.tencentsdk.qq.callback;

import com.jumook.tencentsdk.qq.model.QQAccessToken;
import com.tencent.tauth.UiError;

/**
 * 增量授权
 * Created by Administrator on 2015-09-01.
 */
public interface OnQQReAuthListener {
    void onComplete(QQAccessToken accessToken);

    void onError(UiError uiError);

    void onCancel();
}
