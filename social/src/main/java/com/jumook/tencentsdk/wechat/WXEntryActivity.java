package com.jumook.tencentsdk.wechat;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;


/**
 * 微信activity
 * Created by Administrator on 2015-08-28.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                switch (WeChatUtil.getInstance(this).getState()) {
                    case WeChatUtil.LOGIN:
                        String code = ((SendAuth.Resp) baseResp).code;
                        Intent intent = new Intent();
                        intent.putExtra("code", code);
                        intent.setAction("authlogin");
                        WXEntryActivity.this.sendBroadcast(intent);
                        finish();
                    case WeChatUtil.SHARE:
                        //TODO
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                option(-2, WeChatUtil.getInstance(this).getState());
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                option(-4, WeChatUtil.getInstance(this).getState());
                break;
            default:
                Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void option(int code, int type) {
        switch (type) {
            case WeChatUtil.LOGIN:
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                break;
            case WeChatUtil.SHARE:
                Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
