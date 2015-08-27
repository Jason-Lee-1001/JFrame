package com.studio.jframework.base.impl;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.studio.jframework.R;
import com.studio.jframework.adapter.pager.SimpleViewPagerAdapter;
import com.studio.jframework.base.BaseAppCompatActivity;
import com.studio.jframework.widget.pager.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zbb
 */
public abstract class BaseGuidelineActivity extends BaseAppCompatActivity {
    private static final String TAG = "BaseGuidelineActivity";

    private AutoScrollViewPager guideViewPager;
    private Button guideBtn;
    private LinearLayout guideIndicator;//游标生成的layout

    private SimpleViewPagerAdapter guideAdapter;//自定义ViewPager的适配器
    private List<Integer> pics;//引导界面图片资源数组
    private ArrayList<ImageView> indicatorGroup;// 游标点集合


    @Override
    protected boolean onRestoreState(Bundle paramSavedState) {
        return false;
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.base_guideline_layout);
    }

    @Override
    protected void findViews() {
        guideViewPager = (AutoScrollViewPager) findViewById(R.id.guide_viewpager);
        guideBtn = (Button) findViewById(R.id.guide_btn);
        guideIndicator = (LinearLayout) findViewById(R.id.guide_point);
    }

    @Override
    protected void initialization() {
        pics = setBackgrounds();
        initIndicator();
        guideAdapter = new SimpleViewPagerAdapter(this, pics) {
            @Override
            public List<View> inflateContent(List data) {
                List<View> views = new ArrayList<>();
                LayoutInflater inflater = LayoutInflater.from(BaseGuidelineActivity.this);
//                View view = inflater.inflate(R.layout.);
                return views;
            }
        };
        guideViewPager.setAdapter(guideAdapter);
        guideViewPager.setCurrentItem(0);
        guideViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        guideBtn.setVisibility(View.GONE);
    }

    @Override
    protected void bindEvent() {
        guideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                drawIndicator(i);
                guideViewPager.setCurrentItem(i);
                if (i == pics.size() - 1) {
                    guideBtn.setVisibility(View.VISIBLE);
                } else {
                    guideBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    public Button getGuideButton() {
        return guideBtn;
    }

    /**
     * 初始化页面指示器
     */
    public void initIndicator() {

        indicatorGroup = new ArrayList<>();
        guideIndicator.removeAllViews();
        ImageView imageView;
        for (int i = 0; i < pics.size(); i++) {
            imageView = new ImageView(this);
            imageView.setBackgroundResource(R.drawable.point_unchecked);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            layoutParams.width = 30;
            layoutParams.height = 30;
            guideIndicator.addView(imageView, layoutParams);
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.point_checked);
            }
            indicatorGroup.add(imageView);
        }
    }

    /**
     * 绘制游标背景
     */
    public void drawIndicator(int index) {
        for (int i = 0; i < indicatorGroup.size(); i++) {
            if (index == i) {
                indicatorGroup.get(i).setBackgroundResource(R.drawable.point_checked);
            } else {
                indicatorGroup.get(i).setBackgroundResource(R.drawable.point_unchecked);
            }
        }
    }

    /**
     * 启动自动滚动，默认时间间隔为1500毫秒
     * 可以通过setAutoScrollTime设置滚动的时间间隔
     */
    protected void startAutoScroll() {
        guideViewPager.startAutoScroll();
    }

    /**
     * 启动自动滚动
     *
     * @param delayTimeInMills 滚动的时间间隔
     */
    protected void startAutoScroll(int delayTimeInMills) {
        guideViewPager.startAutoScroll(delayTimeInMills);
    }

    /**
     * 设置自动滚动的时间间隔
     *
     * @param delayTimeInMills 要设置的毫秒时间
     */
    protected void setAutoScrollTime(int delayTimeInMills) {
        guideViewPager.setInterval(delayTimeInMills);
    }

    /**
     * 获取自动滚动的时间间隔
     *
     * @return long 返回间隔时间
     */
    protected long getAutoScrollTime() {
        return guideViewPager.getInterval();
    }

    /**
     * 停止自动滚动
     */
    protected void stopAutoScroll() {
        guideViewPager.stopAutoScroll();
    }

    /**
     * 设置是否无限循环自动滚动模式
     *
     * @param isCycle true : 无限循环  false :　不无限循环
     */
    protected void setAutoMode(boolean isCycle) {
        guideViewPager.setCycle(isCycle);
    }

    /**
     * 获取当前自动滚动的模式
     *
     * @return 返回是否开启自动滚动模式
     */
    protected boolean getAutoMode() {
        return guideViewPager.isCycle();
    }

    /**
     * 传入引导界面的图片
     *
     * @return 返回给父类的成员List
     */
    protected abstract List<Integer> setBackgrounds();

}