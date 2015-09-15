package com.jumook.tencentsdk.wechat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jumook.tencentsdk.wechat.callback.OnAccessTokenListener;
import com.jumook.tencentsdk.wechat.callback.OnCheckOutListener;
import com.jumook.tencentsdk.wechat.callback.OnRefreshTokenListener;
import com.jumook.tencentsdk.wechat.callback.OnUserInfoListener;
import com.jumook.tencentsdk.wechat.model.WeChatAccessToken;
import com.jumook.tencentsdk.wechat.model.WeChatUserInfo;
import com.studio.jframework.network.volley.VolleyController;
import com.studio.jframework.network.volley.VolleyStringRequest;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXEmojiObject;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * 微信对外使用工具
 * Created by Administrator on 2015-08-28.
 */
public class WeChatUtil {

    public static final int LOGIN = 1;
    public static final int SHARE = 2;

    private static final String ERRCODE = "errcode";
    private static final String ERRMSG = "errmsg";
    private static WeChatUtil instance = null;
    private static Context mContext;

    private IWXAPI weChatApi;
    private int state;
    private String appId;
    private String secret;

    private OnAccessTokenListener onAccessTokenListener;
    private OnRefreshTokenListener onRefreshTokenListener;
    private OnCheckOutListener onCheckOutListener;
    private OnUserInfoListener onUserInfoListener;

    public static synchronized WeChatUtil getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new WeChatUtil();
        }
        return instance;
    }

    private WeChatUtil() {

    }

    /**
     * 微信SDK初始化
     *
     * @param appId 微信appId
     */
    public void initWeChat(String appId, String secret) {
        this.appId = appId;
        this.secret = secret;
        weChatApi = WXAPIFactory.createWXAPI(mContext, appId, true);
        //将应用的appID注册到微信
        weChatApi.registerApp(appId);
        setContextNull();
    }

    /**
     * 微信登录:获取Code码
     *
     * @param stateStr 描述(随意的字符串,作为返回的唯一标识)
     */
    public void weChatLogin(String stateStr) {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = stateStr;
        state = LOGIN;
        weChatApi.sendReq(req);
    }

    /**
     * 获取Code回调
     */
    class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getAccessToken(intent.getStringExtra("code"));
        }
    }

    /**
     * 获取access_token
     *
     * @param code Code值
     */
    private void getAccessToken(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", appId);
        params.put("secret", secret);
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        VolleyStringRequest post = new VolleyStringRequest(Request.Method.POST, WeChatUrl.GET_APPTOKEN, params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                    WeChatAccessToken weChatAccessToken = new WeChatAccessToken();
                    weChatAccessToken.setAccessToken(json.getString(WeChatAccessToken.ACCESS_TOKEN));
                    weChatAccessToken.setExpriesIn(json.getInt(WeChatAccessToken.EXPIRES_IN));
                    weChatAccessToken.setRefreshToken(json.getString(WeChatAccessToken.REFRESH_TOKEN));
                    weChatAccessToken.setOpenId(json.getString(WeChatAccessToken.OPENID));
                    weChatAccessToken.setScope(json.getString(WeChatAccessToken.SCOPE));
                    onAccessTokenListener.onAccessTokenSuccess(weChatAccessToken);
                    setContextNull();
                } catch (JSONException e) {
                    int code = json.optInt(ERRCODE);
                    String msg = json.optString(ERRMSG);
                    onAccessTokenListener.onAccessTokenError(code, msg);
                    setContextNull();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onAccessTokenListener.onAccessTokenError(-1, "网络请求失败");
                setContextNull();
            }
        });
        VolleyController.getInstance(mContext).addToQueue(post);
    }

    /**
     * 重新获取access_token值
     *
     * @param refresh_token 用户刷新access_token
     */
    public void refreshToken(String refresh_token) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", appId);
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refresh_token);
        VolleyStringRequest post = new VolleyStringRequest(Request.Method.POST, WeChatUrl.REFRESH_TOKEN, params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                    WeChatAccessToken weChatAccessToken = new WeChatAccessToken();
                    weChatAccessToken.setAccessToken(json.getString(WeChatAccessToken.ACCESS_TOKEN));
                    weChatAccessToken.setExpriesIn(json.getInt(WeChatAccessToken.EXPIRES_IN));
                    weChatAccessToken.setRefreshToken(json.getString(WeChatAccessToken.REFRESH_TOKEN));
                    weChatAccessToken.setOpenId(json.getString(WeChatAccessToken.OPENID));
                    weChatAccessToken.setScope(json.getString(WeChatAccessToken.SCOPE));
                    onRefreshTokenListener.onRefreshTokenSuccess(weChatAccessToken);
                    setContextNull();
                } catch (JSONException e) {
                    int code = json.optInt(ERRCODE);
                    String msg = json.optString(ERRMSG);
                    onRefreshTokenListener.onRefreshTokenError(code, msg);
                    setContextNull();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onRefreshTokenListener.onRefreshTokenError(-1, "请检查网络设备状况");
                setContextNull();
            }
        });
        VolleyController.getInstance(mContext).addToQueue(post);
    }

    /**
     * 校验accessToken是否可用
     *
     * @param accessToken accessToken值
     * @param openId      openId值
     */
    public void checkOutAccessToken(String accessToken, String openId) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("openid", "openId");
        VolleyStringRequest post = new VolleyStringRequest(Request.Method.POST, WeChatUrl.AUTH, params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                    int code = json.optInt(ERRCODE);
                    String msg = json.optString(ERRMSG);
                    onCheckOutListener.onCheckOutResult(code, msg);
                    setContextNull();
                } catch (JSONException e) {
                    e.printStackTrace();
                    setContextNull();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onCheckOutListener.onCheckOutResult(-1, "请检查网络设备状况");
                setContextNull();
            }
        });
        VolleyController.getInstance(mContext).addToQueue(post);

    }

    /**
     * 获取用户信息
     *
     * @param accessToken accessToken值
     * @param openId      openId值
     */
    public void getUserInfo(String accessToken, String openId) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("openid", "openId");
        VolleyStringRequest post = new VolleyStringRequest(Request.Method.POST, WeChatUrl.GET_USERINFO, params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                    WeChatUserInfo userInfo = new WeChatUserInfo();
                    userInfo.setOpenId(json.getString(WeChatUserInfo.OPENID));
                    userInfo.setNickName(json.getString(WeChatUserInfo.NICKNAME));
                    userInfo.setSex(json.getInt(WeChatUserInfo.SEX));
                    userInfo.setProvince(json.getString(WeChatUserInfo.PROVINCE));
                    userInfo.setCity(json.getString(WeChatUserInfo.CITY));
                    userInfo.setCountry(json.getString(WeChatUserInfo.COUNTRY));
                    userInfo.setHeadimgurl(json.getString(WeChatUserInfo.HEADIMGURL));
                    userInfo.setUnionid(json.getString(WeChatUserInfo.UNIONID));
                    onUserInfoListener.onUserInfoSuccess(userInfo);
                    setContextNull();
                } catch (JSONException e) {
                    int code = json.optInt(ERRCODE);
                    String msg = json.optString(ERRMSG);
                    onUserInfoListener.onUserInfoError(code, msg);
                    setContextNull();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onUserInfoListener.onUserInfoError(-1, "请检查网络设备状况");
                setContextNull();
            }
        });
        VolleyController.getInstance(mContext).addToQueue(post);
    }

    /**
     * 分享文本
     *
     * @param text      文本内容
     * @param sceneType 朋友圈(1):SendMessageToWX.Req.WXSceneTimeline   会话(0): SendMessageToWX.Req.WXSceneSession
     */
    public void shareText(String text, int sceneType) {
        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = text;

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = sceneType;//分享朋友圈or分享好友

        // 调用api接口发送数据到微信
        weChatApi.sendReq(req);
    }

    /**
     * 分享图片(根据Bitmap)
     *
     * @param bmp       Bitmap
     * @param dstWidth  缩略图的宽(建议150)
     * @param dstHeight 缩略图的高(建议150)
     * @param sceneType 分享的目的地  朋友圈(1):SendMessageToWX.Req.WXSceneTimeline   会话(0): SendMessageToWX.Req.WXSceneSession
     */
    public void shareImageBitmap(Bitmap bmp, int dstWidth, int dstHeight, int sceneType) {
        WXImageObject imgObj = new WXImageObject(bmp);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, dstWidth, dstHeight, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = sceneType;
        weChatApi.sendReq(req);
    }

    /**
     * 分享图片(根据路径)
     *
     * @param path      图片路径
     * @param dstWidth  缩略图的宽(建议150)
     * @param dstHeight 缩略图的高(建议150)
     * @param sceneType 分享的目的地  朋友圈(1):SendMessageToWX.Req.WXSceneTimeline   会话(0): SendMessageToWX.Req.WXSceneSession
     */
    public void shareImagePath(String path, int dstWidth, int dstHeight, int sceneType) {
        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(path);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap bmp = BitmapFactory.decodeFile(path);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, dstWidth, dstHeight, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = sceneType;
        weChatApi.sendReq(req);
    }

    /**
     * 分享图片(根据网络地址)
     *
     * @param url       网络地址
     * @param dstWidth  缩略图的宽(建议150)
     * @param dstHeight 缩略图的高(建议150)
     * @param sceneType 分享的目的地  朋友圈(1):SendMessageToWX.Req.WXSceneTimeline   会话(0): SendMessageToWX.Req.WXSceneSession
     */
    public void shareImageUrl(String url, int dstWidth, int dstHeight, int sceneType) {
        try {
            WXImageObject imgObj = new WXImageObject();
            imgObj.imageUrl = url;

            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = imgObj;

            Bitmap bmp = BitmapFactory.decodeStream(new URL(url).openStream());
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, dstWidth, dstHeight, true);
            bmp.recycle();
            msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("img");
            req.message = msg;
            req.scene = sceneType;
            weChatApi.sendReq(req);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分享音乐
     *
     * @param musicUrl    音乐路径
     * @param title       标题
     * @param description 描述
     * @param bmp         标志
     * @param sceneType   分享的目的地  朋友圈(1):SendMessageToWX.Req.WXSceneTimeline   会话(0): SendMessageToWX.Req.WXSceneSession
     */
    public void shareMusicUrl(String musicUrl, String title, String description, Bitmap bmp, int sceneType) {
        WXMusicObject music = new WXMusicObject();
        music.musicUrl = musicUrl;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = title;
        msg.description = description;

        Bitmap thumb = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        msg.thumbData = Util.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("music");
        req.message = msg;
        req.scene = sceneType;
        weChatApi.sendReq(req);

    }

    /**
     * 分享视频
     *
     * @param videoUrl    分享视频URl
     * @param title       标题
     * @param description 描述
     * @param bmp         标志
     * @param sceneType   分享的目的地  朋友圈(1):SendMessageToWX.Req.WXSceneTimeline   会话(0): SendMessageToWX.Req.WXSceneSession
     */
    public void shareVideoUrl(String videoUrl, String title, String description, Bitmap bmp, int sceneType) {
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = videoUrl;

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = title;
        msg.description = description;
        Bitmap thumb = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        msg.thumbData = Util.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene = sceneType;
        weChatApi.sendReq(req);
    }

    /**
     * 分享链接
     *
     * @param url         链接地址
     * @param title       标题
     * @param description 描述
     * @param bmp         标志
     * @param sceneType   分享的目的地  朋友圈(1):SendMessageToWX.Req.WXSceneTimeline   会话(0): SendMessageToWX.Req.WXSceneSession
     */
    public void shareWebage(String url, String title, String description, Bitmap bmp, int sceneType) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        Bitmap thumb = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        msg.thumbData = Util.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = sceneType;
        weChatApi.sendReq(req);
    }

    /**
     * 分享表情
     *
     * @param path        表情的路径
     * @param title       消息标题
     * @param description 消息描述
     * @param sceneType   分享的目的地  朋友圈(1):SendMessageToWX.Req.WXSceneTimeline   会话(0): SendMessageToWX.Req.WXSceneSession
     */
    public void shareEmoji(String path, String title, String description, int sceneType) {
        try {
            FileInputStream fileIn = new FileInputStream(new File(path));
            int length = fileIn.available();
            byte[] buffer = new byte[length];
            fileIn.read(buffer);
            fileIn.close();
            // 初始化一个WXEmojiObject对象
            WXEmojiObject emoji = new WXEmojiObject();
            emoji.emojiData = buffer;

            // 用WXEmojiObject对象初始化一个WXMediaMessage对象
            WXMediaMessage msg = new WXMediaMessage(emoji);
            msg.title = title;
            msg.description = description;
            msg.thumbData = buffer;

            // 构造一个Req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.message = msg;
            req.transaction = buildTransaction("emoji");
            req.scene = sceneType;
            weChatApi.sendReq(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getState() {
        return state;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }


    private void setContextNull() {
        mContext = null;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
