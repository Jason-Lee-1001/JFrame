package com.jumook.tencentsdk.qq.callback;

import com.tencent.tauth.UiError;

/**
 * QQ分享后回调监听
 * Created by Administrator on 2015-09-01.
 */
public interface OnQQShareListener {
    void onComplete(Object object);

    void onError(UiError uiError);

    void onCancel();
}
