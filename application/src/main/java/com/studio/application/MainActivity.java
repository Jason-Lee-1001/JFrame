package com.studio.application;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.studio.jframework.utils.LogUtils;
import com.studio.jframework.widget.toast.FullWidthToast;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        startService(new Intent(this, CoreService.class));
        LogUtils.setEnable(true);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                startActivity(new Intent(this, SecActivity.class));
                break;

            case R.id.button2:
                startActivity(new Intent(this, IndexableListViewActivity.class));
        }
    }

    public void onEvent(Bundle bundle) {
        String sec = bundle.getString("sec");
        FullWidthToast.makeToast(this, sec, Toast.LENGTH_LONG, getResources().getDrawable(R.mipmap.ic_launcher)).show();
        textView.setText(sec);

        ArrayList<Person> third = bundle.getParcelableArrayList("third");
        if (third != null) {
            LogUtils.d("",third.size()+"");
        }

        String response = bundle.getString("GetLyric");
        if(response !=null){
            LogUtils.e("", response);
        }
    }

    public static class Person implements Parcelable {

        private String name;

        public Person(String name) {
            this.name = name;
        }

        protected Person(Parcel in) {
            name = in.readString();
        }

        public static final Creator<Person> CREATOR = new Creator<Person>() {
            @Override
            public Person createFromParcel(Parcel in) {
                return new Person(in);
            }

            @Override
            public Person[] newArray(int size) {
                return new Person[size];
            }
        };

        public String getName() {
            return name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
        }
    }
}
