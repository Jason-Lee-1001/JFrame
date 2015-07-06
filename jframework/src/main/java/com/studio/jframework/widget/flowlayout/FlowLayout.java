package com.studio.jframework.widget.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * FlowLayout that allow child views to construct a flow arrangement style
 *
 * @author Jason
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // wrap content
        int width = 0;
        int height = 0;

        // 记录每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 测量子View的宽和高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            // 得到LayoutParams
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            // 获得子view占的宽度
            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            // 获得子view占的高度
            int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            // 换行
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                // 对比得到最大宽度
                width = Math.max(width, lineWidth);
                // 重置lineWidth
                lineWidth = childWidth;
                // 记录行高
                height += lineHeight;
                lineHeight = childHeight;
                // 未换行
            } else {
                // 叠加行宽
                lineWidth += childWidth;
                // 得到当前行最大的高度
                lineHeight = Math.max(lineHeight, childHeight);
            }
            // 最后一个控件
            if (i == childCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );
    }

    /**
     * 存储所有的View，一行一行的形式
     */
    private List<List<View>> mAllViews = new ArrayList<>();

    /**
     * 记录每一行的偏移量和高度
     */
    private List<HashMap<String, Integer>> mLocation = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLocation.clear();
        // 当前ViewGroup的宽度
        int width = getWidth();
        //行控件组总长度
        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineViews = new ArrayList<>();
        int childCount = getChildCount();
        String OFFSET = "offset";
        String ROW = "row";
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            // 如果需要换行
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                // 记录LineHeight
                int restSpace = (width - getPaddingLeft() - getPaddingRight() - lineWidth) / 2;
                HashMap<String, Integer> map = new HashMap<>();
                map.put(OFFSET, restSpace);
                map.put(ROW, lineHeight);
                mLocation.add(map);
                // 记录当前行的Views
                mAllViews.add(lineViews);
                // 重置行宽和行高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                // 重置View的集合
                lineViews = new ArrayList<View>();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(childView);
        }
        // 处理最后一行
        int restSpace1 = (width - getPaddingLeft() - getPaddingRight() - lineWidth) / 2;
        HashMap<String, Integer> map1 = new HashMap<>();
        map1.put(OFFSET, restSpace1);
        map1.put(ROW, lineHeight);
        mLocation.add(map1);
        mAllViews.add(lineViews);
        // 设置子View的位置
        int left = getPaddingLeft();
        int top = getPaddingTop();
        // 行数
        int lineNum = mAllViews.size();
        for (int i = 0; i < lineNum; i++) {
            // 当前行的所有的View
            lineViews = mAllViews.get(i);
            lineHeight = mLocation.get(i).get(ROW);
            for (int j = 0; j < lineViews.size(); j++) {
                View childView = lineViews.get(j);
                // 判断child的状态
                if (childView.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
                // 为子类布局
                int lc = left + lp.leftMargin + mLocation.get(i).get(OFFSET);
                int tc = top + lp.topMargin;
                int rc = lc + childView.getMeasuredWidth();
                int bc = tc + childView.getMeasuredHeight();
                childView.layout(lc, tc, rc, bc);
                left += childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    /**
     * 与当前ViewGroup对应的LayoutParams
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
