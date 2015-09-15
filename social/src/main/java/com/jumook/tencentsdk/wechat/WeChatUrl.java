package com.jumook.tencentsdk.wechat;

/**
 * 微信接口
 * Created by Administrator on 2015-08-31.
 */
public class WeChatUrl {

    /**
     * 通过Code获取access_token和openId
     */
    public static final String GET_APPTOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 重新获取access_token
     */
    public static final String REFRESH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token?";

    /**
     * 校验授权凭证access_token是否有效
     */
    public static final String AUTH = "https://api.weixin.qq.com/sns/auth?";

    /**
     * 获取用户信息
     */
    public static final String GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo?";


}
