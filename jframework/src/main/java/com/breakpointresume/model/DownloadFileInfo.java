package com.breakpointresume.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jason
 */
public class DownloadFileInfo implements Parcelable {

    private int id;
    private String url;
    private String fileName;
    private int length;
    private int finished;

    public final static Creator<DownloadFileInfo> CREATOR = new Creator<DownloadFileInfo>() {
        @Override
        public DownloadFileInfo createFromParcel(Parcel source) {
            return new DownloadFileInfo(source);
        }

        @Override
        public DownloadFileInfo[] newArray(int size) {
            return new DownloadFileInfo[size];
        }
    };

    public DownloadFileInfo() {
    }

    public DownloadFileInfo(Parcel parcel) {
        this.id = parcel.readInt();
        this.url = parcel.readString();
        this.fileName = parcel.readString();
        this.length = parcel.readInt();
        this.finished = parcel.readInt();
    }

    public DownloadFileInfo(int id, String url, String fileName, int length, int finished) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.length = length;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "DownloadFileInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", length=" + length +
                ", finished=" + finished +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeString(fileName);
        dest.writeInt(length);
        dest.writeInt(finished);
    }
}
