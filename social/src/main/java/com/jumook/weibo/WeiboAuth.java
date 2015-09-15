package com.jumook.weibo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.jumook.weibo.callback.OnWbAuthListener;
import com.jumook.weibo.callback.OnWbByCodeAuthListener;
import com.jumook.weibo.callback.OnWbUserInfoListener;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.User;

/**
 * 微博工具
 * Created by Administrator on 2015-09-01.
 */
public class WeiboAuth {

    private static final String OAUTH2_ACCESS_TOKEN_URL = "https://open.weibo.cn/oauth2/access_token";
    private static WeiboAuth instance = null;
    private static Activity appActivity;

    public SsoHandler mSsoHandler;

    private AuthInfo mAuthInfo;
    private UsersAPI mUsersAPI;

    private OnWbAuthListener onWeiboAuthListener;
    private OnWbByCodeAuthListener onWbByCodeAuthListener;
    private OnWbUserInfoListener onWbUserInfoListener;

    public static synchronized WeiboAuth getInstance(Activity activity) {
        appActivity = activity;
        if (instance == null) {
            instance = new WeiboAuth();
        }
        return instance;
    }

    private WeiboAuth() {
    }

    private void setActivityNull() {
        appActivity = null;
    }

    /**
     * 初始化
     *
     * @param appKey 应用的APP_KEY
     * @param url    应用的回调页
     * @param scope  应用申请的高级权限
     */
    public void initWeiBo(String appKey, String url, String scope) {
        mAuthInfo = new AuthInfo(appActivity, appKey, url, scope);
        mSsoHandler = new SsoHandler(appActivity, mAuthInfo);
    }


    /**
     * 微信SSO授权登录
     * SSO 授权时，需要在 activity的onActivityResult中调用 SsoHandler#authorizeCallBack 后该回调才会被执行。
     */
    public void weiboSSOLogin() {
        mSsoHandler.authorizeClientSso(new AuthListener());
    }

    /**
     * 微博web页面授权登录
     */
    public void weiboWebLogin() {
        mSsoHandler.authorizeWeb(new AuthListener());
    }

    /**
     * 微博SSO  or  web页面 二选一授权登录
     * SSO 授权时，需要在 activity的onActivityResult中调用 SsoHandler#authorizeCallBack 后该回调才会被执行。
     */
    public void weiboAllInOneLogin() {
        mSsoHandler.authorize(new AuthListener());
    }

    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(values); // 从 Bundle 中解析 Token
            if (mAccessToken.isSessionValid()) {
                onWeiboAuthListener.getToken(mAccessToken);
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                if (!TextUtils.isEmpty(code)) {
                    onWeiboAuthListener.getCode(code);
                }
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            onWeiboAuthListener.onError(e);
        }

        @Override
        public void onCancel() {
            onWeiboAuthListener.onCancel();
        }
    }


    /**
     * 异步获取 Token(通过Code值手动获取token)
     *
     * @param appKey      应用appKey
     * @param redirectUrl 应用回调页
     * @param authCode    授权 Code，该 Code 是一次性的，只能被获取一次 Token
     * @param appSecret   应用程序的 APP_SECRET，请务必妥善保管好自己的 APP_SECRET,不要直接暴露在程序中。
     */
    public void fetchTokenAsync(String appKey, String redirectUrl, String authCode, String appSecret) {

        WeiboParameters requestParams = new WeiboParameters(appKey);
        requestParams.put(WBConstants.AUTH_PARAMS_CLIENT_ID, appKey);
        requestParams.put(WBConstants.AUTH_PARAMS_CLIENT_SECRET, appSecret);
        requestParams.put(WBConstants.AUTH_PARAMS_GRANT_TYPE, "authorization_code");
        requestParams.put(WBConstants.AUTH_PARAMS_CODE, authCode);
        requestParams.put(WBConstants.AUTH_PARAMS_REDIRECT_URL, redirectUrl);

        // 异步请求，获取 Token
        new AsyncWeiboRunner(appActivity).requestAsync(OAUTH2_ACCESS_TOKEN_URL, requestParams, "POST", new RequestListener() {
            @Override
            public void onComplete(String response) {
                // 获取 Token 成功
                Oauth2AccessToken token = Oauth2AccessToken.parseAccessToken(response);
                if (token != null && token.isSessionValid()) {
                    onWbByCodeAuthListener.onSuccess(token);
                } else {
                    onWbByCodeAuthListener.onFail("Failed to receive access token");
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                onWbByCodeAuthListener.onError(e);
            }
        });
    }

    /**
     * 获取微博用户信息
     *
     * @param context 上下文本
     * @param appKey  应用的appKey
     * @param token   授权获取的AccessToken
     */
    public void getWeiboUserInfo(Context context, String appKey, Oauth2AccessToken token) {
        mUsersAPI = new UsersAPI(context, appKey, token);
        long uid = Long.parseLong(token.getUid());
        mUsersAPI.show(uid, new RequestListener() {
            @Override
            public void onComplete(String response) {
                if (!TextUtils.isEmpty(response)) {
                    User user = User.parse(response);
                    onWbUserInfoListener.onSuccess(user);
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                onWbUserInfoListener.onError(e);
            }
        });
    }


}
