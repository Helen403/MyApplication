package com.example.snoy.myapplication.lib.custemview;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.snoy.myapplication.R;

/**
 * Created by Administrator on 2016/9/8.
 */
public final class MyShowMsgToTwoView extends RelativeLayout {
    public View view;
    //标题
    public TextView tv_title;
    //内容
    public TextView tv_content;
    //取消
    public TextView tv_close;
    //确定
    public TextView tv_sure;

    public MyShowMsgToTwoView(Context context) {
        super(context);
        view = View.inflate(context, R.layout.custermview_base_msg_show_2, this);
        view.setDrawingCacheEnabled(true);
        initView();
        initListener();
    }

    private void initView() {
       tv_title = (TextView) view.findViewById(R.id.title);
        tv_content = (TextView) view.findViewById(R.id.content);
        tv_close = (TextView) view.findViewById(R.id.close);
        tv_sure = (TextView) view.findViewById(R.id.sure);
    }

    private void initListener() {
        tv_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.GONE);
            }
        });
    }

    /***********************************************************/
    /**
     * 设置标题  设置内容  设置关闭  设置确定
     */
    public void setMessage(String title, String content, String close, String sure) {
        if (!TextUtils.isEmpty(title)){
           tv_title.setText(title);
        }
        if (!TextUtils.isEmpty(title)){
           tv_content.setText(content);
        }
        if (!TextUtils.isEmpty(title)){
           tv_close.setText(close);
        }
        if (!TextUtils.isEmpty(title)){
           tv_sure.setText(sure);
        }
    }


}
