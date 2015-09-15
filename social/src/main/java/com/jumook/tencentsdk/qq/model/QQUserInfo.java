package com.jumook.tencentsdk.qq.model;

/**
 * QQ用户信息
 * Created by Administrator on 2015-09-01.
 */
public class QQUserInfo {

    /*
    "is_yellow_year_vip": "0",
    "ret": 0,
    "figureurl_qq_1": "http://q.qlogo.cn/qqapp/222222/8C75BBE3DC6B0E9A64BD31449A3C8CB0/40",
    "figureurl_qq_2": "http://q.qlogo.cn/qqapp/222222/8C75BBE3DC6B0E9A64BD31449A3C8CB0/100",
    "nickname": "小罗",
    "yellow_vip_level": "0",
    "msg": "",
    "figureurl_1": "http://qzapp.qlogo.cn/qzapp/222222/8C75BBE3DC6B0E9A64BD31449A3C8CB0/50",
    "vip": "0",
    "level": "0",
    "figureurl_2": "http://qzapp.qlogo.cn/qzapp/222222/8C75BBE3DC6B0E9A64BD31449A3C8CB0/100",
    "is_yellow_vip": "0",
    "gender": "男",
    "figureurl": "http://qzapp.qlogo.cn/qzapp/222222/8C75BBE3DC6B0E9A64BD31449A3C8CB0/30"
     */

    public static final String IS_YELLOW_VIP = "is_yellow_vip";
    public static final String IS_YELLOW_YEAR_VIP = "is_yellow_year_vip";
    public static final String YELLOW_VIP_LEVEL = "yellow_vip_level";
    public static final String RET = "ret";
    public static final String FIGUREURL_QQ_1 = "figureurl_qq_1";
    public static final String FIGUREURL_QQ_2 = "figureurl_qq_2";
    public static final String FIGUREURL_1 = "figureurl_1";
    public static final String FIGUREURL_2 = "figureurl_2";
    public static final String FIGUREURL = "figureurl";
    public static final String NICKNAME = "nickname";
    public static final String GENDER = "gender";
    public static final String MSG = "msg";
    public static final String VIP = "vip";
    public static final String LEVEL = "level";

    /**
     * 标识用户是否为黄钻用户（0：不是；1：是）
     */
    private String isYellowVip;
    /**
     * 标识是否为年费黄钻用户（0：不是； 1：是）
     */
    private String isYellowYearVip;
    /**
     * 黄钻等级
     */
    private String yellowVipLevel;
    /**
     * 返回码
     */
    private int ret;
    /**
     * 大小为40×40像素的QQ头像URL。
     */
    private String figureurlQQ1;
    /**
     * 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有。
     */
    private String figureurlQQ2;
    /**
     * 大小为30×30像素的QQ空间头像URL。
     */
    private String figureurl;
    /**
     * 大小为50×50像素的QQ空间头像URL。
     */
    private String figureurl1;
    /**
     * 大小为100×100像素的QQ空间头像URL。
     */
    private String figureurl2;
    /**
     * 用户在QQ空间的昵称
     */
    private String nickName;
    /**
     * 性别。 如果获取不到则默认返回"男"
     */
    private String gender;
    /**
     * 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
     */
    private String msg;
    /**
     * 标识用户是否为VIP用户（0：不是；1：是）
     */
    private String vip;
    /**
     * VIP等级
     */
    private String level;

    public QQUserInfo() {

    }

    public String getIsYellowVip() {
        return isYellowVip;
    }

    public void setIsYellowVip(String isYellowVip) {
        this.isYellowVip = isYellowVip;
    }

    public String getIsYellowYearVip() {
        return isYellowYearVip;
    }

    public void setIsYellowYearVip(String isYellowYearVip) {
        this.isYellowYearVip = isYellowYearVip;
    }

    public String getYellowVipLevel() {
        return yellowVipLevel;
    }

    public void setYellowVipLevel(String yellowVipLevel) {
        this.yellowVipLevel = yellowVipLevel;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getFigureurlQQ1() {
        return figureurlQQ1;
    }

    public void setFigureurlQQ1(String figureurlQQ1) {
        this.figureurlQQ1 = figureurlQQ1;
    }

    public String getFigureurlQQ2() {
        return figureurlQQ2;
    }

    public void setFigureurlQQ2(String figureurlQQ2) {
        this.figureurlQQ2 = figureurlQQ2;
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }

    public String getFigureurl1() {
        return figureurl1;
    }

    public void setFigureurl1(String figureurl1) {
        this.figureurl1 = figureurl1;
    }

    public String getFigureurl2() {
        return figureurl2;
    }

    public void setFigureurl2(String figureurl2) {
        this.figureurl2 = figureurl2;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
