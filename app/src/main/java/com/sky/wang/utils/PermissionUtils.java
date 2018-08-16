package com.sky.wang.utils;

import android.Manifest;
import android.app.Activity;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterActivity;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

/**
 * Created by bluesky on 2018/7/26.
 * 权限处理工具类
 */

public class PermissionUtils {
    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final PermissionUtils INSTANCE = new PermissionUtils();
    }
    //获取单例
    public static PermissionUtils getInstance() {
        return PermissionUtils.SingletonHolder.INSTANCE;
    }

    public void getGpsPerssion(final Activity activity){
        Dexter.withActivity(activity).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                Toast.makeText(activity,"授权通过",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(activity,"授权拒绝",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                 token.continuePermissionRequest();
            }
        }).check();
    }

}

