package com.sky.wang.subscrible;

/**
 * Created by bluesky on 2018/6/22.
 */

public interface OnResultListener<T> {

    void onNext(T t);

    void onError(String errorMsg);
}
