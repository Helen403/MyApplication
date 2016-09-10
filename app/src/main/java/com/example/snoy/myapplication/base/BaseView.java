package com.example.snoy.myapplication.base;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;


public abstract class BaseView extends RelativeLayout {
    //基本View
    protected View view;

    public BaseView(Context context) {
        super(context);
        view = View.inflate(context, getContentView(), this);
        view.setDrawingCacheEnabled(true);
        initData();
    }

    public abstract int getContentView();

    /**
     * 初始化数据
     */
    public abstract void initData();

}
