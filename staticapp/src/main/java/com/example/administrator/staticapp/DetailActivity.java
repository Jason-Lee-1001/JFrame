/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.administrator.staticapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.studio.jframework.base.BaseSwipeActivity;

public class DetailActivity extends BaseSwipeActivity{

    public static final String EXTRA_NAME = "cheese_name";


    @Override
    protected boolean onRestoreState(Bundle paramSavedState) {
        return false;
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);

        loadBackdrop();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initialization() {

    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void doMoreInOnCreate() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_settings:
                startActivity(new Intent(this, DetailActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
//        Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true).setMessage("This is a message").setTitle("Title").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).create().show();
    }

}
