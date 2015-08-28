package com.studio.jason.jframe;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.studio.jason.jframe.Constants.Mail;
import com.studio.jframework.base.BaseAppCompatActivity;
import com.studio.jframework.network.volley.VolleyController;
import com.studio.jframework.network.volley.VolleyStringRequest;
import com.studio.jframework.utils.ExitAppUtils;
import com.studio.jframework.utils.JsonUtils;
import com.studio.jframework.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends BaseAppCompatActivity implements View.OnClickListener {

//    public static final String TAG = "MainActivity";
//    private Button button1, button2, button3;
//    private AESUtils aesUtils;
//    private String origin = "15507592016~!@#$%^&*().,?/\\[]{};|';<>`张三abccdegff";
//
//    private ImageView imageView;
//    private View dialog;
//    public Drawable drawable;
//    private Dialog dialogInstance;

    private RelativeLayout layout;
    private ImageView imageView;

    @Override
    public boolean onRestoreState(Bundle paramSavedState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
        setSystemTintColor(R.color.orange);
    }

    @Override
    public void initialization() {
//        ExitAppUtils.getInstance().addActivity(this);
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        getResources().getDisplayMetrics();
//        Resources.getSystem().getDisplayMetrics();
//        aesUtils = new AESUtils();
////        drawable = getResources().getDrawable(R.drawable.color1);
//        drawable.setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
//        dialog = LayoutInflater.from(this).inflate(R.layout.dialog, null, false);
//        DialogCreator creator = new DialogCreator();
////        dialogInstance = creator.createNormalDialog(MainActivity.this,dialog, DialogCreator.Position.TOP);
////        dialogInstance = creator.createProgressDialog(this, DialogCreator.Position.TOP, Color.GREEN, "loading");
//        Toast.makeText(this, SizeUtils.convertDp2Px(this, 100) + "", Toast.LENGTH_LONG).show();
        layout.setDrawingCacheEnabled(true);
    }

    @Override
    public void findViews() {
//        button1 = (Button) findViewById(R.id.button1);
//        button2 = (Button) findViewById(R.id.button2);
//        button3 = (Button) findViewById(R.id.button3);
//        imageView = (ImageView) findViewById(R.id.imageView);
        layout = (RelativeLayout) findViewById(R.id.layout);
        imageView = (ImageView) findViewById(R.id.imageView);
    }


    @Override
    public void bindEvent() {
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, SwipeActivity.class));
//                FullWidthToast toast = new FullWidthToast(MainActivity.this);
//                toast.setBackgroundColor(Color.WHITE).setTextColor(Color.BLACK).setText("ceshi")
//                        .setImage(getResources().getDrawable(R.mipmap.ic_launcher))
//                        .setDuration(Toast.LENGTH_LONG).setGravity(Gravity.CENTER)
//                        .setAnimation(R.style.top_default_toast_animation).show();
//            }
//        });
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageView.setImageDrawable(drawable);
//            }
//        });
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                VolleyStringRequest request = new VolleyStringRequest("https://www.baidu.com/", null, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        LogUtils.d(TAG, response);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        LogUtils.e(TAG, VolleyErrorHelper.getMessage(error));
//                    }
//                });
//                VolleyController.getInstance(MainActivity.this).addToQueue(request, "b");
//                VolleyController.getInstance(MainActivity.this).getImageLoader().get("https://www.baidu.com/img/bd_logo1.png", new ImageLoader.ImageListener() {
//                    @Override
//                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
//                        imageView.setImageBitmap(response.getBitmap());
//                    }
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        LogUtils.e(TAG, VolleyErrorHelper.getMessage(error));
//                    }
//                }, 400, 400);
//            }
//        });
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Key key2 = aesUtils.generateKey("Jumook".getBytes());
////                String enc = aesUtils.toHex(aesUtils.encryptToBytes(key2,origin));
////                LogUtils.e(TAG,enc);
////                Key key1 = aesUtils.generateKey("Jumook".getBytes());
////
////                yy = aesUtils.decryptToBytes(key1,aesUtils.toByte(enc));
////                LogUtils.e(TAG, new String(yy));
//                dialogInstance.show();
//
//            }
//        });
    }

    @Override
    protected void doMoreInOnCreate() {
        getNoticeList();
    }

    private void getNoticeList() {
        String url = "http://112.74.16.180:8694/v1/notice/showSystemNoticeList?sign=shenyouhuiAPI&app_token=7565461440730493&page=1";
        HashMap<String, String> params = new HashMap<>();
        VolleyStringRequest request = new VolleyStringRequest(Request.Method.POST, url, params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    LogUtils.d("", response);
                    JSONObject object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("data");
                    JSONArray array = data.getJSONArray("list");
                    List<Mail> mailList = JsonUtils.parseToList(Mail[].class, array.toString());
                    LogUtils.d("", mailList.size() + "");
                    for (int i = 0; i < mailList.size(); i++) {
                        LogUtils.d("", mailList.get(i).toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleyController.getInstance(this).addToQueue(request);
    }

//    public byte[] by;
//    public byte[] yy;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitAppUtils.getInstance().removeActivity(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                Bitmap bitmap = layout.getDrawingCache();
                if (bitmap != null) {
                    LogUtils.d("", "bitmap is not null");
                    imageView.setImageBitmap(bitmap);
                    OutputStream outputStream = null;
                    try {
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                        File file = new File(path, "aaa.png");
                        outputStream = new FileOutputStream(file);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        baos.writeTo(outputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }
}
