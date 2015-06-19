package com.studio.jason.jframe;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.studio.jframework.widget.toast.FullWidthToast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
    }

    public void performClick(View v){
        switch (v.getId()){
            case R.id.button1:
                FullWidthToast toast = new FullWidthToast(this);
                toast.setBackgroundColor(Color.WHITE).setTextColor(Color.BLACK).setText("ceshi")
                        .setImage(getResources().getDrawable(R.mipmap.ic_launcher))
                        .setDuration(Toast.LENGTH_LONG).setGravity(Gravity.CENTER)
                        .setAnimation(R.style.top_default_toast_animation).show();
                break;
            case R.id.button2:
                FullWidthToast.makeToast(this,"ceshi2", Toast.LENGTH_SHORT, null).show();
                break;
            default:
        }
    }
}
