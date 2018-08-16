package com.sky.wang.base;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.sky.wang.App;
import com.sky.wang.app.AppStatusConstant;
import com.sky.wang.app.AppStatusManager;
import com.sky.wang.utils.FastClickUtils;
import com.sky.wang.view.home.HomeActivity;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by bluesky on 2018/7/23.
 */

public abstract class BaseActivity extends RxAppCompatActivity implements View.OnClickListener {
    protected boolean isDestroy = false;

    public LifecycleTransformer getLifecycleTransformer(){
           return  bindUntilEvent(ActivityEvent.DESTROY);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if (AppStatusManager.getInstance().getAppStatus() == AppStatusConstant.STATUS_FORCE_KILLED) {
            restartApp();
            return;
        }
        App.getInstance().openActivity(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (AppStatusManager.getInstance().getAppStatus() == AppStatusConstant.STATUS_FORCE_KILLED) {
            restartApp();
        }
    }
    protected void restartApp() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(AppStatusConstant.KEY_HOME_ACTION, AppStatusConstant.ACTION_RESTART_APP);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        if (FastClickUtils.doubleClick()) return;
        normalOnClick(v);
    }
    /**
     * 不用检查网络，可以直接触发的点击事件
     */
    protected void normalOnClick(View v) {
    }
    public boolean isDestoryed(){
        return isDestroy;
    }

    protected void onDestroy() {
        isDestroy = true;
        super.onDestroy();
        App.getInstance().closeActivity(this);
    }

    //重写dispatchKeyEvent方法，对软键盘的一些点击事件，进行监听（例如enter，返回键）
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        try {
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                hideSoftKeyBoard();
                onEnterPressed();
                return true;
                //点击返回键时候
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                onBackKeyPressed();
                return true;
            }
        } catch (Exception e) {

        }
        return super.dispatchKeyEvent(event);
    }
    //当软键盘enter按键被按下时候，此方法会被调用，此方法需要子类重写
    public void onEnterPressed() {
    }
    /*隐藏软键盘*/
    public void hideSoftKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && inputMethodManager.isActive() && getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    //如果子类需要在点击返回键时候，做一些事情，重写下面方法即可
    public void onBackKeyPressed() {
        finish();
    }

}
