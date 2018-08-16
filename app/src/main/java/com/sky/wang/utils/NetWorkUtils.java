package com.sky.wang.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by bluesky on 2018/7/26.
 * 网络相关工具类
 */

public class NetWorkUtils {

    /**
     * 判断是否有网络连接
     * @param context
     * @return
     */
    public static boolean isNetWorkAvaliable(Context context) {
        boolean _isConnect = false;
        ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if (network != null) {
            _isConnect = conManager.getActiveNetworkInfo().isAvailable();
        }
        return _isConnect;
    }
}
