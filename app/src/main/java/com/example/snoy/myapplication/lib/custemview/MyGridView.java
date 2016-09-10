package com.example.snoy.myapplication.lib.custemview;

import android.content.Context;
import android.widget.GridView;

/***
 * 解决只显示一行数据，scrollview嵌套GridView
 */
public class MyGridView extends GridView {

    public MyGridView(Context context) {
        super(context);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}