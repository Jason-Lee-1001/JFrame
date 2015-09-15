package com.jumook.tencentsdk.wechat.model;

/**
 * Created by Administrator on 2015-08-31.
 */
public class WeChatAccessToken {

    /*
    "access_token":"ACCESS_TOKEN",
    "expires_in":7200,
    "refresh_token":"REFRESH_TOKEN",
    "openid":"OPENID",
    "scope":"SCOPE"
     */

    public static final String ACCESS_TOKEN = "access_token";
    public static final String EXPIRES_IN = "expires_in";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String OPENID = "openid";
    public static final String SCOPE = "scope";

    private String accessToken;
    private int expriesIn;
    private String refreshToken;
    private String openId;
    private String scope;

    public WeChatAccessToken() {

    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpriesIn() {
        return expriesIn;
    }

    public void setExpriesIn(int expriesIn) {
        this.expriesIn = expriesIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
