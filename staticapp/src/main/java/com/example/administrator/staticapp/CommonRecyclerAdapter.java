package com.example.administrator.staticapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Jason
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerHolder> {

    protected Context mContext;
    protected List<T> mData;
    protected int mBackground;
    protected final TypedValue mTypedValue = new TypedValue();

    public CommonRecyclerAdapter(Context context, List<T> data){
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

//    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(setItemLayout(viewType), parent, false);
        itemView.setBackgroundResource(mBackground);
        return new RecyclerHolder(itemView);
    }

    public abstract int setItemLayout(int type);
    public abstract void inflateContent(RecyclerHolder holder, int position, T t);

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        inflateContent(holder, position, (T)getItem(position));
    }
}
