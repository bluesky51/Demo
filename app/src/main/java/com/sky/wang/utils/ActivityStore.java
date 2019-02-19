package com.sky.wang.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * Created by bluesky on 2018/9/12.
 * activity 管理器
 */
public class ActivityStore {
    private static LinkedList<WeakReference<Activity>> activityList;

    static {
        activityList = new LinkedList<>();
    }

    // 添加Activity 到容器中
    public static void addActivity(Activity activity) {
        activityList.add(new WeakReference<>(activity));
    }

    /**
     * 删除activity
     */
    public static void removeActivity(Activity activity) {
        activityList.remove(new WeakReference<>(activity));
    }

    /**
     * 遍历所有注册登录Activity 并finish
     */
    public static void finishAll(final LinkedList<WeakReference<Activity>> activityList) {
        for (WeakReference<Activity> weak : activityList) {
            if (weak.get() != null) {
                weak.get().finish();
            }
        }
        activityList.clear();
    }

    // 遍历所有Activity 并finish
    public static void finishAll() {
        finishAll(activityList);
    }
}
