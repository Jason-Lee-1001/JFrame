package com.studio.jframework.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.studio.jframework.utils.ExitAppUtils;
import com.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Base class for the activity
 *
 * @author Jason
 */
public abstract class BaseSwipeActivity extends SwipeBackActivity {

    /**
     * Perform initialization of all fragments and loaders
     *
     * @param savedInstanceState The savedInstanceState from onSaveInstanceState
     */
    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!onRestoreState(savedInstanceState)) {
            if (savedInstanceState != null) {
                savedInstanceState.clear();
                savedInstanceState = null;
                restartApp();
            }
        }
        setContentView();
        findViews();
        initialization();
        bindEvent();
        doMoreInOnCreate();
        ExitAppUtils.getInstance().addActivity(this);
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
     * Do bind event, such set event listener
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
     * Easy to dismiss the specify dialog
     *
     * @param dialog The dialog you want to dismiss
     */
    protected void dismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * Will remove the activity from the Activity List when the current activity was destroy.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitAppUtils.getInstance().removeActivity(this);
    }
}