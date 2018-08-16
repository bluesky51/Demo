package com.sky.wang.base.mvpbase.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sky.wang.app.AppStatusConstant;
import com.sky.wang.app.AppStatusManager;
import com.sky.wang.base.BaseActivity;
import com.sky.wang.base.mvpbase.factory.PresenterMvpFactory;
import com.sky.wang.base.mvpbase.factory.PresenterMvpFactoryImpl;
import com.sky.wang.base.mvpbase.presenter.BaseMvpPresenter;
import com.sky.wang.base.mvpbase.proxy.BaseMvpProxy;
import com.sky.wang.base.mvpbase.proxy.PresenterProxyInterface;

/**
 * Created by bluesky on 2018/7/23.
 * activity基类
 */

public class AbstractMvpAppCompatActivity<V extends BaseMvpView, P extends BaseMvpPresenter<V>>
        extends BaseActivity implements PresenterProxyInterface<V, P>, View.OnClickListener {
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private BaseMvpProxy<V, P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V, P>createFactory(getClass()));

    /**
     * BaseActivity的内容
     */
    public final String TAG = this.getClass().getSimpleName();
    protected boolean isDestroy = false;

    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) {
        mProxy.setPresenterFactory(presenterFactory);
    }

    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        return mProxy.getPresenterFactory();
    }

    @Override
    public P getMvpPresenter() {
        return mProxy.getMvpPresenter();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppStatusManager.getInstance().getAppStatus() == AppStatusConstant.STATUS_FORCE_KILLED) {
            restartApp();
            return;
        }
        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
        mProxy.onResume((V) this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppStatusManager.getInstance().getAppStatus() == AppStatusConstant.STATUS_FORCE_KILLED) {
            restartApp();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
        isDestroy = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       outState.putBundle(PRESENTER_SAVE_KEY, mProxy.onSaveInstanceState());
    }


}
