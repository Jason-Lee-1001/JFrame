package com.studio.jframework.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.studio.jframework.R;
import com.studio.jframework.network.base.NetworkCallback;
import com.studio.jframework.network.impl.HttpRequester;
import com.studio.jframework.ui.SystemBarTintManager;
import com.studio.jframework.utils.ExitAppUtils;
import com.studio.jframework.widget.dialog.DialogCreator;

/**
 * Base class for the activity
 *
 * @author Jason
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity implements NetworkCallback {

    protected static final short MODE_NONE = 0;
    protected static final short MODE_UP = 1;
    protected static final short MODE_MENU = 2;

    protected Dialog mProgressDialog;

    protected FragmentManager mFragmentManager;
    protected HttpRequester mRequester;
    private Toast mSingleToast;

    /**
     * Perform initialization of all fragments and loaders
     *
     * @param savedInstanceState The savedInstanceState from onSaveInstanceState
     */
    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        ExitAppUtils.getInstance().addActivity(this);
        if (!onRestoreState(savedInstanceState)) {
            if (savedInstanceState != null) {
                savedInstanceState.clear();
                savedInstanceState = null;
                restartApp();
            }
        }
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        mRequester = new HttpRequester(this);
        mSingleToast = Toast.makeText(this, "", Toast.LENGTH_LONG);
        setContentView();
        findViews();
        initialization();
        bindEvent();
        doMoreInOnCreate();
    }

    /**
     * To restore the environment with savedInstanceState, if you don't handle it by yourself,
     * The app will restart the whole application in case of crash when restore the killed process
     *
     * @param paramSavedState The savedInstanceState from onSaveInstanceState
     * @return If you handle it by yourself, you need to return true, default is false which indicate
     * that will restart the whole app
     */
    protected abstract boolean onRestoreState(Bundle paramSavedState);

    /**
     * Invoke setContentView(int layoutId) here, you may call setTheme(int resId) first
     */
    protected abstract void setContentView();

    /**
     * Do find views by id
     */
    protected abstract void findViews();

    /**
     * Do some initialization, such as getIntent()
     */
    protected abstract void initialization();

    /**
     * Do bind event, such as setup listener
     */
    protected abstract void bindEvent();

    /**
     * If you want to do more in onCreate method, you may do it here
     */
    protected abstract void doMoreInOnCreate();

    /**
     * For the use in restoring activity when the memory of this app is recycled, when the user
     * restore the app, will detect whether the savedInstanceState is null or not. If not, will
     * restart the app again.
     */
    private void restartApp() {
        Intent intentForPackage = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        intentForPackage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intentForPackage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentForPackage);
        finish();
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
     * Show the progress dialog
     *
     * @param message    The message in the dialog
     * @param cancelable Determine whether the dialog is cancelable
     */
    protected void showProgressDialog(String message, boolean cancelable) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
        mProgressDialog = DialogCreator.createNoDimProgressDialog(this, message);
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.show();
    }

    /**
     * Dismiss the progress dialog
     */
    protected void dismissProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start an activity with specify Bundle
     *
     * @param paramClass  The activity class you want to start
     * @param paramBundle The bundle you want to put in the intent
     */
    protected void openActivityWithBundle(Class<?> paramClass, Bundle paramBundle) {
        Intent intent = new Intent(this, paramClass);
        if (paramBundle != null) {
            intent.putExtras(paramBundle);
        }
        startActivity(intent);
    }

    /**
     * Replace a fragment in this activity with animation
     *
     * @param mContentId The layout if the fragment to be replaced
     * @param mFragment  The new fragment to be shown
     * @param mTag       The tag of the fragment
     * @param enterAnim  The entry animation
     * @param exitAnim   The exit animation
     */
    protected final void replaceFragments(int mContentId, Fragment mFragment, String mTag, int enterAnim, int exitAnim) {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(enterAnim, exitAnim);
        mFragmentTransaction.replace(mContentId, mFragment, mTag);
        mFragmentTransaction.commit();
    }

    /**
     * A method to set the color of the status bar since kitkat
     *
     * @param colorId The resource id of the color
     */
    protected void setSystemTintColor(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setTintColor(getResources().getColor(colorId));
        }
    }

    /**
     * Init the toolbar as the action bar
     *
     * @param toolBarId The id of the toolbar component
     * @param title     The title that show in tool bar
     * @param mode      Different style of the up indicate button
     */
    protected void setupToolBar(int toolBarId, CharSequence title, int mode) {
        setupToolBar(toolBarId, title, null, mode);
    }

    /**
     * Init the toolbar as the action bar
     *
     * @param toolBarId The id of the toolbar component
     * @param title     The title that show in tool bar
     * @param subTitle  The sub title that show in tool bar
     * @param mode      Different style of the up indicate button
     */
    protected void setupToolBar(int toolBarId, CharSequence title, CharSequence subTitle, int mode) {
        Toolbar toolbar = (Toolbar) findViewById(toolBarId);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            switch (mode) {
                case MODE_NONE:
                    break;
                case MODE_MENU:
                    actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    break;
                case MODE_UP:
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    break;
            }
            actionBar.setTitle(title);
            actionBar.setSubtitle(subTitle);
        }
    }

    /**
     * Judge whether the keyboard is showing
     *
     * @return True if the keyboard is showing, false otherwise
     */
    protected boolean isKeyboardShown() {
        return getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED;
    }

    /**
     * Easy to hide the soft keyboard
     *
     * @param view A view to getWindowToken
     */
    protected void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Force to hide the keyboard
     */
    protected void forceHideKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * Easy to show the soft keyboard
     *
     * @param view A view to getWindowToken
     */
    protected void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED);
        imm.showSoftInput(view, 0);
    }

    /**
     * Will remove the activity from the Activity List when the current activity was destroy.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitAppUtils.getInstance().removeActivity(this);
    }

    @Override
    public void onStart(String method) {

    }

    @Override
    public void onGetWholeObjectSuccess(String method, JsonObject wholeObject) {

    }

    @Override
    public void onGetDataObjectSuccess(String method, JsonObject dataObject) {

    }

    @Override
    public void onGetListObjectSuccess(String method, JsonArray listArray) {

    }

    @Override
    public void onFailed(int code, String method, String msg, JsonObject object) {

    }

    @Override
    public void onFinish(String method) {

    }
}