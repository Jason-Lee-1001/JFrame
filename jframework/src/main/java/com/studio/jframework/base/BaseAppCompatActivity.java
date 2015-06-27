package com.studio.jframework.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.studio.jframework.utils.ExitAppUtils;
import com.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Jason<p/>
 * Base class for the activity
 *
 */
public abstract class BaseAppCompatActivity extends SwipeBackActivity {

    /**
     * If savedInstanceState is not null, will restart the whole application
     * @param savedInstanceState The savedInstanceState from onSaveInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            restartApp();
            savedInstanceState.clear();
            savedInstanceState = null;
        }
        ExitAppUtils.getInstance().addActivity(this);
    }

    /**
     * Do find views in onCreateView()
     */
    public abstract void findViews();

    /**
     * Do some initialization in onCreateView()
     */
    public abstract void initialization();

    /**
     * Do bind event in onCreateView()
     */
    public abstract void bindEvent();

    /**
     * For the use in restoring activity when the memory of this app is recycled, when the user
     * restore the app, will detect the savedInstanceState is not null, then we will restart the
     * app again.
     */
    private void restartApp() {
        Intent intentForPackage = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        intentForPackage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intentForPackage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentForPackage);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Start an activity with specify Bundle
     * @param paramClass The activity class you want to start
     * @param paramBundle The bundle you want to put in the intent
     */
    protected void openActivityWithBundle(Class<?> paramClass, Bundle paramBundle){
        Intent intent = new Intent(this, paramClass);
        if(paramBundle != null) {
            intent.putExtras(paramBundle);
        }
        startActivity(intent);
    }

    /**
     * Judge whether the keyboard is showing
     *
     * @return Trur if the keyboard is showing, false otherwise
     */
    protected boolean isKeyboardShown() {
        return getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED;
    }

    protected void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
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
}
