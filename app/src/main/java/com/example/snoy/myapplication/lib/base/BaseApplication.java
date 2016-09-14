package com.example.snoy.myapplication.lib.base;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;


/**
 * Created by SNOY on 2016/8/5.
 */
public final class BaseApplication extends Application {

    public static Context context;

    // 应用在微信上申请的app_id
    public static String appid = "wx877b51cc596d6537";
    public static String token = "";

    //2代表是买单页面进入的微信支付
    public static int payTyPe;



    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 百度地图全局context 的初始化
        SDKInitializer.initialize(this);
    }
}
