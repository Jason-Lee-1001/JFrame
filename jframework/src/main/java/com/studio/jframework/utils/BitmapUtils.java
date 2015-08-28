package com.studio.jframework.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.view.View;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Usefully tool to do bitmap operation
 *
 * @author Jason
 */
public class BitmapUtils {


    public static Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fd, Rect rect, int requestWidth, int requestHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, rect, options);

        // Calculate inSampleSize
        options.inSampleSize = getBitmapSampleSize(options, requestWidth, requestHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, rect, options);
    }

    public static Bitmap decodeSampledBitmapFromPath(String path, int requestWidth, int requestHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = getBitmapSampleSize(options, requestWidth, requestHeight);
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }


    public static Bitmap decodeResourcesDrawable(Context context, int resID, int requestWidth, int requestHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resID, options);

        // Calculate inSampleSize
        options.inSampleSize = getBitmapSampleSize(options, requestWidth, requestHeight);
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(), resID, options);
    }


    public static Bitmap decodeInputStream(InputStream inputStream, int requestWidth, int requestHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, new Rect(10, 10, 10, 10), options);

        // Calculate inSampleSize
        options.inSampleSize = getBitmapSampleSize(options, requestWidth, requestHeight);
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inputStream, new Rect(10, 10, 10, 10), options);
    }

    /**
     * @param options       The original {@link android.graphics.BitmapFactory.Options} of an image
     * @param requestWidth  The destine width you want
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

    public static Bitmap decodeBitmapFromUri(Uri uri, Context context) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap decodeBitmapFromUri(Uri uri, Context context, int requestWidth, int requestHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        String url = UriUtils.getRealPathFromURI(context, uri);
        BitmapFactory.decodeFile(url, options);
//            InputStream inputStream = context.getContentResolver().openInputStream(uri);
        options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        options.inJustDecodeBounds = false;
//            InputStream inputStream = context.getContentResolver().openInputStream(uri);
//            return BitmapFactory.decodeStream(inputStream, null, options);
        return BitmapFactory.decodeFile(url, options);
    }

    /**
     * 根据宽高比例获取最优的比值
     *
     * @param options   操作
     * @param reqWidth  需求的宽
     * @param reqHeight 需求的高
     * @return 图片压缩比例
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * Create thumbnail with the specify bitmap
     *
     * @param bitmap The origin bitmap
     * @param width  The width of the thumbnail
     * @param height The height of the thumbnail
     * @return The thumbnail
     */
    public static Bitmap getBitmapThumbnail(Bitmap bitmap, int width, int height) {
        return ThumbnailUtils.extractThumbnail(bitmap, width, height);
    }

    /**
     * Get the screen shot by the view
     *
     * @param view The view to be capture screen shot
     * @return Bitmap of the view' screen shot
     */
    public static Bitmap getScreenShotByView(View view) {
        if (view == null) {
            return null;
        }
        view.setDrawingCacheEnabled(true);
        return view.getDrawingCache();
    }

    /**
     * Get the screen shot of whole screen
     *
     * @param activity Activity
     * @return Bitmap that captured
     */
    public static Bitmap getScreenShot(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        return decorView.getDrawingCache();
    }

}
