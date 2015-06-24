package com.studio.jason.jframe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.studio.jframework.utils.ExitAppUtils;
import com.swipebacklayout.lib.app.SwipeBackActivity;


/**
 * Created by Administrator on 2015/6/23.
 */
public class SwipeActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExitAppUtils.getInstance().addActivity(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SwipeActivity.this, SwipeSecActivity.class));
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitAppUtils.getInstance().removeActivity(this);
    }
}
