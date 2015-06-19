package com.studio.jframework.network.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.studio.jframework.utils.FileUtils;
import com.studio.jframework.utils.MD5Utils;

/**
 * Created by Jason<p/>
 * Usage:<p/>
 * VolleyRequest request = new VolleyRequest(...);<p/>
 * VolleyController.getInstance(activity).addToQueue(request);<p/>
 */
public class VolleyController {

    private static VolleyController INSTANCE;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    /**
     * Log or global request TAG
     */
    private static final String TAG = "VolleyPattern";

    /**
     * Gain the instance of VolleyHub class
     *
     * @param context Context
     * @return the instance of this class
     */
    public synchronized static VolleyController getInstance(Context context, boolean isEncryptKey) {
        if (INSTANCE == null) {
            INSTANCE = new VolleyController(context, isEncryptKey);
        }
        return INSTANCE;
    }

    /**
     * Add request instance to the queue
     *
     * @param request The request instance you want to add to the queue
     */
    public void addToQueue(Request<?> request) {
        addToQueue(request, null);
    }

    /**
     * Add request instance to the queue and specify a tag
     *
     * @param request The request instance you want to add to the queue
     * @param tag     The tag you wanna specify for the request
     */
    public void addToQueue(Request<?> request, String tag) {
        if (tag != null) {
            request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        }
        mRequestQueue.add(request);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag The tag you labeled the request when you add to the queue
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**
     * Get the request queue that in volley
     *
     * @return the request queue in volley
     */
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    /**
     * Get the image loader that in volley
     *
     * @return the image loader in volley
     */
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    /**
     * The constructor will also initialize the ImageCache for ImageLoader
     *
     * @param context Context
     */
    private VolleyController(Context context, final boolean isEncryptKey) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private FileUtils fileUtils = new FileUtils();
                    private LruCache<String, Bitmap> memoryCache = new LruCache<String, Bitmap>(10 * 1024 * 1024) {
                        protected int sizeOf(String key, Bitmap value) {
                            return value.getRowBytes() * value.getHeight();
                        }
                    };

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        String key = MD5Utils.get32bitsMD5(url, MD5Utils.ENCRYPT_METHOD_1);
                        memoryCache.put((isEncryptKey && key != null) ? key : url, bitmap);
                        fileUtils.saveBitmap(key, bitmap);
                    }

                    @Override
                    public Bitmap getBitmap(String url) {
                        String key = MD5Utils.get32bitsMD5(url, MD5Utils.ENCRYPT_METHOD_1);
                        Bitmap bitmap = memoryCache.get((isEncryptKey && key != null) ? key : url);
                        if (bitmap == null) {
                            bitmap = fileUtils.readBitmap(key);
                        }
                        return bitmap;
                    }
                });
    }
}
