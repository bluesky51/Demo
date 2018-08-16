package com.sky.wang.base.mvpbase.factory;

import com.sky.wang.base.mvpbase.presenter.BaseMvpPresenter;
import com.sky.wang.base.mvpbase.view.BaseMvpView;

/**
 * Created by bluesky on 2018/7/23.
 *
 */

public interface PresenterMvpFactory<V extends BaseMvpView,P extends BaseMvpPresenter<V>> {

    /**
     * 创建Presenter的接口方法
     * @return 需要创建的Presenter
     */
    P createMvpPresenter();

}
