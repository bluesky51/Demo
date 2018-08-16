package com.sky.wang.apiservice;

import com.sky.wang.model.bean.DataInfo;
import com.sky.wang.http.ServerUrl;
import com.sky.wang.base.mvpbase.model.BaseModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by bluesky on 2018/7/23.
 * 请求参数接口类
 */

public interface RetrofitService {
    /**
     * 获取快递信息
     * Rx方式
     * @return Observable<ExpressInfo>
     *
     * 传递的参数为：  id=27610708&page=1
     */
    @GET(ServerUrl.UrlOrigin.get_info)
    Observable<BaseModel<DataInfo>> getDataInfo(@QueryMap Map<String,String> map);
}
