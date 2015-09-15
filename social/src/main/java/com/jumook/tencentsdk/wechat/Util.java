package com.jumook.tencentsdk.wechat;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * 微信专用工具
 * Created by Administrator on 2015-08-31.
 */
public class Util {


    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
