package com.studio.jframework.widget.listview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.studio.jframework.R;

/**
 * Created by Jason
 */
public class UltimateListView extends FrameLayout {

    static enum State {
        NO_NETWORK,
        NO_CONTENT,
        NOTICE
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LoadMoreListView mListView;

    private OnItemClickListener mListener;
    private OnRetryClickListener mClickListener;

    private View mEmptyView;
    private ImageView mEmptyImage;
    private TextView mEmptyText;
    private Button mEmptyButton;

    public UltimateListView(Context context) {
        this(context, null);
    }

    public UltimateListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public UltimateListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.common_listview_layout, this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mListView = (LoadMoreListView) view.findViewById(R.id.list_view);
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.holo_green_dark, R.color.holo_orange_dark, R.color.holo_red_light);

        mEmptyView = view.findViewById(R.id.empty_view);

        mEmptyImage = (ImageView) view.findViewById(R.id.empty_image);
        mEmptyText = (TextView) view.findViewById(R.id.empty_text);
        mEmptyButton = (Button) view.findViewById(R.id.empty_retry);

        mEmptyButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mSwipeRefreshLayout.setVisibility(VISIBLE);
                mEmptyView.setVisibility(GONE);
                if (mClickListener != null) {
                    mClickListener.onClick(view);
                }
            }
        });
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public LoadMoreListView getListView() {
        return mListView;
    }

    public void setAdapter(ListAdapter adapter) {
        mListView.setAdapter(adapter);
    }

    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(listener);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
        final int headerViewCount = mListView.getHeaderViewsCount();
        final int footerViewCount = mListView.getFooterViewsCount();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int size = mListView.getAdapter().getCount();
                if (position < headerViewCount || position >= size + headerViewCount) {
                    return;
                } else {
                    mListener.onItemClick(adapterView, view, position - headerViewCount, l);
                }
            }
        });
    }

    public void setOnLoadMoreListener(LoadMoreListView.OnLoadMoreListener listener) {
        mListView.setOnLoadMoreListener(listener);
    }

    public void setOnRetryClickListener(OnRetryClickListener listener) {
        mClickListener = listener;
    }

    public ListAdapter getAdapter() {
        return mListView.getAdapter();
    }

    public void addHeader(View view) {
        mListView.addHeaderView(view);
    }

    public void addOneOnlyHeader(View view, boolean selectable) {
        if (mListView.getHeaderViewsCount() == 0) {
            mListView.addHeaderView(view, null, selectable);
        }
    }

    public void addFooter(View view) {
        mListView.addFooterView(view);
    }

    public void addOneOnlyFooter(View view, boolean selectable) {
        if (mListView.getFooterViewsCount() == 0) {
            mListView.addFooterView(view, null, selectable);
        }
    }

    public void showEmptyView(State state, String text) {
        mSwipeRefreshLayout.setVisibility(GONE);
        mEmptyView.setVisibility(VISIBLE);
        switch (state) {
            case NO_NETWORK:
                mEmptyImage.setVisibility(View.VISIBLE);
                mEmptyText.setText(text);
                mEmptyButton.setVisibility(View.VISIBLE);
                break;

            case NO_CONTENT:
                mEmptyImage.setVisibility(VISIBLE);
                mEmptyText.setText(text);
                mEmptyButton.setVisibility(VISIBLE);
                break;

            case NOTICE:
                mEmptyImage.setVisibility(INVISIBLE);
                mEmptyText.setText(text);
                mEmptyButton.setVisibility(GONE);
                break;
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id);
    }

    public interface OnRetryClickListener {
        public void onClick(View view);
    }

}
