package com.jumook.tencentsdk.wechat.callback;

/**
 * 校验accessToken回调
 * Created by Administrator on 2015-08-31.
 */
public interface OnCheckOutListener {
     void onCheckOutResult(int code, String msg);

}
