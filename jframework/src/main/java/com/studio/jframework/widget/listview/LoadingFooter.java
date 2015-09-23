package com.studio.jframework.widget.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.studio.jframework.R;

/**
 * Created by Jason
 */
public class LoadingFooter extends View {

    private TextView mProgress;
    private TextView mText;

    public static final int FOOTER_LOADING = 1;
    public static final int FOOTER_NOMORE = 2;

    public LoadingFooter(Context context) {
        this(context, null);
    }

    public LoadingFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_view_loading_footer, null);
        mProgress = (TextView)view.findViewById(R.id.loading_progress);
        mText = (TextView)view.findViewById(R.id.loading_text);
    }

    public void setFooterState(int state){
        switch (state){
            case FOOTER_LOADING:
                mProgress.setBackgroundResource(R.drawable.abc_btn_radio_to_on_mtrl_015);
                mText.setText(getResources().getString(R.string.loading));
                break;

            case FOOTER_NOMORE:
                mProgress.setBackgroundResource(0);
                mText.setText(getResources().getString(R.string.no_more));
                break;
        }
    }
}
