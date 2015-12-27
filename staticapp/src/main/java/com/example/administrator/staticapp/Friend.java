package com.example.administrator.staticapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jason
 */
public class Friend extends Data implements Parcelable{

    String name;

    protected Friend(Parcel in) {
        super(in);
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
}
