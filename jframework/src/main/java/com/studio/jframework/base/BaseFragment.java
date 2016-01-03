package com.studio.jframework.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.studio.jframework.network.base.NetworkCallback;
import com.studio.jframework.network.impl.HttpRequester;

/**
 * Base class for the fragment
 *
 * @author Jason
 */
public abstract class BaseFragment extends Fragment implements NetworkCallback {

    protected View mFragmentView;
    protected Context mContext;
    protected boolean mIsVisible;
    protected boolean mPrepared;
    protected HttpRequester mRequester;
    protected Toast mSingleToast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mRequester = new HttpRequester(this);
        mSingleToast = Toast.makeText(mContext, "", Toast.LENGTH_LONG);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(setLayout(), container, false);
        findViews(mFragmentView);
        initialization();
        bindEvent();
        onCreateView();
        mPrepared = true;
        return mFragmentView;
    }

    /**
     * Method to set the layout
     *
     * @return The layout resource id of the file
     */
    protected abstract int setLayout();

    /**
     * Do find views in onCreateView()
     */
    protected abstract void findViews(View view);

    /**
     * Do some initialization in onCreateView()
     */
    protected abstract void initialization();

    /**
     * Do bind event in onCreateView()
     */
    protected abstract void bindEvent();

    /**
     * The main job should be done here
     */
    protected abstract void onCreateView();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible(mPrepared);
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    /**
     * This method will be invoked when the fragment is invisible to the user
     */
    protected void onInvisible() {
    }

    /**
     * This method will be invoked when the fragment is visible to the user
     *
     * @param prepared When prepared is true, means onCreateView return;
     */
    protected void onVisible(boolean prepared) {
    }

    /**
     * Easy way to show a toast
     *
     * @param message The message you want to toast
     */
    protected void showToast(String message) {
        mSingleToast.setText(message);
        mSingleToast.show();
    }

    /**
     * Show the progress dialog, the fragment should be inside BaseAppCompatActivity and visible
     *
     * @param message    The message in the dialog
     * @param cancelable Determine whether the dialog is cancelable
     */
    protected void showProgressDialog(String message, boolean cancelable) {
        if (mIsVisible && mContext instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) mContext).showProgressDialog(message, cancelable);
        }
    }

    /**
     * Dismiss the progress dialog, the fragment should be inside BaseAppCompatActivity and visible
     */
    protected void dismissProgressDialog() {
        if (mIsVisible && mContext instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) mContext).dismissProgressDialog();
        }
    }

    /**
     * Hide the soft keyboard
     *
     * @param view One of the views in the current window
     */
    protected void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Force to hide the keyboard
     */
    protected void forceHideKeyboard() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * Show the soft keyboard
     *
     * @param view One of the views in the current window
     */
    protected void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED);
        imm.showSoftInput(view, 0);
    }

    /**
     * Start an activity with specify Bundle
     *
     * @param paramClass  The activity class you want to start
     * @param paramBundle The bundle you want to put in the intent
     */
    protected void openActivityWithBundle(Class<?> paramClass, Bundle paramBundle) {
        Intent intent = new Intent(mContext, paramClass);
        if (paramBundle != null) {
            intent.putExtras(paramBundle);
        }
        startActivity(intent);
    }

    @Override
    public void onFinish(String method) {

    }

    @Override
    public void onFailed(int code, String method, String msg, JsonObject object) {

    }

    @Override
    public void onGetListObjectSuccess(String method, JsonArray listArray) {

    }

    @Override
    public void onGetDataObjectSuccess(String method, JsonObject dataObject) {

    }

    @Override
    public void onGetWholeObjectSuccess(String method, JsonObject wholeObject) {

    }

    @Override
    public void onStart(String method) {

    }
}
