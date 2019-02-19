package com.sky.wang.view.web;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;

import com.sky.wang.R;
import com.sky.wang.web.base.BaseAgentWebActivity;
import com.sky.wang.widget.CustomNavigatorBar;

import org.json.JSONObject;

public class TestWebJsActivity extends BaseAgentWebActivity {

    CustomNavigatorBar customNavigatorBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web_js);
        customNavigatorBar = findViewById(R.id.customView);
        customNavigatorBar.setMidText("测试");
        customNavigatorBar.setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如下方法测试都是android调用js函数执行
//                mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroid");
//                mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroidParam","Hello ! Agentweb");
//                mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroidMoreParams", new ValueCallback<String>() {
//                    @Override
//                    public void onReceiveValue(String value) {
//                        Log.i("Info","value:"+value);
//                    }
//                },getJson(),"say:", " Hello! Agentweb");

//                mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroidInteraction","你好Js");
            }
        });
    }
    private String getJson(){

        String result="";
        try {

            JSONObject mJSONObject=new JSONObject();
            mJSONObject.put("id",1);
            mJSONObject.put("name","Agentweb");
            mJSONObject.put("age",18);
            result= mJSONObject.toString();
        }catch (Exception e){

        }

        return result;
    }
    @Override
    protected String getUrl() {
        return "file:///android_asset/js_interaction/hello.html";
    }

    @Override
    protected int getIndicatorColor() {
        return Color.parseColor("#ff0000");
    }

    @Override
    protected int getIndicatorHeight() {
        return 3;
    }

    @Override
    protected ViewGroup getAgentWebParent() {
        return (ViewGroup) this.findViewById(R.id.container);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
