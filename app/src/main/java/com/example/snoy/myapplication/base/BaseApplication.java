package com.example.snoy.myapplication.base;

import android.app.Application;
import android.content.Context;


/**
 * Created by SNOY on 2016/8/5.
 */
public final class BaseApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
