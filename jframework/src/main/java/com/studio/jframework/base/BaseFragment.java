package com.studio.jframework.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Jason<p/>
 * Base class for the fragment
 */
public abstract class BaseFragment extends Fragment {

    protected View mFragmentView;
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(setLayout(),container,false);
        onCreateView();
        return mFragmentView;
    }

    /**
     * Method to set the layout
     * @return The layout resource id of the file
     */
    public abstract int setLayout();

    /**
     * The main job should be done here
     */
    public abstract void onCreateView();

    /**
     * Do some initialization in onCreateView()
     */
    public abstract void initialization();

    /**
     * Do find views in onCreateView()
     */
    public abstract void findViews(View view);

    /**
     * Do bind event in onCreateView()
     */
    public abstract void bindEvent();

    /**
     * Hide the soft keyboard
     * @param view One of the views in the current window
     */
    protected void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Show the soft keyboard
     * @param view One of the views in the current window
     */
    protected void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED);
        imm.showSoftInput(view, 0);
    }

    /**
     * Start an activity with specify Bundle
     * @param paramClass The activity class you want to start
     * @param paramBundle The bundle you want to put in the intent
     */
    protected void openActivityWithBundle(Class<?> paramClass, Bundle paramBundle){
        Intent intent = new Intent(mContext, paramClass);
        if(paramBundle != null) {
            intent.putExtras(paramBundle);
        }
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
