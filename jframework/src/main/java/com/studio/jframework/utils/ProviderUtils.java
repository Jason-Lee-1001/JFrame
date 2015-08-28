package com.studio.jframework.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

/**
 * Created by Jason
 */
public class ProviderUtils {

    public static Cursor getSongList(Context context) {
        return context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
    }

    public static Cursor getArtistList(Context context) {
        return context.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,  null, null, null, null);
    }

//    public static Cursor getAlbumList()


}
