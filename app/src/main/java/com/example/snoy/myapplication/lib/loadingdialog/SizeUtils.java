package com.example.snoy.myapplication.lib.loadingdialog;

import android.content.Context;

/**
 * Created by Luo_xiasuhuei321@163.com on 2016/11/6.
 * desc:
 */

class SizeUtils {
    /**
     * dp转px
     */
    static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    static boolean RightFirst = true;

    static boolean WrongFirst = true;
}
