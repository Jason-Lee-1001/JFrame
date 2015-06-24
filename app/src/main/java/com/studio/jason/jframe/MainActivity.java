package com.studio.jason.jframe;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import com.studio.jframework.base.BaseAppCompatActivity;
import com.studio.jframework.utils.ExitAppUtils;
import com.studio.jframework.utils.LogUtils;
import com.studio.jframework.utils.SizeUtils;
import com.studio.jframework.widget.toast.FullWidthToast;


public class MainActivity extends BaseAppCompatActivity {

    public static final String TAG = "MainActivity";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExitAppUtils.getInstance().addActivity(this);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SwipeActivity.class));
                FullWidthToast toast = new FullWidthToast(MainActivity.this);
                toast.setBackgroundColor(Color.WHITE).setTextColor(Color.BLACK).setText("ceshi")
                        .setImage(getResources().getDrawable(R.mipmap.ic_launcher))
                        .setDuration(Toast.LENGTH_LONG).setGravity(Gravity.CENTER)
                        .setAnimation(R.style.top_default_toast_animation).show();
            }
        });
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Resources.getSystem().getDisplayMetrics();
        button = (Button) findViewById(R.id.button1);
        final ViewTreeObserver observer = button.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                button.getViewTreeObserver().removeOnPreDrawListener(this);
                LogUtils.d(TAG, "pre height" + button.getHeight() + "");
                return false;
            }
        });
        LogUtils.setEnable(true);
        LogUtils.d(TAG, dm.heightPixels + "");
        LogUtils.d(TAG, Resources.getSystem().getDisplayMetrics().heightPixels + "");


        LogUtils.d(TAG, "scheight" + SizeUtils.getScreenHeight(this) + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ExitAppUtils.getInstance().removeActivity(this);

    }
}
