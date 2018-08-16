package com.sky.wang.base.mvpbase.factory;

import com.sky.wang.base.mvpbase.presenter.BaseMvpPresenter;
import com.sky.wang.base.mvpbase.view.BaseMvpView;

/**
 * Created by bluesky on 2018/7/23.
 *  Presenter工厂实现类
 */

public class PresenterMvpFactoryImpl<V extends BaseMvpView, P extends BaseMvpPresenter<V>> implements PresenterMvpFactory<V, P> {

    /**
     * 需要创建的Presenter的类型
     */
    private final Class<P> mPresenterClass;


    /**
     * 根据注解创建Presenter的工厂实现类
     * @param viewClazz 需要创建Presenter的V层实现类
     * @param <V> 当前View实现的接口类型
     * @param <P> 当前要创建的Presenter类型
     * @return 工厂类
     */
    public static <V extends BaseMvpView, P extends BaseMvpPresenter<V>> PresenterMvpFactoryImpl<V,P> createFactory(Class<?> viewClazz){
        CreatePresenter annotation = viewClazz.getAnnotation(CreatePresenter.class);
        Class<P> aClass = null;
        if(annotation != null){
            aClass = (Class<P>) annotation.value();
        }
        return aClass == null ? null : new PresenterMvpFactoryImpl<V, P>(aClass);
    }


    private PresenterMvpFactoryImpl(Class<P> presenterClass) {
        this.mPresenterClass = presenterClass;
    }

    @Override
    public P createMvpPresenter() {
        try {
            return mPresenterClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Presenter创建失败!，检查是否声明了@CreatePresenter(xx.class)注解");
        }
    }
}
