package com.studio.jframework.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.jframework.R;

import java.util.List;

/**
 * Created by Jason
 * 使用方法见CommonAdapter
 */
public abstract class CommonRvAdapter<T> extends RecyclerView.Adapter<RvViewHolder> {

    protected Context mContext;
    protected List<T> mData;
    protected int mBackground;
    protected final TypedValue mTypedValue = new TypedValue();

    public CommonRvAdapter(Context context, List<T> data){
        this.mContext = context;
        this.mData = data;
        this.mContext.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        this.mBackground = mTypedValue.resourceId;
    }

    public void setData(List<T> data){
        if (data != null) {
            this.mData = data;
        }
        this.notifyDataSetChanged();
    }

    public List<T> getData() {
        return mData;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public T getItem(int position){
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

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(setItemLayout(viewType), parent, false);
        itemView.setBackgroundResource(mBackground);
        return new RvViewHolder(itemView);
    }

    public abstract int setItemLayout(int type);
    public abstract void inflateContent(RvViewHolder holder, int position, T t);

    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        inflateContent(holder, position, getItem(position));
    }
}
