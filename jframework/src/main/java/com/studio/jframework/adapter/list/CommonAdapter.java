package com.studio.jframework.adapter.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Jason
 * ListView和GridView的万能适配器，不需单独配置ViewHolder，对于多布局同样适合
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mData;
    protected LayoutInflater mInflater;

    public CommonAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
    }

    /**
     * 更新适配器的内容，已调用notifyDataSetChanged
     *
     * @param data 更新到适配器的内容
     */
    public void setData(List<T> data) {
        if (data != null) {
            this.mData = data;
        }
        this.notifyDataSetChanged();
    }

    /**
     * 获取当前适配器中的数据
     *
     * @return 返回处于适配器中的数据，没有则返回空
     */
    public List<T> getData() {
        return mData;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position % 5 == 1) {
//            return 0;
//        } else {
//            return 1;
//        }
//    }

//    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }

    @Override
    public abstract long getItemId(int position);

    /**
     * 填充Item内容的方法，如设置TextView的text
     *
     * @param holder   返回可供用户调用的holder
     * @param position 当前的位置
     * @param t        元数据
     */
    public abstract void inflateContent(ViewHolder holder, int position, T t);

    /**
     * 设置Item的布局文件id
     *
     * @param type 由getItemViewType方法返回
     * @return 需要根据type返回相应的布局文件id，如果只有一种布局，type为0。如果是多布局，
     * 多种布局必须返回不同的布局，getItemViewType和getViewTypeCount也需要复写，
     * getItemViewType返回类型0,1,2...，getViewTypeCount返回item数量
     */
    public abstract int setItemLayout(int type);

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        int layout = 0;
        for (int i = 0; i < getViewTypeCount(); i++) {
            // 通过item种类数量遍历类型，如果跟当前position类型吻合
            // 则调用setItemLayout，找到跳出循环
            if (getItemViewType(position) == i) {
                layout = setItemLayout(i);
                break;
            }
        }
        ViewHolder holder = ViewHolder.getInstance(mContext, convertView, parent, layout, position);
        inflateContent(holder, position, getItem(position));
        return holder.getConvertView();
    }
}
