package com.jumook.weibo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.jumook.weibo.callback.OnWbShareListener;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;

/**
 * 创建微博分享对象
 * Created by Administrator on 2015-09-02.
 */
public class WeiboShare {

    private static final int TEXT = 1;
    private static final int IMAGE = 2;
    private static final int WEBPAGE = 3;
    private static final int MUSIC = 4;
    private static final int VIDEO = 5;
    private static final int VOICE = 6;

    public static final int SHARE_CLIENT = 10;
    public static final int SHARE_ALL_IN_ONE = 20;


    private static WeiboShare instance = null;
    private static Activity appActivity;
    private int mShareType = SHARE_CLIENT;

    private String appKey;
    private String url;
    private String scope;

    private IWeiboShareAPI mWeiboShareAPI;
    private OnWbShareListener onWbShareListener;

    public static synchronized WeiboShare getInstance(Activity activity) {
        appActivity = activity;
        if (instance == null) {
            instance = new WeiboShare();
        }
        return instance;
    }

    private WeiboShare() {
    }

    private void setActivityNull() {
        appActivity = null;
    }

    /**
     * 初始化
     *
     * @param type   分享的方式： SHARE_CLIENT 仅客户端, SHARE_ALL_IN_ONE all in one
     * @param appKey 应用的APP_KEY
     * @param url    应用的回调页
     * @param scope  应用申请的高级权限
     */
    public void initWeiboShare(int type, String appKey, String url, String scope) {
        this.mShareType = type;
        this.appKey = appKey;
        this.url = url;
        this.scope = scope;
        //注册微博分享APIs
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(appActivity, appKey);
        mWeiboShareAPI.registerApp();
    }

    /**
     * 设置微博分享后反面界面数据的处理
     *
     * @param intent
     */
    public void setIntent(Intent intent) {
        mWeiboShareAPI.handleWeiboResponse(intent, new ShareListener());
    }


    /**
     * 分享消息
     *
     * @param type         消息类型
     * @param mAccessToken 微博AccessToken
     * @param textObject   文本消息
     * @param imageObject  图片
     * @param mediaObject  网站连接
     * @param musicObject  音乐
     * @param videoObject  视频
     * @param voiceObject  音频
     */
    private void sendMessage(int type, Oauth2AccessToken mAccessToken, TextObject textObject, ImageObject imageObject, WebpageObject mediaObject, MusicObject musicObject, VideoObject videoObject, VoiceObject voiceObject) {
        if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
            int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
            if (supportApi == -1) {
                onWbShareListener.onShareFail(OnWbShareListener.SDK_ERROR, "微博客户端不支持SDK分享or微博客户端未安装or微博客户端非官方版本");
            } else if (supportApi >= 10351) {
                sendMultiMessage(type, mAccessToken, textObject, imageObject, mediaObject, musicObject, videoObject, voiceObject);
            } else {//getWeiboAppSupportAPI() < 10351 时，只支持分享单条消息
                sendSingleMessage(type, textObject, imageObject, mediaObject, musicObject, videoObject);
            }
        } else {
            onWbShareListener.onShareFail(OnWbShareListener.WEIBOSHAREAPI_NULL, "未注册应用到微博");
        }
    }

    private void sendMultiMessage(int type, Oauth2AccessToken mAccessToken, TextObject textObject, ImageObject imageObject, WebpageObject mediaObject, MusicObject musicObject, VideoObject videoObject, VoiceObject voiceObject) {
        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        switch (type) {
            case TEXT:
                weiboMessage.textObject = textObject;
                break;
            case IMAGE:
                weiboMessage.imageObject = imageObject;
                break;
            case WEBPAGE:
                weiboMessage.mediaObject = mediaObject;
                break;
            case MUSIC:
                weiboMessage.mediaObject = musicObject;
                break;
            case VIDEO:
                weiboMessage.mediaObject = videoObject;
                break;
            case VOICE:
                weiboMessage.mediaObject = voiceObject;
                break;
            default:
                break;
        }
        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        if (mShareType == SHARE_CLIENT) {
            mWeiboShareAPI.sendRequest(appActivity, request);
        } else if (mShareType == SHARE_ALL_IN_ONE) {
            AuthInfo authInfo = new AuthInfo(appActivity, appKey, url, scope);
            String token = "";
            if (mAccessToken != null) {
                token = mAccessToken.getToken();
            }
            mWeiboShareAPI.sendRequest(appActivity, request, authInfo, token, new WeiboAuthListener() {

                @Override
                public void onWeiboException(WeiboException e) {
                    onWbShareListener.onError(e);
                }

                @Override
                public void onComplete(Bundle bundle) {
                    Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                    onWbShareListener.newToken(newToken);
                }

                @Override
                public void onCancel() {
                    onWbShareListener.onCancel();
                }
            });
        }
    }

    private void sendSingleMessage(int type, TextObject textObject, ImageObject imageObject, WebpageObject mediaObject, MusicObject musicObject, VideoObject videoObject) {

        // 1. 初始化微博的分享消息
        WeiboMessage weiboMessage = new WeiboMessage();
        switch (type) {
            case TEXT:
                weiboMessage.mediaObject = textObject;
                break;
            case IMAGE:
                weiboMessage.mediaObject = imageObject;
                break;
            case WEBPAGE:
                weiboMessage.mediaObject = mediaObject;
                break;
            case MUSIC:
                weiboMessage.mediaObject = musicObject;
                break;
            case VIDEO:
                weiboMessage.mediaObject = videoObject;
                break;
            case VOICE:
                onWbShareListener.onShareFail(OnWbShareListener.SHARE_FAIL, "该模式不支持音频分享");
                break;
            default:
                onWbShareListener.onShareFail(OnWbShareListener.SHARE_FAIL, "分享消息类型不存在");
                break;
        }

        // 2. 初始化从第三方到微博的消息请求
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(appActivity, request);
    }


    class ShareListener implements IWeiboHandler.Response {

        @Override
        public void onResponse(BaseResponse baseResponse) {
            switch (baseResponse.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    onWbShareListener.onShareSuccess();
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    onWbShareListener.onShareCancel();
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    onWbShareListener.onShareFail(OnWbShareListener.SHARE_FAIL, baseResponse.errMsg);
                    break;
            }
        }
    }

    /**
     * 创建文本消息对象。
     *
     * @param text 文本消息
     * @return 文本消息对象。
     */
    public void getTextObj(String text, Oauth2AccessToken mAccessToken) {
        TextObject textObject = new TextObject();
        textObject.text = text;
        sendMessage(TEXT, mAccessToken, textObject, null, null, null, null, null);
//        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @param bmp 图片资源
     * @return 图片消息对象。
     */
    public void getImageObj(Bitmap bmp, Oauth2AccessToken mAccessToken) {
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(bmp);
        sendMessage(IMAGE, mAccessToken, null, imageObject, null, null, null, null);
//        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @param title       标题
     * @param description 描述
     * @param bmp         图片资源
     * @param actionUrl   链接
     * @param defaultText 默认文案
     * @return 多媒体（网页）消息对象。
     */
    public void getWebpageObj(String title, String description, Bitmap bmp, String actionUrl, String defaultText, Oauth2AccessToken mAccessToken) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = description;

        // 设置 Bitmap 类型的图片到视频对象里
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        bmp.recycle();
        mediaObject.setThumbImage(thumbBmp);
        mediaObject.actionUrl = actionUrl;
        mediaObject.defaultText = defaultText;
        sendMessage(WEBPAGE, mAccessToken, null, null, mediaObject, null, null, null);
//        return mediaObject;
    }

    /**
     * 创建多媒体（音乐）消息对象。
     *
     * @param title       标题
     * @param description 描述
     * @param bmp         图片资源
     * @param actionUrl   音乐连接
     * @param dataUrl
     * @param dataHdUrl
     * @param defaultText 默认文案
     * @return 多媒体（音乐）消息对象。
     */
    public void getMusicObj(String title, String description, Bitmap bmp, String actionUrl, String dataUrl, String dataHdUrl, String defaultText, Oauth2AccessToken mAccessToken) {
        // 创建媒体消息
        MusicObject musicObject = new MusicObject();
        musicObject.identify = Utility.generateGUID();
        musicObject.title = title;
        musicObject.description = description;

        // 设置 Bitmap 类型的图片到视频对象里
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        bmp.recycle();
        musicObject.setThumbImage(thumbBmp);
        musicObject.actionUrl = actionUrl;
        musicObject.dataUrl = dataUrl;
        musicObject.dataHdUrl = dataHdUrl;
        musicObject.duration = 10;
        musicObject.defaultText = defaultText;
        sendMessage(MUSIC, mAccessToken, null, null, null, musicObject, null, null);
//        return musicObject;
    }

    /**
     * 创建多媒体（视频）消息对象。
     *
     * @param title       标题
     * @param description 描述
     * @param bmp         图片资源
     * @param actionUrl   音乐连接
     * @param dataUrl
     * @param dataHdUrl
     * @param defaultText 默认文案
     * @return 多媒体（视频）消息对象。
     */
    public void getVideoObj(String title, String description, Bitmap bmp, String actionUrl, String dataUrl, String dataHdUrl, String defaultText, Oauth2AccessToken mAccessToken) {
        // 创建媒体消息
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title = title;
        videoObject.description = description;

        // 设置 Bitmap 类型的图片到视频对象里
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        bmp.recycle();
        videoObject.setThumbImage(thumbBmp);
        videoObject.actionUrl = actionUrl;
        videoObject.dataUrl = dataUrl;
        videoObject.dataHdUrl = dataHdUrl;
        videoObject.duration = 10;
        videoObject.defaultText = defaultText;
        sendMessage(VIDEO, mAccessToken, null, null, null, null, videoObject, null);
//        return videoObject;
    }

    /**
     * 创建多媒体（音频）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    public void getVoiceObj(String title, String description, Bitmap bmp, String actionUrl, String dataUrl, String dataHdUrl, String defaultText, Oauth2AccessToken mAccessToken) {
        // 创建媒体消息
        VoiceObject voiceObject = new VoiceObject();
        voiceObject.identify = Utility.generateGUID();
        voiceObject.title = title;
        voiceObject.description = description;

        // 设置 Bitmap 类型的图片到视频对象里
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        bmp.recycle();
        voiceObject.setThumbImage(thumbBmp);
        voiceObject.actionUrl = actionUrl;
        voiceObject.dataUrl = dataUrl;
        voiceObject.dataHdUrl = dataHdUrl;
        voiceObject.duration = 10;
        voiceObject.defaultText = defaultText;
        sendMessage(VOICE, mAccessToken, null, null, null, null, null, voiceObject);
//        return voiceObject;
    }

}
