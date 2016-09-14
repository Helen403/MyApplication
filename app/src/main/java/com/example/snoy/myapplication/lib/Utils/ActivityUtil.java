package com.example.snoy.myapplication.lib.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author http://blog.csdn.net/finddreams
 * @Description: Activity的工具类
 */
public final class ActivityUtil {

    private static final String TAG = "ActivityUtil";

    /**
     * 延迟去往新的Activity
     */
    public static void delayToActivity(final Context context, final Class<?> cls, long delay) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                context.startActivity(new Intent(context, cls));
            }
        }, delay);
    }

    /**
     * 跳转到另一个Activity，不携带数据，不设置flag
     */
    public static void goToActivity(Context context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    /**
     * go to activity,use animation
     */
    public static void goToActivity(Context context, Class<?> cls, int enterAnim, int exitAnim, Bundle bundle) {
        Activity activity = (Activity) context;
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * to new activity,use animation from right to left
     */
    public static void goToActivityFromLeft2Right(Context context, Class<?> cls) {
        Activity activity = (Activity) context;
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        activity.startActivity(intent);
//		activity.overridePendingTransition(R.anim.in_from_right,R.anim.out_to_right);
    }

    /**
     * to new activity,use animation from right to left carry data
     */
    public static void goToActivityFromLeft2Right(Context context, Class<?> cls, Bundle bundle) {
        Activity activity = (Activity) context;
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        intent.putExtras(bundle);
        activity.startActivity(intent);
//		activity.overridePendingTransition(R.anim.in_from_right,R.anim.out_to_right);
    }

    /**
     * to new activity,use animation from left to right
     */
    public static void goToActivityFromRight2Left(Context context, Class<?> cls) {
        Activity activity = (Activity) context;
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        activity.startActivity(intent);
//		activity.overridePendingTransition(R.anim.in_from_left,R.anim.out_to_left);
    }

    /**
     * to new activity,use animation from left to right carry data
     */
    public static void goToActivityFromRight2Left(Context context, Class<?> cls, Bundle bundle) {
        Activity activity = (Activity) context;
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        intent.putExtras(bundle);
        activity.startActivity(intent);
//		activity.overridePendingTransition(R.anim.in_from_left,R.anim.out_to_left);
    }

    /**
     * to new activity,use animation from bottom to top carry data
     */
    public static void goToActivityFromBottom2Top(Context context, Class<?> cls, Bundle bundle) {
        Activity activity = (Activity) context;
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        intent.putExtras(bundle);
        activity.startActivity(intent);
//		activity.overridePendingTransition(R.anim.in_from_bottom,R.anim.out_to_top);
    }

    /**
     * to new activity,use animation from bottom to top
     */
    public static void goToActivityFromBottom2Top(Context context, Class<?> cls) {
        Activity activity = (Activity) context;
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        activity.startActivity(intent);
//		activity.overridePendingTransition(R.anim.in_from_bottom,R.anim.out_to_top);
    }

    /**
     * to new activity,use animation form top to bottom
     */
    public static void goToActivityFromTop2Bottom(Context context, Class<?> cls) {
        Activity activity = (Activity) context;
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        activity.startActivity(intent);
//		activity.overridePendingTransition(R.anim.in_from_top,R.anim.out_to_bottom);
    }


    /**
     * 跳转到另一个Activity，携带数据
     */
    public static void goToActivity(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


}
