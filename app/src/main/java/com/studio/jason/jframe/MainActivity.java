package com.studio.jason.jframe;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.studio.jframework.base.BaseAppCompatActivity;
import com.studio.jframework.network.volley.VolleyController;
import com.studio.jframework.network.volley.VolleyErrorHelper;
import com.studio.jframework.network.volley.VolleyRequest;
import com.studio.jframework.utils.AESUtils;
import com.studio.jframework.utils.ExitAppUtils;
import com.studio.jframework.utils.LogUtils;
import com.studio.jframework.utils.PreferencesUtils;
import com.studio.jframework.widget.LoadMoreListView;
import com.studio.jframework.widget.toast.FullWidthToast;

import java.security.Key;


public class MainActivity extends BaseAppCompatActivity {

    public static final String TAG = "MainActivity";
    private Button button1, button2, button3;
    private AESUtils aesUtils;
    private String origin = "15507592016~!@#$%^&*().,?/\\[]{};|';<>`张三abccdegff";

    private ImageView imageView;

    public Drawable drawable;
    public LoadMoreListView listView = new LoadMoreListView(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
        findViews();
        bindEvent();
    }

    @Override
    public void initialization() {
        getSwipeBackLayout().setEnableGesture(false);
        ExitAppUtils.getInstance().addActivity(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getResources().getDisplayMetrics();
        Resources.getSystem().getDisplayMetrics();
        aesUtils = new AESUtils();
        drawable = getResources().getDrawable(R.drawable.color1);
        drawable.setColorFilter(Color.GREEN,PorterDuff.Mode.MULTIPLY);
        listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void loadMore() {

            }
        });
    }

    @Override
    public void findViews() {
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        imageView = (ImageView) findViewById(R.id.imageView);
    }



    @Override
    public void bindEvent() {
        button1.setOnClickListener(new View.OnClickListener() {
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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageDrawable(drawable);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyRequest request = new VolleyRequest("https://www.baidu.com/", null, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtils.d(TAG, response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtils.e(TAG, VolleyErrorHelper.getMessage(error));
                    }
                });
                VolleyController.getInstance(MainActivity.this).addToQueue(request,"b");
                VolleyController.getInstance(MainActivity.this).getImageLoader().get("https://www.baidu.com/img/bd_logo1.png", new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        imageView.setImageBitmap(response.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtils.e(TAG, VolleyErrorHelper.getMessage(error));
                    }
                },400,400);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Key key2 = aesUtils.generateKey("Jumook".getBytes());
                String enc = aesUtils.toHex(aesUtils.encryptToBytes(key2,origin));
                LogUtils.e(TAG,enc);
                Key key1 = aesUtils.generateKey("Jumook".getBytes());

                yy = aesUtils.decryptToBytes(key1,aesUtils.toByte(enc));
                LogUtils.e(TAG,new String(yy));
            }
        });
        PreferencesUtils preferencesUtils = PreferencesUtils.getInstance(this);
    }

    public byte[] by;
    public byte[] yy;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
