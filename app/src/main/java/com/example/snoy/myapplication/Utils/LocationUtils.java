package com.example.snoy.myapplication.Utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.snoy.myapplication.base.BaseApplication;

/**
 * 用于定位
 */
public class LocationUtils {
    //配置一下
    private static Context context = BaseApplication.context;
    //是否只定位一次
    private static Boolean flag = true;

    /***
     * 初始化百度地图定位；
     */
    public static void getBDLocation(final OnLocationUtils onLocationUtils) {
        LocationClient mLocClient = new LocationClient(context);
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");// 设置坐标类型
        option.setOpenGps(true);// 设置开启gps
        option.setScanSpan(1000);// 设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
        option.setIsNeedAddress(true);// 设置是否需要地址信息，默认为无地址
        mLocClient.setLocOption(option);
        mLocClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location == null) {
                    return;
                }
                if (flag) {
                    onLocationUtils.onSuccess(location);
                    flag = false;
                }
                // L.d(location.getCountry() + "-->" + location.getProvince()
                // + "-->" + location.getCity() + "-->");
                // L.d(location.getAddrStr());
                //  纬度
                // location.getLatitude()
                //  经度
                // location.getLongitude()
            }
        });
        // 开启定位
        mLocClient.start();
    }


    /**
     * 异步定位的回调接口
     */
    public interface OnLocationUtils {
        void onSuccess(BDLocation location);
    }

}
