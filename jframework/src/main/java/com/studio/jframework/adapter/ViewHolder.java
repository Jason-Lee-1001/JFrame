package com.studio.jframework.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jason
 * 模板设计模式，抽象ViewHolder，配合CommonAdapter使用
 */
public class ViewHolder {

    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    /**
     * 作为Adapter的getView方法的返回值
     *
     * @return 返回处理过的convertView
     */
    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 获取ViewHolder对象的方法
     *
     * @param context     上下文对象
     * @param convertView 传入Adapter的方法getView中的convertView即可
     * @param parent      传入Adapter的方法getView中的parent即可
     * @param layoutId    传入Item的布局文件Id
     * @param position    传入Adapter的方法getView中的position即可
     * @return 返回ViewHolder对象
     */
    public static ViewHolder getInstance(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    /**
     * 获得Item中的控件，也是将view加入到容器的方法
     *
     * @param viewId 控件的Id
     * @param <T>    View的子类
     * @return 返回找到的控件
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置指定Id的TextView控件的文字
     *
     * @param viewId 控件的Id
     * @param text   要设置的文字
     * @return 返回当前ViewHolder对象
     */
    public ViewHolder setTextByString(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    /**
     * 设置指定Id的TextView控件的文字
     *
     * @param viewId 控件的Id
     * @param resId  要设置的资源id
     * @return 返回当前ViewHolder对象
     */
    public ViewHolder setTextByRes(int viewId, int resId) {
        TextView textView = getView(viewId);
        textView.setText(resId);
        return this;
    }

    /**
     * 设置指定Id的View控件的显隐
     *
     * @param viewId     控件的Id
     * @param visibility 传入View.GONE或者View.VISIBILITY
     * @return 返回当前ViewHolder对象
     */
    public ViewHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }
}
