package com.example.snoy.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.snoy.myapplication.Utils.DButils;
import com.example.snoy.myapplication.Utils.HttpUtils;
import com.example.snoy.myapplication.constant.Constants;


/**
 * 核心 服务
 */
public final class CoreService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initData();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 超超耗时操作
     */
    public void initData() {
    }




}
