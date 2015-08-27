package com.studio.jframework.network.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.lang.ref.WeakReference;

/**
 * Created by Jason
 */
public class VolleyImageLoader {

    public static ImageLoader mImageLoader;
    private static VolleyImageLoader INSTANCE;

    private VolleyImageLoader(Context context) {
        mImageLoader = VolleyController.getInstance(context).getImageLoader();
    }

    public static VolleyImageLoader getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new VolleyImageLoader(context);
        }
        return INSTANCE;
    }

    public void showImage(final ImageView imageView, String url, final int placeHolder, int maxWidth, int maxHeight) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(placeHolder);
        } else {
            mImageLoader.get(url, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    Bitmap bitmap = response.getBitmap();
                    WeakReference<Bitmap> weakReference = new WeakReference<>(bitmap);
                    if (weakReference.get() != null) {
                        imageView.setImageBitmap(weakReference.get());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    imageView.setImageResource(placeHolder);
                }
            }, maxWidth, maxHeight);
        }
    }
}
