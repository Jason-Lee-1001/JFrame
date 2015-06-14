package com.studio.jframework.widget.toast;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.studio.jframework.R;

import java.lang.reflect.Field;

/**
 * Created by Jason
 */
public class FullWidthToast {

    private Toast mToast;
    private TextView mTextView;
    private ImageView mImageView;
    private RelativeLayout mRelativeLayout;
    private Context mContext;

    public FullWidthToast(Context context){
        mContext = context;
        mToast = new Toast(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.widget_toast_layout, null);
        mTextView = (TextView) view.findViewById(R.id.text);
        mImageView = (ImageView) view.findViewById(R.id.icon);
        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.toast_container);
        mToast.setView(view);
    }

    public Toast getToast() {
        return this.mToast;
    }

    public FullWidthToast setTextColor(int color){
        this.mTextView.setTextColor(color);
        return this;
    }

    public FullWidthToast setImage(Drawable drawable){
        this.mImageView.setImageDrawable(drawable);
        return this;
    }

    public FullWidthToast setBackgroundColor(int color){
        this.mRelativeLayout.setBackgroundColor(color);
        return this;
    }

    public FullWidthToast setText(String text){
        this.mTextView.setText(text);
        return this;
    }

    public FullWidthToast setTextSize(float size){
        this.mTextView.setTextSize(size);
        return this;
    }

    public FullWidthToast setAnimation(int resId) {
        try {
            Object mTN = null;
            mTN = getField(mToast, "mTN");
            if (mTN != null) {
                Object mParams = getField(mTN, "mParams");
                if (mParams != null && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    params.windowAnimations = resId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 反射字段
     * @param object 要反射的对象
     * @param fieldName 要反射的字段名称
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static Object getField(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }

    public FullWidthToast setDuration(int duration){
        this.mToast.setDuration(duration);
        return this;
    }


    public FullWidthToast setText(int resId){
        this.mTextView.setText(resId);
        return this;
    }

    /**
     * Set the gravity of the FullWidthToast
     *
     * @param gravity One of Gravity.TOP or Gravity.BOTTOM
     * @return The instance of this class
     */
    public FullWidthToast setGravity(int gravity){
        int position = Gravity.FILL_HORIZONTAL | gravity;
        this.mToast.setGravity(position,0,0);
        return this;
    }

    public void show(){
        getToast().show();
    }

    private static Toast setAnimation(Toast toast) {
        try {
            Object mTN = null;
            mTN = getField(toast, "mTN");
            if (mTN != null) {
                Object mParams = getField(mTN, "mParams");
                if (mParams != null && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    params.windowAnimations = R.style.bottom_default_toast_animation;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toast;
    }


    public static Toast makeToast(Context context, String text, int duration){
        Toast toast = new Toast(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toast_view = inflater.inflate(R.layout.widget_toast_layout, null);
        TextView textView = (TextView) toast_view.findViewById(R.id.text);
        ImageView imageView = (ImageView) toast_view.findViewById(R.id.icon);
        textView.setText(text);
        toast.setView(toast_view);
        toast.setDuration(duration);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL,0,0);
        setAnimation(toast);
        return toast;
    }

    public static Toast makeToast(Context context, int resId, int duration){
        Toast toast = new Toast(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toast_view = inflater.inflate(R.layout.widget_toast_layout, null);
        TextView textView = (TextView) toast_view.findViewById(R.id.text);
        textView.setText(resId);
        toast.setView(toast_view);
        toast.setDuration(duration);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL,0,0);
        setAnimation(toast);
        return toast;
    }

}
