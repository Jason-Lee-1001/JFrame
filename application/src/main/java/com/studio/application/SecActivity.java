package com.studio.application;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class SecActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
    }


    @Override
    public void onClick(View v) {
        ArrayList<MainActivity.Person> persons = new ArrayList<>();
        persons.add(new MainActivity.Person("person from second activity"));
        persons.add(new MainActivity.Person("person from second activity"));
        persons.add(new MainActivity.Person("person from second activity"));
        persons.add(new MainActivity.Person("person from second activity"));
        Bundle bundle = new Bundle();
        bundle.putString("sec", "person from sec");
        bundle.putParcelableArrayList("third", persons);
        EventBus.getDefault().post(bundle);
        finish();
    }
}
