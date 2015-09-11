package com.example.administrator.staticapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.studio.jframework.utils.LogUtils;
import com.studio.jframework.widget.pager.NoSlidingViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason
 */
public class TabActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int TAB_FIRST = 0;
    public static final int TAB_SECOND = 1;
    public static final int TAB_THIRD = 2;
    public static final int TAB_FORTH = 3;

    private NoSlidingViewPager mViewPager;
    private RadioButton mTabFirst;
    private RadioButton mTabSecond;
    private RadioButton mTabThird;
    private RadioButton mTabForth;

    private ViewPagerAdapter mPagerAdapter;
    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);

        setupToolbar();

        mViewPager = (NoSlidingViewPager) this.findViewById(R.id.content_container);

        //Tab
        mTabFirst = (RadioButton) this.findViewById(R.id.tab_community);
        mTabSecond = (RadioButton) this.findViewById(R.id.tab_library);
        mTabThird = (RadioButton) this.findViewById(R.id.tab_search);
        mTabForth = (RadioButton) this.findViewById(R.id.tab_personal);
        //----------------------------------------------------------------
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new Fragment());
        mFragmentList.add(new Fragment());
        mFragmentList.add(new Fragment());
        mFragmentList.add(new Fragment());

        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
        //使主页ViewPager不销毁、不重建
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mPagerAdapter);
        //----------------------------------------------------------------
        mTabFirst.setOnClickListener(this);
        mTabSecond.setOnClickListener(this);
        mTabThird.setOnClickListener(this);
        mTabForth.setOnClickListener(this);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case TAB_FIRST:
                        mTabFirst.setChecked(true);
                        break;
                    case TAB_SECOND:
                        mTabSecond.setChecked(true);
                        break;
                    case TAB_THIRD:
                        mTabThird.setChecked(true);
                        break;
                    case TAB_FORTH:
                        mTabForth.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_community:
                mViewPager.setCurrentItem(TAB_FIRST, false);
                break;

            case R.id.tab_search:
                mViewPager.setCurrentItem(TAB_SECOND, false);
                break;

            case R.id.tab_library:
                mViewPager.setCurrentItem(TAB_THIRD, false);
                break;

            case R.id.tab_personal:
                mViewPager.setCurrentItem(TAB_FORTH, false);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        LogUtils.setEnable(true);
        LogUtils.d("id", id+"");
        switch (id) {
            case android.R.id.home:
                return true;

            case R.id.action_settings:
                Snackbar.make(mViewPager, "setting", Snackbar.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        List<Fragment> fragments;

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }
    }
}
