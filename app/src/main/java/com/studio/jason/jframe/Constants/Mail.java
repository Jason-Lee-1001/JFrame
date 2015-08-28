package com.studio.jason.jframe.Constants;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason
 */
public class Mail implements Parcelable {

    /*{"status":1,"code":0,"data":{"list":[
        {"notice_id":17,"sender_id":0,"notice_title":"\u4f60\u597d","notice_content":"\u6d4b\u8bd5","notice_time":1438770301},
        {"notice_id":16,"sender_id":0,"notice_title":"\u6cbf\u6c5f","notice_content":"\u6cbf\u6c5f","notice_time":1438770039},
        {"notice_id":15,"sender_id":0,"notice_title":"e","notice_content":"d","notice_time":1438763750}]}}*/

    /*http://120.24.59.179:8693/v1/notice/showSystemNoticeList?app_token=3111421437978653&page=1*/

    public static final String TAG = "Mail";

    public static final String NOTICE_ID = "notice_id";
    public static final String SENDER_ID = "sender_id";
    public static final String NOTICE_TITLE = "notice_title";
    public static final String NOTICE_CONTENT = "notice_content";
    public static final String NOTICE_TIME = "notice_time";

    @SerializedName(NOTICE_ID)
    private int noticeId;
    @SerializedName(SENDER_ID)
    private int senderId;
    @SerializedName(NOTICE_TITLE)
    private String noticeTitle;
    @SerializedName(NOTICE_CONTENT)
    private String noticeContent;
    @SerializedName(NOTICE_TIME)
    private long noticeTime;

    public Mail() {
    }

    protected Mail(Parcel in) {
        noticeId = in.readInt();
        senderId = in.readInt();
        noticeTitle = in.readString();
        noticeContent = in.readString();
        noticeTime = in.readLong();
    }

    public static final Creator<Mail> CREATOR = new Creator<Mail>() {
        @Override
        public Mail createFromParcel(Parcel in) {
            return new Mail(in);
        }

        @Override
        public Mail[] newArray(int size) {
            return new Mail[size];
        }
    };

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public long getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(long noticeTime) {
        this.noticeTime = noticeTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noticeId);
        dest.writeInt(senderId);
        dest.writeString(noticeTitle);
        dest.writeString(noticeContent);
        dest.writeLong(noticeTime);
    }

    public static Mail getEntity(JSONObject object) {
        Mail mail = new Mail();
        mail.setNoticeId(object.optInt(NOTICE_ID));
        mail.setSenderId(object.optInt(SENDER_ID));
        mail.setNoticeTitle(object.optString(NOTICE_TITLE));
        mail.setNoticeContent(object.optString(NOTICE_CONTENT));
        mail.setNoticeTime(object.optLong(NOTICE_TIME));
        return mail;
    }

    public static List<Mail> getList(JSONArray array) {
        List<Mail> mailList = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject object = array.getJSONObject(i);
                mailList.add(getEntity(object));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mailList;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "noticeId=" + noticeId +
                ", senderId=" + senderId +
                ", noticeTitle='" + noticeTitle + '\'' +
                ", noticeContent='" + noticeContent + '\'' +
                ", noticeTime=" + noticeTime +
                '}';
    }
}
