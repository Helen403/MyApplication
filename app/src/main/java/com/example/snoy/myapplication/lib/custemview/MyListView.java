package com.example.snoy.myapplication.lib.custemview;

import android.content.Context;
import android.widget.ListView;

/***
 * 解决只显示一行数据，scrollview 嵌套listview 或者 listview 嵌套 listview
 */
public final class MyListView extends ListView {

    public MyListView(Context context) {
        super(context);

    }



    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
