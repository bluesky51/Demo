package com.sky.wang.subscrible;
import com.sky.wang.base.mvpbase.model.BaseModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by bluesky on 2018/6/22.
 */

public class ResultSubcrible<T> implements Observer<BaseModel<T>> {
    private OnResultListener mOnResultListener;
    private Disposable d;
    public ResultSubcrible( OnResultListener mOnResultListener) {

        this.mOnResultListener = mOnResultListener;
    }


    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(BaseModel<T> t) {
          if (t.getCode()==200){
              mOnResultListener.onNext(t.getData());
          }else{
              mOnResultListener.onError(t.getMsg());
          }

    }

    @Override
    public void onError(Throwable e) {
       mOnResultListener.onError(e.getMessage());
    }

    @Override
    public void onComplete() {
        //如果处于订阅状态，则取消订阅
        if (!d.isDisposed()) {
            d.dispose();
        }
    }
}