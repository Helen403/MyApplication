package com.example.snoy.myapplication.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.snoy.myapplication.MainActivity;
import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.Utils.DButils;
import com.example.snoy.myapplication.Utils.HttpUtils;
import com.example.snoy.myapplication.Utils.ImageUtils;
import com.example.snoy.myapplication.constant.Constants;
import com.example.snoy.myapplication.lib.NavView.AnimImageGroup;
import com.example.snoy.myapplication.lib.NavView.NavImgLayout;


public class WelcomeActivity extends Activity implements GestureDetector.OnGestureListener {

    private AnimImageGroup aigpic, aigcontent, aigtitle;
    private NavImgLayout navImag;
    private ImageView iv_logo;
    private boolean flag = true;
    private Handler handler = new Handler();
    private boolean flagOnce = true;
    /**
     * 声明一个手势的检测器对象
     * 1、初始化一个手势检测器的对象
     * 2、注册手势检测器的监听方法
     * 3、将activity的onTouchEvent方法（屏幕触摸方法）托管给手势检测器
     */
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        initDetector();
        gotoTimer();
    }

    private void initView() {
        aigpic = (AnimImageGroup) findViewById(R.id.animpicid);
        aigcontent = (AnimImageGroup) findViewById(R.id.animcontentid);
        aigtitle = (AnimImageGroup) findViewById(R.id.animtitleid);
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        navImag = (NavImgLayout) findViewById(R.id.navImag);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * 初始化手势检测器
     */
    private void initDetector() {
        gestureDetector = new GestureDetector(this, this);
    }

    /**
     * 只加载一次
     * 预加载数据
     */
    private void loadDatas() {
        //遍历Constants.JSON的URL
        int countJ = Constants.JSON.length;
        for (int i = 0; i < countJ; i++) {
            InsertSqlByUrl(Constants.JSON[i]);
        }
        int countB = Constants.BITMAP.length;
        for (int i = 0; i < countB; i++) {
            InsertSDCardByUrl(Constants.BITMAP[i]);
        }
    }


    /**
     * 从网络请求数据插入数据库
     * 存进Forever的表中
     */
    private void InsertSqlByUrl(final String url) {
        HttpUtils.posts(url, null, new HttpUtils.OnHttpUtilsResultListener() {
            @Override
            public void onHttpSuccess(String url, String result) {
                DButils.insertStringForeverBySql(url, result);
            }

            @Override
            public void onHttpFailure(String url) {
            }
        });
    }

    /**
     *
     */
    private void InsertSDCardByUrl(final String url) {
        HttpUtils.getBitmapByUrl(url, new HttpUtils.OnHttpUtilsBitmapListener() {
            @Override
            public void onImageSuccess(String url, Bitmap bitmap) {
                ImageUtils.getInstance().saveBitmapToSDCardByUrl(url, bitmap);
            }

            @Override
            public void onImageFailure(String url) {

            }
        });
    }

    /**
     * 跳转定时器
     */
    private void gotoTimer() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(DButils.get("once"))) {
                            //不是第一次登录
                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                            overridePendingTransition(R.anim.activity_in_from_rigth, R.anim.activity_out_to_scale);
                            finish();
                        } else {
                            Animation anim = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.img_left_out);
                            anim.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    iv_logo.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            iv_logo.startAnimation(anim);


                            Animation navanim = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.img_right_in);
                            navanim.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    navImag.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            navImag.startAnimation(navanim);

                            aigpic.nextContent();
                            aigcontent.nextContent();
                            aigtitle.nextContent();
                            flag = true;

                            //标记为第一次登录
                            DButils.put("once", "Helen");
                            //加载一次网络数据
                            loadDatas();
                        }
                    }
                });
            }
        }, 2000);
    }

    /**
     * 手势检测器的监听方法
     */
    @Override
    public boolean onDown(MotionEvent e) {
        //单击，手指触碰到触摸屏时触发
        if (navImag.getChildCount() - 1 == navImag.getIndex() && flagOnce) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.activity_in_from_rigth, R.anim.activity_out_to_scale);
            finish();
            flagOnce = false;
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //短按，手指触碰到触摸屏后，停留一小段时间，触发，如果立刻抬起，不触发
        if (navImag.getChildCount() - 1 == navImag.getIndex() && flagOnce) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.activity_in_from_rigth, R.anim.activity_out_to_scale);
            finish();
            flagOnce = false;
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //当单击或者短按后，然后抬起触发，如果单击后执行了长按，滚动，滑动的方法，该方法就不执行了
        if (navImag.getChildCount() - 1 == navImag.getIndex() && flagOnce) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.activity_in_from_rigth, R.anim.activity_out_to_scale);
            finish();
            flagOnce = false;
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //滚动，触摸当屏幕以后滑动触发
        if (navImag.getChildCount() - 1 == navImag.getIndex() && flagOnce) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.activity_in_from_rigth, R.anim.activity_out_to_scale);
            finish();
            flagOnce = false;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //长按，按住比较长的一段时间
        if (navImag.getChildCount() - 1 == navImag.getIndex() && flagOnce) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.activity_in_from_rigth, R.anim.activity_out_to_scale);
            finish();
            flagOnce = false;
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //滑动，在屏幕上有一个快速滚动的操作
        if (e2.getX() - e1.getX() > 50) {
            if (flag) {
                //左滑
                aigpic.aboveContent();
                aigcontent.aboveContent();
                aigtitle.aboveContent();
                navImag.above();
            }
        } else if (e1.getX() - e2.getX() > 50) {
            if (flag) {
                //右滑
                aigpic.nextContent();
                aigcontent.nextContent();
                aigtitle.nextContent();
                navImag.next();
            }
        }
        return false;
    }
}
