package com.studio.jframework.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.io.InputStream;

/**
 * Usefully tool to do bitmap operation
 * @author Jason
 */
public class BitmapUtils {

    /**
     *
     * @param context
     * @param resID
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    public static Bitmap decodeResourcesDrawable(Context context,int resID, int requestWidth, int requestHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(),resID, options);

        // Calculate inSampleSize
        options.inSampleSize = getBitmapSampleSize(options, requestWidth, requestHeight);
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(),resID, options);
    }

    /**
     *
     * @param inputStream
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    public static Bitmap decodeInputStream(InputStream inputStream, int requestWidth, int requestHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream,new Rect(10,10,10,10), options);

        // Calculate inSampleSize
        options.inSampleSize = getBitmapSampleSize(options, requestWidth, requestHeight);
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inputStream,new Rect(10,10,10,10), options);
    }

    /**
     *
     * @param options The original {@link android.graphics.BitmapFactory.Options} of an image
     * @param requestWidth The destine width you want
     * @param requestHeight The destine height you want
     * @return The sample size base on your destine width and height, the scale will not change
     */
    public static int getBitmapSampleSize(BitmapFactory.Options options, int requestWidth, int requestHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > requestHeight || width > requestWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > requestHeight && (halfWidth / inSampleSize) > requestWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


    //ThumbnailUtils
    /**
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getBitmapThumbnail(Bitmap bitmap){
        return null;
    }

}
