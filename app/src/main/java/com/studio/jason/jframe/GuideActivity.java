package com.studio.jason.jframe;

import android.content.Intent;

import com.studio.jframework.base.BaseGuidelineActivity;

/**
 * Created by Administrator on 2015-07-01.
 */
public class GuideActivity extends BaseGuidelineActivity {


    /**
     * 继承BaseGuidLineActivity以内置布局
     */
    @Override
    public void noViewOnCreate() {
        setFunction(false,false);
        setAutoScrollTime(3000);
        setAutoMode(false);
        startAutoScroll();
    }

    /**
     * 设置引导页面的图片资源
     * @return
     */
    @Override
    public int[] setBackgrounds() {
        return new int[]{R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};
    }

    /**
     * 设置引导界面最后一页点击按钮的跳转
     */
    @Override
    public void skipSetOnClickListener() {
        Intent intent = new Intent(GuideActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
