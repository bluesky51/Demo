package com.sky.wang.aspect;

import android.os.SystemClock;

import com.orhanobut.logger.Logger;
import com.sky.wang.App;
import com.sky.wang.base.BaseView;
import com.sky.wang.base.mvpbase.presenter.BaseMvpPresenter;
import com.sky.wang.utils.NetWorkUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Created by bluesky on 2018/7/26.
 */
@Aspect
public class PresenterAspect {
    @Pointcut(" (execution(* com.sky.wang.view.splash.SplashPresenter.*(..))) || " +
            "(execution(* com.sky.wang.view.home.HomePresenter.*(..)))  "
    )
    public void pointcut() {

    }

//    @Before("pointcut()")
//    public void before(JoinPoint point) {
//        Log.e("BlueSky","@Before");
//    }

    @Around("pointcut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature sig = joinPoint.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = joinPoint.getTarget();
        BaseMvpPresenter baseMvpPresenter=(BaseMvpPresenter)target;
        Logger.e("@target==="+target.toString());
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        if (currentMethod==null) return;
        Logger.e("@Around==="+currentMethod.getName());
        //long beginTime = SystemClock.currentThreadTimeMillis();
        if (!NetWorkUtils.isNetWorkAvaliable(App.getInstance())){
            baseMvpPresenter.getMvpView().noNetworkStatus();
        }else {
            baseMvpPresenter.getMvpView().showProgressDialog();
            joinPoint.proceed();// 目标方法执行完毕
        }
        //long endTime = SystemClock.currentThreadTimeMillis();
        //long dx = endTime - beginTime;
        //Logger.e("耗时：" + dx + "ms");
    }

//    @After("pointcut()")
//    public void after() {
//        Log.e("BlueSky","@After");
//    }
//
//    @AfterReturning("pointcut()")
//    public void afterReturning(JoinPoint point, Object returnValue) {
//        Log.e("BlueSky","@AfterReturning");
//    }
//
//    @AfterThrowing(value = "pointcut()", throwing = "ex")
//    public void afterThrowing(Throwable ex) {
//        Log.e("BlueSky","@afterThrowing");
//        Log.e("BlueSky","ex = " + ex.getMessage());
//    }


}
