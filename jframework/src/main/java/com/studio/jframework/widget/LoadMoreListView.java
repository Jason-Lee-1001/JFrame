package com.studio.jframework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * A list view which can detect when scroll to the bottom.
 */
public class LoadMoreListView extends ListView {

    protected int currentFirstVisibleItem;
    protected int currentVisibleItemCount;
    protected int currentScrollState;
    protected int totalItemCounts;
    protected boolean isLoading = false;
    private OnLoadMoreListener onLoadMoreListener;

    protected OnScrollListener loadMoreOnScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            currentScrollState = scrollState;
            isScrollCompleted();
        }
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            currentFirstVisibleItem = firstVisibleItem;
            currentVisibleItemCount = visibleItemCount;
            totalItemCounts = totalItemCount;
        }
    };

    public LoadMoreListView(Context context) {
        super(context);
        init();
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void isScrollCompleted() {
        if (this.currentVisibleItemCount > 0 && this.currentScrollState == OnScrollListener.SCROLL_STATE_IDLE && (currentFirstVisibleItem + currentVisibleItemCount) >= totalItemCounts - 1) {
            if (!isLoading) {
                isLoading = true;
                onLoadMore();
            }
        }
    }

    private void init() {
        setOnScrollListener(loadMoreOnScrollListener);
    }

    public interface OnLoadMoreListener {
        void loadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void onLoadMore() {
        onLoadMoreListener.loadMore();
        isLoading = false;
    }
}
