package com.example.snoy.myapplication.lib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.snoy.myapplication.base.BaseApplication;

/**
 * 特殊需求的Activity使用
 */
public abstract class MyBaseActivity extends Activity {

    //配置一下
    protected Context contextAppliction = BaseApplication.context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dealLogicBeforeFindView();
        setFullScreen(false);
        findViews();
        initData();
        setListeners();
    }


    /***
     * 给调用者调用
     */
    public abstract void dealLogicBeforeFindView();

    public abstract void findViews();

    public abstract void initData();

    public abstract void setListeners();
    /************************************************************/
    /**
     * 判断是否铺满全屏
     */
    public void setFullScreen(boolean fullScreen) {
        if (fullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }


    /*************************************************************/

    public void T(String msg) {
        Toast.makeText(contextAppliction, msg, Toast.LENGTH_SHORT).show();
    }

    public void T(float msg) {
        Toast.makeText(contextAppliction, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(double msg) {
        Toast.makeText(contextAppliction, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(int msg) {
        Toast.makeText(contextAppliction, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(boolean msg) {
        Toast.makeText(contextAppliction, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void L(String msg) {
        Log.d("Helen", msg);
    }

    public void L(float msg) {
        Log.d("Helen", msg + "");
    }

    public void L(double msg) {
        Log.d("Helen", msg + "");
    }

    public void L(int msg) {
        Log.d("Helen", msg + "");
    }

    public void L(boolean msg) {
        Log.d("Helen", msg + "");
    }

    /*************************************************************/

}
