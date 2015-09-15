package com.jumook.tencentsdk.qq.callback;

import com.jumook.tencentsdk.qq.model.QQUserInfo;
import com.tencent.tauth.UiError;

/**
 * QQ用户信息
 * Created by Administrator on 2015-09-01.
 */
public interface OnQQUserInfoListener {
    void onComplete(QQUserInfo userInfo);

    void onError(UiError uiError);

    void onCancel();


}
