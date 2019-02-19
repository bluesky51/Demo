package com.sky.wang.web;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.just.agentweb.AgentWeb;

/**
 * Created by bluesky on 2018/9/13.
 */
public class AndroidInterface {
    private AgentWeb agent;
    private Context context;

    public AndroidInterface(AgentWeb agent, Context context) {
        this.agent = agent;
        this.context = context;
    }
    @JavascriptInterface
    public void callAndroid(final String msg) {
     Log.e("BlueSky","==msg=="+msg);
    }

    }
