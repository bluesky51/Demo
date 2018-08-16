package com.sky.wang.view.splash;

import com.sky.wang.base.BaseView;

/**
 * Created by bluesky on 2018/7/23.
 */

interface SplashView<T>  extends BaseView{

    void onSuccess(T t);

    void onError(String message);



}
