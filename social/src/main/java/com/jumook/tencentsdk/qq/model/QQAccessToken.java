package com.jumook.tencentsdk.qq.model;

/**
 * QQ accessToken
 * Created by Administrator on 2015-08-31.
 */
public class QQAccessToken {

    /*
    "ret":0,
    "pay_token":"xxxxxxxxxxxxxxxx",
    "pf":"openmobile_android",
    "expires_in":"7776000",
    "openid":"xxxxxxxxxxxxxxxxxxx",
    "pfkey":"xxxxxxxxxxxxxxxxxxx",
    "msg":"sucess",
    "access_token":"xxxxxxxxxxxxxxxxxxxxx"
     */

    public static final String RET = "ret";
    public static final String PAY_TOKEN = "pay_token";
    public static final String PF = "pf";
    public static final String EXPIRES_IN = "expires_in";
    public static final String OPENID = "openid";
    public static final String PFKEY = "pfkey";
    public static final String MSG = "msg";
    public static final String ACCESS_TOKEN = "access_token";

    private int ret;
    private String payToken;
    private String pf;
    private String expiresIn;
    private String openId;
    private String pfKey;
    private String msg;
    private String accessToken;

    public QQAccessToken() {

    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getPayToken() {
        return payToken;
    }

    public void setPayToken(String payToken) {
        this.payToken = payToken;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPfKey() {
        return pfKey;
    }

    public void setPfKey(String pfKey) {
        this.pfKey = pfKey;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
