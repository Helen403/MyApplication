package com.example.snoy.myapplication.lib.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.baidu.mapapi.SDKInitializer;
import com.example.snoy.myapplication.lib.Exception.CrashHandler;
import com.pgyersdk.crash.PgyCrashManager;


/**
 * Created by SNOY on 2016/8/5.
 */
public final class BaseApplication extends Application {

    public static Context context;
    public static Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 百度地图全局context 的初始化
        SDKInitializer.initialize(this);
        //捕获全局异常
        CrashHandler.getInstance().init();
        PgyCrashManager.register(this);
    }
}
