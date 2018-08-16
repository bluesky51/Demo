package com.sky.wang.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.inputmethod.InputMethodManager;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by bluesky on 2018/7/23.
 */

public abstract class BaseApplication extends Application {
    protected List<Activity> activityList;

    @Override
    public void onCreate() {
        super.onCreate();
        activityList = new ArrayList<>();
    }

    /**
     * 打开一个页面
     *
     * @param activity activity
     */
    public void openActivity(@NonNull Activity activity) {
        activityList.add(0, activity);
    }

    /**
     * 关闭一个页面
     *
     * @param activity activity
     */
    public void closeActivity(@NonNull Activity activity) {
        activityList.remove(activity);
    }

    /**
     * 退出App
     */
    public void exitApp() {
        if (activityList.size() > 0) {
            for (Activity activity : activityList) {
                activity.finish();
            }
        }
    }

    /**
     * 隐藏键盘
     *
     * @param activity 页面
     */
    public void hideSoftKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null && activity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * 显示键盘
     */
    public void showSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, 0);
        }
    }

    public void needLogin() {
        exitApp();
        if (activityList.size() > 0) {
            activityList.get(0).startActivity(needLoginIntent());
        }
    }
    public abstract Intent needLoginIntent();
}