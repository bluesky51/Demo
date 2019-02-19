package com.sky.wang;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.sky.wang.app.AppStatusTracker;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by bluesky on 2018/7/23.
 */

public class App extends Application {
    private static App instance;
    private AppStatusTracker appStatusTracker;

    public  RefWatcher refWatcher=null;
    /**
     * 日志输出时的TAG
     */
    private static String mTag = "BlueSky";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);//对应的多Dex包分割器
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .tag("BlueSKy")// (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        if (!BuildConfig.DEBUG)
        {
            Logger.clearLogAdapters();

        }
        //        appStatusTracker = new AppStatusTracker(this);
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        refWatcher = LeakCanary.install(this);



    }

    public static App getInstance() {
        return instance;
    }
}
