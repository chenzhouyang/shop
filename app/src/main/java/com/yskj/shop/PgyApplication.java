package com.yskj.shop;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.pgyersdk.crash.PgyCrashManager;
import com.zhy.http.okhttp.OkHttpUtils;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class PgyApplication extends Application {
    public static PgyApplication instance = null;
    public static SharedPreferences sp;
    public static PgyApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化 JPush
        instance = this;
        PgyCrashManager.register(getApplicationContext());
        sp = getSharedPreferences("UserInfor", 0);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);

        //TODO		打包的时候 LogLevel.NONE
        if (BuildConfig.DEBUG) {
            Logger.init("LSKMall").setLogLevel(LogLevel.FULL);
        } else {
            Logger.init("LSKMall").setLogLevel(LogLevel.NONE);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
