package com.sky.wang.http;

import android.app.Activity;

import com.sky.wang.App;
import com.sky.wang.apiservice.RetrofitService;
import com.sky.wang.base.BaseActivity;
import com.sky.wang.base.mvpbase.model.BaseModel;
import com.sky.wang.model.bean.DataInfo;
import com.sky.wang.subscrible.ResultSubcrible;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bluesky on 2018/7/23.
 */

public class DataManager {
    private RetrofitService retrofitService;

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final DataManager INSTANCE = new DataManager();
    }

    //获取单例
    public static DataManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 初始化Retrofit,拿到RetrofitService
     */
    private DataManager() {
        retrofitService = RetrofitHelper.getInstance().getRetrofitService();
    }

    //---------------------------------------------------------
    //从下边开始,就是各个接口的请求
    public void getDataInfo(Activity activity, Map<String, String> map, ResultSubcrible<DataInfo> resultSubcrible) {
        retrofitService.getDataInfo(map)
                .subscribeOn(Schedulers.newThread())// 子线程执行网络操作
                .observeOn(AndroidSchedulers.mainThread())// 转回主线程
                // 当 ACTIVITY 销毁时,Subscriber 里方法将不会执行
                //这一句是重点，当activity执行到了destroy方法后，onNext将不会执行了。
                  .compose(((BaseActivity)activity).getLifecycleTransformer())
                .subscribe(resultSubcrible);


    }
}
