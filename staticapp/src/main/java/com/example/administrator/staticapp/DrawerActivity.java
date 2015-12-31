package com.example.administrator.staticapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.RequestParams;
import com.studio.jframework.base.BaseAppCompatActivity;
import com.studio.jframework.network.base.AsyncRequestCreator;
import com.studio.jframework.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class DrawerActivity extends BaseAppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = DrawerActivity.class.getSimpleName();

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private static final int MENU_GROUP = 3;
    Button button;

    @Override
    protected boolean onRestoreState(Bundle paramSavedState) {
        return false;
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_drawer_main);
        AsyncRequestCreator.initRequestSettings(1, 1000, false, 5000, 5000);
    }

    @Override
    protected void findViews() {
        button = (Button) findViewById(R.id.test);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        setupToolbar();
        setupNavigationView();
    }

    @Override
    protected void initialization() {
        LogUtils.setEnable(true);
    }

    @Override
    protected void bindEvent() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();
                map.put("sign", "ElectAPI");
                map.put("app_token", "1495351451118384");
                map.put("chaffyDishIds", "6,5");
                map.put("user_id", "1");
                RequestParams params = new RequestParams(map);
                mRequester.postRequest(DrawerActivity.this, "http://182.254.155.71:8693/v1/order/placeOrder", params, "placeOrder");
            }
        });
    }

    /**
     * If you want to do more in onCreate method, you may do it here
     */
    @Override
    protected void doMoreInOnCreate() {

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

    private void setupNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
//        menu.clear();
        for (int i = 0; i < 4; i++) {
            menu.add(MENU_GROUP, i, i, "abc");
        }
//        menu.setGroupEnabled(MENU_GROUP, false);
//        menu.setGroupCheckable(MENU_GROUP, true, true);
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
        LogUtils.d("id", id + "");
        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_settings:
                Snackbar.make(drawerLayout, "setting", Snackbar.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Snackbar.make(drawerLayout, "home", Snackbar.LENGTH_LONG).show();
                break;

            case R.id.nav_messages:
                Snackbar.make(drawerLayout, "message", Snackbar.LENGTH_LONG).show();
                break;

            case R.id.nav_friends:
                Snackbar.make(drawerLayout, "friends", Snackbar.LENGTH_LONG).show();
                break;

            case R.id.nav_discussion:
                Snackbar.make(drawerLayout, "discussion", Snackbar.LENGTH_LONG).show();
                break;

            case R.id.sub_fir:
                startService(new Intent(DrawerActivity.this, BackgroundService.class));
                finish();
                break;

            case R.id.sub_sec:
                startActivity(new Intent(this, DetailActivity.class));
                break;
        }
        LogUtils.d("menu id", "menu id:" + menuItem.getItemId() + " group id:" + menuItem.getGroupId());
        drawerLayout.closeDrawers();
        return true;
    }

//    @Override
//    public void onStart(String method) {
//        LogUtils.d("onStart", method);
//        showToast("onStart");
//        showProgressDialog("加载中，请稍候...", true);
//    }

    @Override
    public void onGetWholeObjectSuccess(String method, JSONObject wholeObject) {
        switch (method) {
            case "placeOrder":
                LogUtils.d("onGetWholeObjectSuccess", method + " -- " + wholeObject.toString());
                break;
        }
    }
//
    @Override
    public void onGetDataObjectSuccess(String method, JSONObject dataObject) {
        switch (method) {
            case "placeOrder":
                LogUtils.d("onGetWholeObjectSuccess", method + " -- " + dataObject.toString());
                break;
        }
    }
//
    @Override
    public void onGetListObjectSuccess(String method, JSONArray listArray) {
        switch (method) {
            case "placeOrder":
                LogUtils.d("onGetWholeObjectSuccess", method + " -- " + listArray.toString());
                break;
        }
    }

    @Override
    public void onFailed(int code, String method, String msg, JSONObject object) {
        switch (method) {
            case "placeOrder":
                LogUtils.d("onGetWholeObjectSuccess", code + " -- " + method);
                break;
        }
    }

    @Override
    public void onFinish(String method) {
        LogUtils.d("onFinish", method);
    }
}
