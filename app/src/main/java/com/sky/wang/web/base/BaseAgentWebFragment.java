package com.sky.wang.web.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebSettingsImpl;
import com.just.agentweb.AgentWebUIControllerImplBase;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.IWebLayout;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.MiddlewareWebClientBase;
import com.just.agentweb.PermissionInterceptor;
import com.sky.wang.R;
import com.sky.wang.web.AndroidInterface;

/**
 * Created by bluesky on 2018/9/13.
 */
public abstract class BaseAgentWebFragment  extends Fragment{
    protected AgentWeb mAgentWeb;
    private MiddlewareWebChromeBase mMiddleWareWebChrome;
    private MiddlewareWebClientBase mMiddleWareWebClient;
    private BaseAgentWebActivity.ErrorLayoutEntity mErrorLayoutEntity;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buildAgentWeb();
    }
    private void buildAgentWeb() {
        BaseAgentWebActivity.ErrorLayoutEntity mErrorLayoutEntity = getErrorLayoutEntity();
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(getAgentWebParent(), new ViewGroup.LayoutParams(-1, -1))
                .useDefaultIndicator(getIndicatorColor(), getIndicatorHeight())//设置进度条颜色与高度，-1为默认值，单位为dp。
                .setWebChromeClient(getWebChromeClient())
                .setWebViewClient(getWebViewClient())
                .setWebView(getWebView())
                .setPermissionInterceptor(getPermissionInterceptor()) //权限拦截
                .setWebLayout(getWebLayout())
                .setAgentWebUIController(getAgentWebUIController())//自定义UI  AgentWeb3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .useMiddlewareWebChrome(getMiddleWareWebChrome())//设置WebChromeClient中间件，支持多个WebChromeClient
                .useMiddlewareWebClient(getMiddleWareWebClient())//设置WebViewClient中间件，支持多个WebViewClient
                .setAgentWebWebSettings(getAgentWebSettings())
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)//参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)//严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                .go(getUrl()); //WebView载入该url地址的页面并显示。
        //javascript调用java
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android",new AndroidInterface(mAgentWeb,getActivity()));
    }


    @Override
    public void onPause() {
        if (mAgentWeb!=null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();

    }

    @Override
    public void onResume() {
        if (mAgentWeb!=null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroyView();
    }

    protected @NonNull
    BaseAgentWebActivity.ErrorLayoutEntity getErrorLayoutEntity() {
        if (this.mErrorLayoutEntity == null) {
            this.mErrorLayoutEntity = new BaseAgentWebActivity.ErrorLayoutEntity();
        }
        return mErrorLayoutEntity;
    }

    protected AgentWeb getAgentWeb() {
        return this.mAgentWeb;
    }


    protected static class ErrorLayoutEntity {
        private int layoutRes = com.just.agentweb.R.layout.agentweb_error_page;
        private int reloadId;

        public void setLayoutRes(int layoutRes) {
            this.layoutRes = layoutRes;
            if (layoutRes <= 0) {
                layoutRes = -1;
            }
        }

        public void setReloadId(int reloadId) {
            this.reloadId = reloadId;
            if (reloadId <= 0) {
                reloadId = -1;
            }
        }
    }
    protected  String getUrl() { return null; }
    public AgentWebUIControllerImplBase getAgentWebUIController() { return null; }
    private WebChromeClient getWebChromeClient() { return null; }
    private WebViewClient getWebViewClient() { return null; }
    protected abstract ViewGroup getAgentWebParent();
    protected int getIndicatorColor() { return -1; }
    protected int getIndicatorHeight() {
        return -1;
    }
    protected WebView getWebView() { return null; }
    protected PermissionInterceptor getPermissionInterceptor() { return null; }
    protected IWebLayout getWebLayout() { return null; }
    protected MiddlewareWebChromeBase getMiddleWareWebChrome() {
        return this.mMiddleWareWebChrome = new MiddlewareWebChromeBase() {}; }
    protected MiddlewareWebClientBase getMiddleWareWebClient() {
        return this.mMiddleWareWebClient = new MiddlewareWebClientBase() {}; }
    public IAgentWebSettings getAgentWebSettings() {
        return AgentWebSettingsImpl.getInstance(); }
}
