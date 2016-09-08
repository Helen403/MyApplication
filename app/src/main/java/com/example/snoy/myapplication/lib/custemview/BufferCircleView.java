package com.example.snoy.myapplication.lib.custemview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.snoy.myapplication.R;


/**
 * Created by Administrator on 2016/8/26.
 */
public final class BufferCircleView extends RelativeLayout {
    //转圈圈的大小
    private int ImgSize = 40;
    private AnimationDrawable spinner;

    public BufferCircleView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.custermview_dialog_progress_custom, this);
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        //设置转圈圈
        LinearLayout.LayoutParams ImgLayoutParams = new LinearLayout.LayoutParams(dip2px(getContext(), ImgSize), dip2px(getContext(), ImgSize));
        ImageView imageView = (ImageView) view.findViewById(R.id.spinnerImageView);
        imageView.setLayoutParams(ImgLayoutParams);
        // 获取ImageView上的动画背景
        spinner = (AnimationDrawable) imageView.getBackground();
        //文字
        TextView txt = (TextView) view.findViewById(R.id.message);
        txt.setText("正在加载");
        onShape(ll);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void onShape(LinearLayout ll) {
        int roundRadius = dip2px(getContext(), 8); // 8dp 圆角半径
        int fillColor = Color.parseColor("#ff404040");//内部填充颜色

        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        ll.setBackground(gd);
    }

    /**
     * 向外界提供显示
     */
    public void show() {
        this.setVisibility(VISIBLE);
        startAnimation();
    }

    /**
     * 向外界提供显示隐藏的方法
     */
    public void hide() {
        this.setVisibility(GONE);
        stopAnimation();
    }

    /**
     * 点击屏幕就隐藏
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        hide();
        return super.onTouchEvent(event);
    }

    /**
     * 判断是否隐藏
     */
    public boolean isVisibility() {
        return this.getVisibility() == VISIBLE;
    }


    /**
     * 暂停动画
     */
    public void stopAnimation() {
        spinner.stop();
    }

    /**
     * 开始动画
     */
    public void startAnimation() {
        if (isVisibility())
            spinner.start();
    }


    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
