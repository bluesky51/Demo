package com.sky.wang.utils;

/**
 * Created by bluesky on 2018/7/23.
 * 禁双击事件
 */

public class FastClickUtils {
    public static long currentTime;

    public static boolean doubleClick() {
        long time = System.currentTimeMillis();
        boolean res = Math.abs((time - currentTime)) < 350;
        currentTime = time;
        return res;
    }

    public static long currentTimeLong;

    /**
     * 点击间隔1000毫秒的
     *
     * @return
     */
    public static boolean doubleClickLong() {
        long time = System.currentTimeMillis();
        boolean res = Math.abs((time - currentTimeLong)) < 1000;
        currentTimeLong = time;
        return res;
    }

    public static long currentTimeRequestInterface;

    /**
     * 请求服务器间隔2000毫秒的
     *
     * @return
     */
    public static boolean fastRequestService() {
        long time = System.currentTimeMillis();
        boolean res = Math.abs((time - currentTimeRequestInterface)) < 100;
        currentTimeRequestInterface = time;
        return res;
    }


    public static long currentTimeSearchInterval;

    /**
     * 快速搜索请求服务器间隔1000毫秒的
     *
     * @return
     */
    public static boolean fastRequestShopFragment() {
        long time = System.currentTimeMillis();
        boolean res = Math.abs((time - currentTimeSearchInterval)) < 1000;
        currentTimeSearchInterval = time;
        return res;
    }


    public static long currentTimeUpdate;

    /**
     * 快速搜索请求服务器间隔1000毫秒的
     *
     * @return
     */
    public static boolean fastRequestUpdate() {
        long time = System.currentTimeMillis();
        boolean res = Math.abs((time - currentTimeUpdate)) < 2000;
        currentTimeUpdate = time;
        return res;
    }


    public static long fastDragTime;

    /**
     * 快速搜索请求服务器间隔1000毫秒的
     *
     * @return
     */
    public static boolean fastDrag() {
        long time = System.currentTimeMillis();
        boolean res = Math.abs((time - fastDragTime)) < 300;
        fastDragTime = time;
        return res;
    }
}
