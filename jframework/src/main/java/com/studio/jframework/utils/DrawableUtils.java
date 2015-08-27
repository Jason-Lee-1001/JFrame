package com.studio.jframework.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

public final class DrawableUtils {

    public static byte[] bitmapToBytes(Bitmap paramBitmap) {
        byte[] arrayOfByte = null;
        if (paramBitmap != null) {
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            paramBitmap.compress(CompressFormat.PNG, 100, localByteArrayOutputStream);
            arrayOfByte = localByteArrayOutputStream.toByteArray();
        }
        return arrayOfByte;
    }

    public static Drawable bitmapToDrawable(Bitmap paramBitmap) {
        return new BitmapDrawable(paramBitmap);
    }

    public static Bitmap byteToBitmap(byte[] paramArrayOfByte) {
        if (paramArrayOfByte.length != 0)
            return BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length);
        return null;
    }

    public static Drawable byteToDrawable(byte[] paramArrayOfByte) {
        ByteArrayInputStream localByteArrayInputStream = null;
        if (paramArrayOfByte != null)
            localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
        return Drawable.createFromStream(localByteArrayInputStream, null);
    }

    public static Bitmap createReflectionImageWithOrigin(Bitmap paramBitmap) {
        int i = paramBitmap.getWidth();
        int j = paramBitmap.getHeight();
        Matrix localMatrix = new Matrix();
        localMatrix.preScale(1.0F, -1.0F);
        Bitmap localBitmap1 = Bitmap.createBitmap(paramBitmap, 0, j / 2, i, j / 2, localMatrix, false);
        Bitmap localBitmap2 = Bitmap.createBitmap(i, j + j / 2, Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap2);
        localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, null);
        Paint localPaint1 = new Paint();
        localCanvas.drawRect(0.0F, j, i, j + 4, localPaint1);
        localCanvas.drawBitmap(localBitmap1, 0.0F, j + 4, null);
        Paint localPaint2 = new Paint();
        localPaint2.setShader(new LinearGradient(0.0F, paramBitmap.getHeight(), 0.0F, 4 + localBitmap2.getHeight(), 1895825407, 16777215, Shader.TileMode.CLAMP));
        localPaint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        localCanvas.drawRect(0.0F, j, i, 4 + localBitmap2.getHeight(), localPaint2);
        return localBitmap2;
    }

    public static Bitmap drawableToBitmap(Drawable paramDrawable) {
        int i = paramDrawable.getIntrinsicWidth();
        int j = paramDrawable.getIntrinsicHeight();
        if (paramDrawable.getOpacity() != -1) ;
        for (Config localConfig = Config.ARGB_8888; ; localConfig = Config.RGB_565) {
            Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
            Canvas localCanvas = new Canvas(localBitmap);
            paramDrawable.setBounds(0, 0, i, j);
            paramDrawable.draw(localCanvas);
            return localBitmap;
        }
    }

    public static byte[] drawableToBytes(Drawable paramDrawable) {
        return bitmapToBytes(((BitmapDrawable) paramDrawable).getBitmap());
    }

    public static boolean findPhotoFromSDCard(String paramString1, String paramString2) {

        //TODO
        return false;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap paramBitmap, float paramFloat) {
        int i = paramBitmap.getWidth();
        int j = paramBitmap.getHeight();
        Bitmap localBitmap = Bitmap.createBitmap(i, j, Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        Rect localRect = new Rect(0, 0, i, j);
        RectF localRectF = new RectF(localRect);
        localPaint.setAntiAlias(true);
        localCanvas.drawARGB(0, 0, 0, 0);
        localPaint.setColor(-12434878);
        localCanvas.drawRoundRect(localRectF, paramFloat, paramFloat, localPaint);
        localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
        return localBitmap;
    }

    public static Bitmap inputStreamToBitmap(InputStream paramInputStream) throws Exception {
        return BitmapFactory.decodeStream(paramInputStream);
    }

    public static Bitmap zoomBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2) {
        int i = paramBitmap.getWidth();
        int j = paramBitmap.getHeight();
        Matrix localMatrix = new Matrix();
        localMatrix.postScale(paramInt1 / i, paramInt2 / j);
        return Bitmap.createBitmap(paramBitmap, 0, 0, i, j, localMatrix, true);
    }

    public static Drawable zoomDrawable(Drawable paramDrawable, int paramInt1, int paramInt2) {
        int i = paramDrawable.getIntrinsicWidth();
        int j = paramDrawable.getIntrinsicHeight();
        Bitmap localBitmap = drawableToBitmap(paramDrawable);
        Matrix localMatrix = new Matrix();
        localMatrix.postScale(paramInt1 / i, paramInt2 / j);
        return new BitmapDrawable(Bitmap.createBitmap(localBitmap, 0, 0, i, j, localMatrix, true));
    }
}