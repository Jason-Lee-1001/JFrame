package com.studio.jason.jframe;

import android.os.Bundle;

import com.studio.jframework.utils.ExitAppUtils;
import com.swipebacklayout.lib.app.SwipeBackActivity;


/**
 * Created by Jason
 */
public class SwipeSecActivity extends SwipeBackActivity {

    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExitAppUtils.getInstance().addActivity(this);
//        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                text.equals("");
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ExitAppUtils.getInstance().removeActivity(this);

    }
}
