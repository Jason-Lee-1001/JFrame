package com.example.administrator.staticapp;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.studio.jframework.utils.LogUtils;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private TextView headerText;

    private static final int MENU_GROUP = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        headerText = (TextView) findViewById(R.id.header_text);
        headerText.setText("User");

        setupToolbar();
        setupNavigationView();
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
        LogUtils.d("id", id+"");
        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_settings:
                Snackbar.make(drawerLayout,"setting", Snackbar.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                Snackbar.make(drawerLayout,"home", Snackbar.LENGTH_LONG).show();
                break;

            case R.id.nav_messages:
                Snackbar.make(drawerLayout,"message", Snackbar.LENGTH_LONG).show();
                break;

            case R.id.nav_friends:
                Snackbar.make(drawerLayout,"friends", Snackbar.LENGTH_LONG).show();
                break;

            case R.id.nav_discussion:
                Snackbar.make(drawerLayout,"discussion", Snackbar.LENGTH_LONG).show();
                break;

            case R.id.sub_fir:
                Snackbar.make(drawerLayout,"fir", Snackbar.LENGTH_LONG).show();
                break;

            case R.id.sub_sec:
                Snackbar.make(drawerLayout,"sec", Snackbar.LENGTH_LONG).show();
                break;
        }
        LogUtils.d("menu id", "menu id:" + menuItem.getItemId() + " group id:" + menuItem.getGroupId());
        drawerLayout.closeDrawers();
        return true;
    }
}
