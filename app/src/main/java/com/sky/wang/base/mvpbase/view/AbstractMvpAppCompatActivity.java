package com.sky.wang.base.mvpbase.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sky.wang.base.BaseActivity;
import com.sky.wang.base.mvpbase.factory.PresenterMvpFactory;
import com.sky.wang.base.mvpbase.factory.PresenterMvpFactoryImpl;
import com.sky.wang.base.mvpbase.presenter.BaseMvpPresenter;
import com.sky.wang.base.mvpbase.proxy.BaseMvpProxy;
import com.sky.wang.base.mvpbase.proxy.PresenterProxyInterface;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bluesky on 2018/7/23.
 * activity基类
 */

public  abstract   class AbstractMvpAppCompatActivity<V extends BaseMvpView, P extends BaseMvpPresenter<V>>
        extends BaseActivity implements PresenterProxyInterface<V, P> {
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    private Unbinder mUnbinder;
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private BaseMvpProxy<V, P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V, P>createFactory(getClass()));

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
        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
        mProxy.onResume((V) this);
        setContentView(initContentView());
        mUnbinder = ButterKnife.bind(this);
        initData();
    }

    protected abstract void initData();

    protected abstract int initContentView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mProxy.onDestroy();
        isDestroy = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY, mProxy.onSaveInstanceState());
    }

}
