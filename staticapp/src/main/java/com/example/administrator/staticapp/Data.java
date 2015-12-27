package com.example.administrator.staticapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jason on 12/26/15.
 */
public class Data implements Parcelable{

    public int num;

    protected Data(){

    }

    protected Data(Parcel in) {
        num = in.readInt();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(num);
    }
}
