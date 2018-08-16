package com.sky.wang.view.splash;

import android.app.Activity;
import com.sky.wang.base.mvpbase.presenter.BaseMvpPresenter;
import com.sky.wang.http.DataManager;
import com.sky.wang.model.bean.DataInfo;
import com.sky.wang.subscrible.OnResultListener;
import com.sky.wang.subscrible.ResultSubcrible;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bluesky on 2018/7/23.
 */

public class SplashPresenter  extends BaseMvpPresenter<SplashView>{

    public void test(Activity activity){
            Map<String,String> map =new HashMap<>();
            map.put("id","27610708");
            map.put("page","1");
            DataManager.getInstance().getDataInfo(activity,map,new ResultSubcrible<DataInfo>(new OnResultListener<DataInfo>() {
                @Override
                public void onNext(DataInfo dataInfo) {
                    getMvpView().hideProgressDialog();
                    if (dataInfo!=null){
                        getMvpView().onSuccess(dataInfo);
                    }else{
                       getMvpView().noContentStatus();
                    }
                }

                @Override
                public void onError(String errorMsg)
                { getMvpView().hideProgressDialog();
                    getMvpView().onError(errorMsg);
                }
            }));
    }
}
