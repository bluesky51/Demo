package com.sky.wang.base.mvpbase.view;

/**
 * Created by bluesky on 2018/7/23.
 */

public interface BaseMvpView {
    /**
     * 显示Loading
     */
    void showProgressDialog();
    /**
     * 隐藏Loading
     */
    void hideProgressDialog();

    /**
     * 无网络状态
     */
    void noNetworkStatus();

    /**
     * 无内容状态
     */
    void noContentStatus();


}
