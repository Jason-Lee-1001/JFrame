package com.jumook.tencentsdk.qq;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.jumook.tencentsdk.qq.callback.OnQQLoginListener;
import com.jumook.tencentsdk.qq.callback.OnQQReAuthListener;
import com.jumook.tencentsdk.qq.callback.OnQQShareListener;
import com.jumook.tencentsdk.qq.callback.OnQQUserInfoListener;
import com.jumook.tencentsdk.qq.model.QQAccessToken;
import com.jumook.tencentsdk.qq.model.QQUserInfo;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * QQ对外使用工具类
 * Created by Administrator on 2015-08-31.
 */
public class QQUtil {

    private static QQUtil instance = null;
    private static Tencent mTencent;
    private static QQAuth mQQAuth;

    private static Activity appActivity;
    private String appId;
    private UserInfo userInfo;//tencentSDK自带

    private OnQQLoginListener onQQLoginListener;
    private OnQQReAuthListener onQQReAuthListener;
    private OnQQUserInfoListener onQQUserInfoListener;
    private OnQQShareListener onQQShareListener;

    /**
     * 单例模式初始化
     *
     * @return instance
     */
    public static synchronized QQUtil getInstance(Activity activity) {
        appActivity = activity;
        if (instance == null) {
            instance = new QQUtil();
        }
        return instance;
    }

    private QQUtil() {

    }

    /**
     * QQ SDK初始化
     *
     * @param appId 企鹅号 appId
     */
    public void initQQ(String appId) {
        this.appId = appId;
        mQQAuth = QQAuth.createInstance(appId, appActivity);
        if (mTencent == null) {
            mTencent = Tencent.createInstance(appId, appActivity);
        }
        setActivityNull();
    }

    /**
     * QQ 授权登录
     *
     * @param scope 应用需要获得哪些接口的权限，由“,”分隔，例如：SCOPE = “get_user_info,add_topic”；如果需要所有权限则使用"all"。
     */
    public void qqLogin(String scope) {
        if (!mTencent.isSessionValid()) {
            mTencent.login(appActivity, scope, new IUiListener() {
                @Override
                public void onComplete(Object obj) {
                    QQAccessToken accessToken = new QQAccessToken();
                    if (obj == null) {
                        setActivityNull();
                        return;
                    }
                    JSONObject json = (JSONObject) obj;
                    accessToken.setRet(json.optInt(QQAccessToken.RET));
                    accessToken.setPayToken(json.optString(QQAccessToken.PAY_TOKEN));
                    accessToken.setPf(json.optString(QQAccessToken.PF));
                    accessToken.setExpiresIn(json.optString(QQAccessToken.EXPIRES_IN));
                    accessToken.setOpenId(json.optString(QQAccessToken.OPENID));
                    accessToken.setPfKey(json.optString(QQAccessToken.PFKEY));
                    accessToken.setMsg(json.optString(QQAccessToken.MSG));
                    accessToken.setAccessToken(json.optString(QQAccessToken.ACCESS_TOKEN));
                    onQQLoginListener.onComplete(accessToken);
                    setActivityNull();
                }

                @Override
                public void onError(UiError uiError) {
                    onQQLoginListener.onError(uiError);
                    setActivityNull();
                }

                @Override
                public void onCancel() {
                    onQQLoginListener.onCancel();
                    setActivityNull();
                }
            });
        }
    }

    /**
     * QQ注销登录
     */
    public void qqLoginOut() {
        mTencent.logout(appActivity);
        setActivityNull();
    }

    /**
     * 返回码为100030时，用户可以增量授权
     *
     * @param scope 应用需要获得哪些API的权限，由“，”分隔。
     *              例如：SCOPE = “get_user_info,add_t”；
     */
    public void qqReAuth(String scope) {
        mTencent.reAuth(appActivity, scope, new IUiListener() {
            @Override
            public void onComplete(Object obj) {
                QQAccessToken accessToken = new QQAccessToken();
                if (obj == null) {
                    setActivityNull();
                    return;
                }
                JSONObject json = (JSONObject) obj;
                accessToken.setRet(json.optInt(QQAccessToken.RET));
                accessToken.setPayToken(json.optString(QQAccessToken.PAY_TOKEN));
                accessToken.setPf(json.optString(QQAccessToken.PF));
                accessToken.setExpiresIn(json.optString(QQAccessToken.EXPIRES_IN));
                accessToken.setOpenId(json.optString(QQAccessToken.OPENID));
                accessToken.setPfKey(json.optString(QQAccessToken.PFKEY));
                accessToken.setMsg(json.optString(QQAccessToken.MSG));
                accessToken.setAccessToken(json.optString(QQAccessToken.ACCESS_TOKEN));
                onQQReAuthListener.onComplete(accessToken);
                setActivityNull();
            }

            @Override
            public void onError(UiError uiError) {
                onQQReAuthListener.onError(uiError);
                setActivityNull();
            }

            @Override
            public void onCancel() {
                onQQReAuthListener.onCancel();
                setActivityNull();
            }
        });
    }

    /**
     * 获取用户QQ信息
     */
    public void getQQUserInfo() {
        userInfo = new UserInfo(appActivity, mTencent.getQQToken());
        userInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object obj) {
                QQUserInfo userInfo = new QQUserInfo();
                if (obj == null) {
                    setActivityNull();
                    return;
                }
                JSONObject json = (JSONObject) obj;
                userInfo.setRet(json.optInt(QQUserInfo.RET));
                userInfo.setMsg(json.optString(QQUserInfo.MSG));
                userInfo.setIsYellowVip(json.optString(QQUserInfo.IS_YELLOW_VIP));
                userInfo.setIsYellowYearVip(json.optString(QQUserInfo.IS_YELLOW_YEAR_VIP));
                userInfo.setYellowVipLevel(json.optString(QQUserInfo.YELLOW_VIP_LEVEL));
                userInfo.setVip(json.optString(QQUserInfo.VIP));
                userInfo.setLevel(json.optString(QQUserInfo.LEVEL));
                userInfo.setNickName(json.optString(QQUserInfo.NICKNAME));
                userInfo.setGender(json.optString(QQUserInfo.GENDER));
                userInfo.setFigureurl(json.optString(QQUserInfo.FIGUREURL));
                userInfo.setFigureurl1(json.optString(QQUserInfo.FIGUREURL_1));
                userInfo.setFigureurl2(json.optString(QQUserInfo.FIGUREURL_2));
                userInfo.setFigureurlQQ1(json.optString(QQUserInfo.FIGUREURL_QQ_1));
                userInfo.setFigureurlQQ2(json.optString(QQUserInfo.FIGUREURL_QQ_2));
                onQQUserInfoListener.onComplete(userInfo);
                setActivityNull();
            }

            @Override
            public void onError(UiError uiError) {
                onQQUserInfoListener.onError(uiError);
                setActivityNull();

            }

            @Override
            public void onCancel() {
                onQQUserInfoListener.onCancel();
                setActivityNull();
            }
        });
    }

    /**
     * 图文分享
     *
     * @param title     分享的标题, 最长30个字符。（必填）
     * @param summary   分享的标题, 最长30个字符。
     * @param targetUrl 这条分享消息被好友点击后的跳转URL。（必填）
     * @param imageUrl  分享图片的URL或者本地路径
     * @param appName   手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
     * @param extInt    分享额外选项，两种类型可选（默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：
     *                  QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN = 1，分享时自动打开分享到QZone的对话框。
     *                  QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE = 2，分享时隐藏分享到QZone按钮.
     *                  默认 = 0，则不填。
     */
    public void shareToQQImageText(String title, String summary, String targetUrl, String imageUrl, String appName, int extInt) {
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(targetUrl)) {
            setActivityNull();
            return;
        }
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, extInt);
        mTencent.shareToQQ(appActivity, params, new IUiListener() {
            @Override
            public void onComplete(Object obj) {
                onQQShareListener.onComplete(obj);
                setActivityNull();
            }

            @Override
            public void onError(UiError uiError) {
                onQQShareListener.onError(uiError);
                setActivityNull();
            }

            @Override
            public void onCancel() {
                onQQShareListener.onCancel();
                setActivityNull();
            }
        });
    }

    /**
     * 分享纯图片
     *
     * @param imageUrl 需要分享的本地图片路径。(只支持本地图片)
     * @param appName  手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替。
     * @param extInt   分享额外选项，两种类型可选（默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：
     *                 QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN = 1，分享时自动打开分享到QZone的对话框。
     *                 QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE = 2，分享时隐藏分享到QZone按钮.
     *                 默认 = 0，则不填。
     */
    public void shareToQQImage(String imageUrl, String appName, int extInt) {
        if (TextUtils.isEmpty(imageUrl)) {
            setActivityNull();
            return;
        }
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, extInt);
        mTencent.shareToQQ(appActivity, params, new IUiListener() {
            @Override
            public void onComplete(Object obj) {
                onQQShareListener.onComplete(obj);
                setActivityNull();
            }

            @Override
            public void onError(UiError uiError) {
                onQQShareListener.onError(uiError);
                setActivityNull();
            }

            @Override
            public void onCancel() {
                onQQShareListener.onCancel();
                setActivityNull();
            }
        });
    }

    /**
     * 分享音乐
     *
     * @param title     分享的标题, 最长30个字符。（必选）
     * @param summary   分享的消息摘要，最长40个字符。
     * @param targetUrl 这条分享消息被好友点击后的跳转URL。（必选）
     * @param imageUrl  分享图片的URL或者本地路径。
     * @param audioUrl  音乐文件的远程链接, 以URL的形式传入, 不支持本地音乐。（必填）
     * @param appName   手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替。
     * @param extInt    分享额外选项，两种类型可选（默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：
     *                  QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN = 1，分享时自动打开分享到QZone的对话框。
     *                  QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE = 2，分享时隐藏分享到QZone按钮.
     *                  默认 = 0，则不填。
     */
    public void shareToQQMusic(String title, String summary, String targetUrl, String imageUrl, String audioUrl, String appName, int extInt) {
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(targetUrl) || TextUtils.isEmpty(audioUrl)) {
            setActivityNull();
            return;
        }
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, audioUrl);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, extInt);
        mTencent.shareToQQ(appActivity, params, new IUiListener() {
            @Override
            public void onComplete(Object obj) {
                onQQShareListener.onComplete(obj);
                setActivityNull();
            }

            @Override
            public void onError(UiError uiError) {
                onQQShareListener.onError(uiError);
                setActivityNull();
            }

            @Override
            public void onCancel() {
                onQQShareListener.onCancel();
                setActivityNull();
            }
        });
    }

    /**
     * 分享到QQ空间
     *
     * @param title     分享的标题，最多200个字符。(必选)
     * @param summary   分享的摘要，最多600字符。
     * @param targetUrl 需要跳转的链接，URL字符串。（必选）
     * @param imageList 分享的图片, 以ArrayList<String>的类型传入，以便支持多张图片（注：图片最多支持9张图片，多余的图片会被丢弃）。
     */
    public void shareToQzone(String title, String summary, String targetUrl, ArrayList<String> imageList) {
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(targetUrl)) {
            setActivityNull();
            return;
        }
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageList);
        mTencent.shareToQzone(appActivity, params, new IUiListener() {
            @Override
            public void onComplete(Object obj) {
                onQQShareListener.onComplete(obj);
                setActivityNull();
            }

            @Override
            public void onError(UiError uiError) {
                onQQShareListener.onError(uiError);
                setActivityNull();
            }

            @Override
            public void onCancel() {
                onQQShareListener.onCancel();
                setActivityNull();
            }
        });
    }


    private void setActivityNull() {
        appActivity = null;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

}
