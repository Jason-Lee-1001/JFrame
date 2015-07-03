package com.studio.jframework.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.studio.jframework.R;
import com.studio.jframework.adapter.GuideViewPagerAdapter;
import com.studio.jframework.utils.LogUtils;
import com.studio.jframework.view.AutoScrollViewPager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-07-01.
 */
public abstract class BaseGuidelineActivity extends BaseAppCompatActivity {


    private static final String TAG = "BaseGuidelineActivity";
    private static final int GETPOSITION = 1;
    private static final int GETCONTACTS = 2;

    private AutoScrollViewPager guideViewPager;
    private Button guideBtn;
    private LinearLayout guidePoint;//游标生成的layout

    private GuideViewPagerAdapter guideAdapter;//自定义ViewPager的适配器
    private int[] pics;//引导界面图片资源数组
    private ArrayList<View> views;//存放图片的View的链表
    private ArrayList<ImageView> pointViews;// 游标点集合

    private boolean isGetPosition;//是否定位
    private boolean isGetContacts;//是否获取通讯录

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baseguidline_layout);
        getSwipeBackLayout().setEnableGesture(false);
        findViews();
        initialization();
        bindEvent();
        noViewOnCreate();
    }


    @Override
    final public void findViews() {
        guideViewPager = (AutoScrollViewPager)findViewById(R.id.guide_viewpager);
        guideBtn = (Button) findViewById(R.id.guide_btn);
        guidePoint = (LinearLayout) findViewById(R.id.guide_point);
    }

    @Override
    final public void initialization() {
        this.isGetPosition = false;
        this.isGetContacts = false;
        pics = setBackgrounds();
        views = new ArrayList<View>();
        //定义一个布局并设置参数
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        //初始化引导页面的图片
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            views.add(iv);
        }
        //初始化游标
        initPoint();
        //设置适配器
        guideAdapter = new GuideViewPagerAdapter(views);
        guideViewPager.setAdapter(guideAdapter);
        guideViewPager.setCurrentItem(0);
        guideViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        guideBtn.setVisibility(View.GONE);
    }

    @Override
    final public void bindEvent() {
        //最后一个引导页面的按钮的点击监听
        guideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipSetOnClickListener();
            }
        });
        //设置viewPager监听
        guideViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                drawPoint(i);//更改游标的位置
                guideViewPager.setCurrentItem(i);
                if (i == views.size() - 1) {
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


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETPOSITION:

                    LogUtils.d(TAG, "启动了定位功能");
                    break;
                case GETCONTACTS:
                    LogUtils.d(TAG, "启动了获取通讯录功能");
                    break;
            }
        }
    };

    /**
     * 初始化游标
     */
    public void initPoint() {

        pointViews = new ArrayList<ImageView>();
        guidePoint.removeAllViews();
        ImageView imageView;
        for (int i = 0; i < views.size(); i++) {
            imageView = new ImageView(this);
            imageView.setBackgroundResource(R.drawable.point_false);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            layoutParams.width = 30;
            layoutParams.height = 30;
            guidePoint.addView(imageView, layoutParams);
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.point_ture);
            }
            pointViews.add(imageView);
        }
    }

    /**
     * 绘制游标背景
     */
    public void drawPoint(int index) {
        for (int i = 0; i < pointViews.size(); i++) {
            if (index == i) {
                pointViews.get(i).setBackgroundResource(R.drawable.point_ture);
            } else {
                pointViews.get(i).setBackgroundResource(R.drawable.point_false);
            }
        }
    }



    /**
     * 设置引导页面是否启动相应功能
     *
     * @param isGetPosition 是否启动定位
     * @param isGetContacts 是否获取通讯录
     */
    public void setFunction( boolean isGetPosition, boolean isGetContacts) {
        this.isGetPosition = isGetPosition;
        this.isGetContacts = isGetContacts;
        if (this.isGetPosition) {
            handler.sendEmptyMessage(GETPOSITION);
        }
        if (this.isGetContacts) {
            handler.sendEmptyMessage(GETCONTACTS);
        }
    }

    /**
     * 启动自动滚动，默认时间间隔为1500毫秒
     * 可以通过setAutoScrollTime设置滚动的时间间隔
     */
    public void startAutoScroll(){
        guideViewPager.startAutoScroll();
    }

    /**
     * 启动自动滚动
     * @param delayTimeInMills 滚动的时间间隔
     */
    public void startAutoScroll(int delayTimeInMills){
        guideViewPager.startAutoScroll(delayTimeInMills);
    }

    /**
     * 设置自动滚动的时间间隔
     * @param delayTimeInMills
     */
    public void setAutoScrollTime(int delayTimeInMills){
        guideViewPager.setInterval(delayTimeInMills);
    }

    /**
     * 获取自动滚动的时间间隔
     * @return long
     */
    public long getAutoScrollTime(){
        return guideViewPager.getInterval();
    }

    /**
     * 停止自动滚动
     */
    public void stopAutoScroll(){
        guideViewPager.stopAutoScroll();
    }

    /**
     * 设置是否无限循环自动滚动模式
     * @param isCycle
     *          true : 无限循环  false :　不无限循环
     */
    public void setAutoMode(boolean isCycle){
        guideViewPager.setCycle(isCycle);
    }

    /**
     * 获取当前自动滚动的模式
     * @return
     */
    public boolean getAutoMode(){
        return guideViewPager.isCycle();
    }

    /**
     * 提供给用户可操作的接口
     */
    public abstract void noViewOnCreate();

    /**
     * 传入引导界面的图片
     *
     * @return
     */
    public abstract int[] setBackgrounds();

    /**
     * 设置点击跳转
     */
    public abstract void skipSetOnClickListener();


}
