package com.jumook.tencentsdk.wechat.model;

/**
 * 微信用户信息类
 * Created by Administrator on 2015-08-31.
 */
public class WeChatUserInfo {

    /*
    "openid":"OPENID",
    "nickname":"NICKNAME",
    "sex":1,
    "province":"PROVINCE",
    "city":"CITY",
    "country":"COUNTRY",
    "headimgurl": "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
    "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
     */

    public static final String OPENID = "openid";
    public static final String NICKNAME = "nickname";
    public static final String SEX = "sex";
    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String COUNTRY = "country";
    public static final String HEADIMGURL = "headimgurl";
    public static final String UNIONID = "unionid";

    private String openId;
    private String nickName;
    private int sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String unionid;

    public WeChatUserInfo() {
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
