package com.studio.jframework.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;

/**
 * Created by Jason
 */
public final class RvViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mView;

    public RvViewHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>();
        this.mView = itemView;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public RvViewHolder setTextByString(int viewId, CharSequence text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public RvViewHolder setTextByRes(int viewId, int resId) {
        TextView textView = getView(viewId);
        textView.setText(resId);
        return this;
    }

    public RvViewHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public RvViewHolder setCheck(int viewId, boolean check) {
        View view = getView(viewId);
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(check);
        }
        return this;
    }

    public RvViewHolder setEnable(int viewId, boolean enable) {
        View view = getView(viewId);
        view.setEnabled(enable);
        return this;
    }

    public RvViewHolder setBackground(int viewId, int resId) {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }
}
