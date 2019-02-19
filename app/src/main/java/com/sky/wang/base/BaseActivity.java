package com.sky.wang.base;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.sky.wang.utils.ActivityStore;
import com.sky.wang.view.home.HomeActivity;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by bluesky on 2018/7/23.
 */

public abstract class BaseActivity extends RxAppCompatActivity {
    private long backTime;
    private static final long TIME = 1000L;

    public LifecycleTransformer getLifecycleTransformer(){
           return  bindUntilEvent(ActivityEvent.DESTROY);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BarUtils.setStatusBarAlpha(this,0);
        super.onCreate(savedInstanceState);
        ActivityStore.addActivity(this);
    }

//    @Override
//    public void onBackPressed() {
//        if ((System.currentTimeMillis() - backTime) > TIME) {
//            backTime = System.currentTimeMillis();
//            Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_LONG).show();
//            return;
//        } else {
//            ActivityStore.finishAll();
//        }
//    }

}
