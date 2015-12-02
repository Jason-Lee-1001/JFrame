package com.studio.jframework.widget.listview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
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

    public static final int FOOTER_NONE = 0;
    public static final int FOOTER_LOADING = 1;
    public static final int FOOTER_NOMORE = 2;

    private Context mContext;

    private boolean mHasLoadingFooter = false;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LoadMoreListView mListView;

    private LoadingFooter mFooter;

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
        this(context, attrs, 0);
    }

    public UltimateListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        int dividerHeight = 1;
        int dividerDrawable = 0;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UltimateListView, defStyleAttr, 0);
        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.UltimateListView_dividerHeight) {
                dividerHeight = a.getDimensionPixelSize(attr,
                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()));
            }
            if (attr == R.styleable.UltimateListView_dividerDrawable) {
                dividerDrawable = a.getResourceId(R.styleable.UltimateListView_dividerDrawable, R.drawable.divider_drawable);
            }
        }
        //array should be release
        a.recycle();

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
        if (dividerDrawable != 0) {
            mListView.setDivider(getResources().getDrawable(dividerDrawable));
        }
        mListView.setDividerHeight(dividerHeight);
    }

    public void setRetryButtonText(CharSequence charSequence) {
        mEmptyButton.setText(charSequence);
    }

    public void setEnableRefreshing(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void postRefresh(Runnable runnable) {
        mSwipeRefreshLayout.post(runnable);
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
        setLoadingState(FOOTER_NONE);
    }

    public void setRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
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

    public void setImageClickListener(OnClickListener listener) {
        mEmptyView.setOnClickListener(listener);
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

    public void addHeader(View view, boolean selectable) {
        mListView.addHeaderView(view, null, selectable);
    }

    public void addFooter(View view) {
        mListView.addFooterView(view);
    }

    public void addFooter(View view, boolean selectable) {
        mListView.addFooterView(view, null, selectable);
    }

    public void showEmptyImage(int imageResId, String text) {
        mSwipeRefreshLayout.setVisibility(GONE);
        mEmptyView.setVisibility(VISIBLE);
        mEmptyImage.setVisibility(VISIBLE);
        mEmptyImage.setImageResource(imageResId);
        mEmptyText.setText(text);
        mEmptyButton.setVisibility(GONE);
    }

    public void removeEmptyView() {
        mSwipeRefreshLayout.setVisibility(VISIBLE);
        mEmptyView.setVisibility(INVISIBLE);
    }

    public void addLoadingFooter() {
        if (!mHasLoadingFooter) {
            if (mFooter == null) {
                mFooter = new LoadingFooter(mContext);
            }
            mListView.addFooterView(mFooter.getView());
            mHasLoadingFooter = true;
        }
    }

    public void setLoadingState(int state) {
        if (mHasLoadingFooter) {
            if (state == FOOTER_NONE) {
                if (mListView.getFooterViewsCount() == 0){
                    mHasLoadingFooter = false;
                    return;
                }
                mListView.removeFooterView(mFooter.getView());
                mHasLoadingFooter = false;
                return;
            }
            mFooter.setFooterState(state);
            addLoadingFooter();
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id);
    }

    public interface OnRetryClickListener {
        public void onClick(View view);
    }

}
