package com.sky.wang.view.web;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.just.agentweb.AgentWeb;
import com.sky.wang.R;
import com.sky.wang.web.base.BaseAgentWebActivity;
import com.sky.wang.widget.CustomNavigatorBar;

/**
 * Created by bluesky on 2018/9/13.
 */
public class TestBridgeWebJsActivity extends AppCompatActivity {
    CustomNavigatorBar customNavigatorBar;
    private BridgeWebView mBridgeWebView;
    AgentWeb mAgentWeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web_js);
        customNavigatorBar = findViewById(R.id.customView);
        mBridgeWebView =new BridgeWebView(this);
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent((ViewGroup) this.findViewById(R.id.container), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//
                .useDefaultIndicator(-1, 2)//
                .setWebViewClient(new BridgeWebViewClient(mBridgeWebView))
                .setWebView(mBridgeWebView)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
//                .setDownloadListener(mDownloadListener) 4.0.0 删除该API
                .createAgentWeb()//
                .ready()//
                .go("file:///android_asset/jsbridge/demo.html");







        //js调用Android(Android注册submitFromWeb方法，js调用)
        mBridgeWebView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {

                Log.e("BlueSky","====data111111===="+data);
                function.onCallBack("从Android相应给js");
            }
        });

        //Android调用js函数
        mBridgeWebView.callHandler("functionInJs", "多的点点滴滴", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.e("BlueSky","====data===="+data);
            }
        });
        mBridgeWebView.send("hello");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
