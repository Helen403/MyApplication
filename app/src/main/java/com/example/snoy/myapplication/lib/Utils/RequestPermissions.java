package com.example.snoy.myapplication.lib.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Administrator on 2016/10/28 0028.
 */
public class RequestPermissions {
    public static int REQUEST_EXTERNAL_STORAGE = 1;
    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.READ_LOGS,
            Manifest.permission.VIBRATE,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.GET_TASKS,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
    };

    /**
     * 读写权限申请回调true有权限false无权限
     */
    public static void writeExternalStorage(final Context mContext, PermissionCallBack mCallBack) {
        //检查是否拥有相应的权限
        int permission = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //没有权限发起申请
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //  6.0及以上版本才需要申请权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            else {
                mCallBack.setOnPermissionListener(false);
            }
        } else {
            mCallBack.setOnPermissionListener(true);
        }
    }

    /**
     * 读写权限申请回调true有权限false无权限
     *
     * @param mContext
     */
    public static void writeExternalStorage(final Context mContext, PermissionCallBack mCallBack, int requestCode) {
        //检查是否拥有相应的权限
        int permission = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // 没有权限发起申请
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 6.0及以上版本才需要申请权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS_STORAGE, requestCode);
            else {
                mCallBack.setOnPermissionListener(false);
            }
        } else {
            mCallBack.setOnPermissionListener(true);
        }
    }

    /**
     * 权限申请回调，需要在你调用的Activity中onRequestPermissionsResult调用该方法true申请权限成功false申请权限失败
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, PermissionCallBack mCallBack) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            //判断权限是否申请成功
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mCallBack.setOnPermissionListener(true);
            } else {
                mCallBack.setOnPermissionListener(false);
            }
            return;
        }
    }


    public interface PermissionCallBack {
        void setOnPermissionListener(Boolean bo);
    }
}