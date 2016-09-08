package com.example.snoy.myapplication.lib.MyRecycleView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.snoy.myapplication.R;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by shichaohui on 2015/8/7 0007.
 * <br/>
 * 自定义动画ImageView
 */
public class AnimImageView extends ImageView {

    private int dp1; // 1dp
    private int screenWidth; // 屏幕宽度
    private int height; // 当前View的高度
    private int maxHeight; // View的最大高度
    private int centerX; // 当前View的中心点的X坐标

    private String text = "下拉刷新";
    private float textWidth; // 文本一半宽度

    private int colorBg = Color.WHITE; // 背景

    private Paint mPaint;
    private Paint mPaintText;

    private Paint mPaintBitmap;

    private Path mPath;
    private String time;
    String timeTmp;
    //显示最后时间
    long mExitTimeLast = 0;
    //当前时间
    long mExitTime = 0;


    /**
     * 指示下拉和释放的箭头
     */
    private ImageView arrow;


    public AnimImageView(Context context) {
        this(context, null);
        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        time = formatter.format(curDate);
        timeTmp = time;
        mExitTimeLast = System.currentTimeMillis();
        mExitTime = mExitTimeLast;
    }

    public AnimImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        dp1 = AnimView.dip2px(context, 1);
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        centerX = screenWidth / 2;
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaintText = new Paint();
        mPaintText.setColor(Color.WHITE);
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(38);
        mPath = new Path();
        textWidth = mPaint.measureText(text);
        mPaintBitmap = new Paint();
        mPaintBitmap.setAntiAlias(true);
    }

    /**
     * @param color1
     * @param color2
     */
    public void setColor(int color1, int color2) {
        mPaint.setColor(Color.WHITE);
        mPaintText.setColor(Color.rgb(149, 156, 166));
//        colorBg = color1;
    }

    @Override
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        invalidate();
    }

    @Override
    public void draw(Canvas canvas) {

        if (getLayoutParams().height > dp1) {


            height = getLayoutParams().height - 60;

            // 背景
            canvas.drawColor(colorBg);
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pull_icon_big);
            // 定义矩阵对象
            Matrix matrix = new Matrix();
            // 缩放原图
            matrix.postScale(1f, 1f);
            //获取当前时间
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            Date curDate = new Date(System.currentTimeMillis());
            timeTmp = formatter.format(curDate);
            mExitTime = System.currentTimeMillis();
            if (mExitTime - mExitTimeLast > 2000) {
                mExitTimeLast = mExitTime;
                time = timeTmp;
            }
            // 文本
            if (height >= 130) {
                // 向左旋转45度，参数为正则向右旋转
                matrix.postRotate(-180);
                Bitmap dstbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(),
                        matrix, true);
                canvas.drawBitmap(dstbmp, centerX - 4 * textWidth + 20, height - textWidth - 10, null);
                canvas.drawText("松手刷新", centerX, height - 22, mPaintText);
                canvas.drawText("松手刷新", centerX, height - 22, mPaintText);
                canvas.drawText("最后更新：今天 " + time, centerX - 90, height + 22, mPaintText);
            } else {
                matrix.postRotate(0);
                Bitmap dstbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(),
                        matrix, true);
                canvas.drawBitmap(dstbmp, centerX - 4 * textWidth + 20, height - textWidth - 10, null);
                canvas.drawText("下拉刷新", centerX, height - 22, mPaintText);
                canvas.drawText("下拉刷新", centerX, height - 22, mPaintText);
                canvas.drawText("最后更新：今天 " + time, centerX - 90, height + 22, mPaintText);
            }
        }
    }


}