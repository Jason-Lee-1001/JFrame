package com.studio.application.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jason
 */
public class Lyric implements Parcelable{

    @SerializedName("aid")
    private int albumId;
    @SerializedName("artist_id")
    private int artistId;
    @SerializedName("sid")
    private int songId;
    @SerializedName("lrc")
    private String lrcUrl;
    @SerializedName("song")
    private String songName;

    public Lyric() {
    }

    protected Lyric(Parcel in) {
        albumId = in.readInt();
        artistId = in.readInt();
        songId = in.readInt();
        lrcUrl = in.readString();
        songName = in.readString();
    }

    public static final Creator<Lyric> CREATOR = new Creator<Lyric>() {
        @Override
        public Lyric createFromParcel(Parcel in) {
            return new Lyric(in);
        }

        @Override
        public Lyric[] newArray(int size) {
            return new Lyric[size];
        }
    };

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getLrcUrl() {
        return lrcUrl;
    }

    public void setLrcUrl(String lrcUrl) {
        this.lrcUrl = lrcUrl;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    @Override
    public String toString() {
        return "Lyric{" +
                "albumId=" + albumId +
                ", artistId=" + artistId +
                ", songId=" + songId +
                ", lrcUrl='" + lrcUrl + '\'' +
                ", songName='" + songName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(albumId);
        dest.writeInt(artistId);
        dest.writeInt(songId);
        dest.writeString(lrcUrl);
        dest.writeString(songName);
    }
}
