package com.example.snoy.myapplication.lib.Utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 屏幕工具类
 * dip2px dp转像素
 * dip2sp dip转sp
 * px2dip 像素转dp
 * px2sp 像素转sp
 * sp2px sp转像素
 * sp2dip sp转dip
 * getScreenW 获取屏幕宽度
 * getScreenH 获取屏幕高度
 * getScreenRealSize 获取屏幕的真实高度
 * getStatusBarH 获取状态栏高度
 * getNavigationBarrH 获取导航栏高度
 */
public final class DensityUtil {

    /**
     * dip2px dp转像素
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * dip2sp dip转sp
     */
    public static int dip2sp(Context context, float dpValue) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics()));
    }

    /**
     * px2dip 像素转dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * px2sp 像素转sp
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * sp2px sp转像素
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * sp2dip sp转dip
     */
    public static int sp2dip(Context context, float spValue) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics()));
    }

    /**
     * getScreenW 获取屏幕宽度
     */
    public static int getScreenW(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * getScreenH 获取屏幕高度
     */
    public static int getScreenH(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * getScreenRealSize 获取屏幕的真实高度
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getScreenRealH(Context context) {
        int h;
        WindowManager winMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = winMgr.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(dm);
            h = dm.heightPixels;
        } else {
            try {
                Method method = Class.forName("android.view.Display").getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, dm);
                h = dm.heightPixels;
            } catch (Exception e) {
                display.getMetrics(dm);
                h = dm.heightPixels;
            }
        }
        return h;
    }

    /**
     * getStatusBarH 获取状态栏高度
     */
    public static int getStatusBarH(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * getNavigationBarrH 获取导航栏高度
     */
    public static int getNavigationBarrH(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelOffset(identifier);
    }
}
