package com.sky.wang.base.mvpbase.view;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sky.wang.App;
import com.sky.wang.R;
import com.sky.wang.base.mvpbase.factory.PresenterMvpFactory;
import com.sky.wang.base.mvpbase.factory.PresenterMvpFactoryImpl;
import com.sky.wang.base.mvpbase.presenter.BaseMvpPresenter;
import com.sky.wang.base.mvpbase.proxy.BaseMvpProxy;
import com.sky.wang.base.mvpbase.proxy.PresenterProxyInterface;
import com.trello.rxlifecycle2.components.RxFragment;

/**
 * Created by bluesky on 2018/7/23.
 * fragment基类
 */

public class AbstractFragment<V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends RxFragment implements PresenterProxyInterface<V, P> {
    public String TAG = this.getClass().getSimpleName();
    protected AbstractMvpAppCompatActivity baseActivity;
    public App getApp() {
        return (App) getActivity().getApplication();
    }

    /**
     * 调用onSaveInstanceState时存入Bundle的key
     */
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private BaseMvpProxy<V, P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V, P>createFactory(getClass()));

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AbstractMvpAppCompatActivity) {
            baseActivity = (AbstractMvpAppCompatActivity) context;

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProxy.onResume((V) this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY, mProxy.onSaveInstanceState());
    }


    /**
     * 可以实现自己PresenterMvpFactory工厂
     *
     * @param presenterFactory PresenterFactory类型
     */
    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) {
        mProxy.setPresenterFactory(presenterFactory);
    }


    /**
     * 获取创建Presenter的工厂
     *
     * @return PresenterMvpFactory类型
     */
    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        return mProxy.getPresenterFactory();
    }

    /**
     * 获取Presenter
     *
     * @return P
     */
    @Override
    public P getMvpPresenter() {
        return mProxy.getMvpPresenter();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
    }

}
