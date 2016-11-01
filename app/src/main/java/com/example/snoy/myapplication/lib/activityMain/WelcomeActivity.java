package com.example.snoy.myapplication.lib.activityMain;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.constant.Constants;
import com.example.snoy.myapplication.lib.NavView.AnimImageGroup;
import com.example.snoy.myapplication.lib.NavView.NavImgLayout;
import com.example.snoy.myapplication.lib.Utils.DButils;
import com.example.snoy.myapplication.lib.Utils.HttpUtils;
import com.example.snoy.myapplication.lib.Utils.ImageUtils;
import com.example.snoy.myapplication.lib.Utils.ProjectUtil;
import com.example.snoy.myapplication.lib.base.BaseActivity;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import dmax.dialog.SpotsDialog;


public final class WelcomeActivity extends BaseActivity implements GestureDetector.OnGestureListener {

    //左滑 右滑的灵敏度  数值越小 越灵敏
    private int sensitive = 25;

    private AnimImageGroup aigpic;
    private NavImgLayout navImag;
    private ImageView iv_logo;
    private boolean flag = true;
    private boolean flagOnce = true;


    private AlertDialog mDialog;
    private InnerHandler mHandler = new InnerHandler();
    private TextView tvMsgShow;


    /**
     * 声明一个手势的检测器对象
     * 1、初始化一个手势检测器的对象
     * 2、注册手势检测器的监听方法
     * 3、将activity的onTouchEvent方法（屏幕触摸方法）托管给手势检测器
     */
    private GestureDetector gestureDetector;


    @Override
    public int getContentView() {
        return R.layout.custermview_activity_welcome;
    }

    @Override
    public void findViews() {
        hideHeadView();
        status.setBackgroundColor(Color.parseColor("#18bbff"));
        aigpic = (AnimImageGroup) findViewById(R.id.animpicid);
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        navImag = (NavImgLayout) findViewById(R.id.navImag);
        tvMsgShow = (TextView) findViewById(R.id.tvMsgShow);
        mDialog = new SpotsDialog(this, "初始化...");
    }

    @Override
    public void initData() {
        //初始化手势检测器
        initDetector();
        gotoTimer();
    }

    @Override
    public void setListeners() {
    }

    /**
     * 配置需要跳转的Activity
     */
    private void goToMainActivity() {
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.custermview_activity_in_from_rigth, R.anim.custermview_activity_out_to_scale);
        finish();
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
     * 从网络请求大图片数据插入SD卡中
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
        mDialog.show();
        mHandler.sendEmptyMessage(0);
    }

    //第一次进入
    public void startOnce() {
        tvMsgShow.setVisibility(View.INVISIBLE);
        Animation anim = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.custermview_img_left_out);
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


        Animation navanim = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.custermview_img_right_in);
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

        flag = true;

        //标记为第一次登录
        DButils.putString("once", "Helen");
        //加载一次网络数据
        loadDatas();
    }


    /**
     * 手势检测器的监听方法
     */
    @Override
    public boolean onDown(MotionEvent e) {
        //单击，手指触碰到触摸屏时触发
        if (navImag.getChildCount() - 1 == navImag.getIndex() && flagOnce) {
            goToMainActivity();
            flagOnce = false;
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //短按，手指触碰到触摸屏后，停留一小段时间，触发，如果立刻抬起，不触发
        if (navImag.getChildCount() - 1 == navImag.getIndex() && flagOnce) {
            goToMainActivity();
            flagOnce = false;
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //当单击或者短按后，然后抬起触发，如果单击后执行了长按，滚动，滑动的方法，该方法就不执行了
        if (navImag.getChildCount() - 1 == navImag.getIndex() && flagOnce) {
            goToMainActivity();
            flagOnce = false;
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //滚动，触摸当屏幕以后滑动触发
        if (navImag.getChildCount() - 1 == navImag.getIndex() && flagOnce) {
            goToMainActivity();
            flagOnce = false;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //长按，按住比较长的一段时间
        if (navImag.getChildCount() - 1 == navImag.getIndex() && flagOnce) {
            goToMainActivity();
            flagOnce = false;
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //滑动，在屏幕上有一个快速滚动的操作
        if (e2.getX() - e1.getX() > sensitive) {
            if (flag) {
                //左滑
                aigpic.aboveContent();
                navImag.above();
            }
        } else if (e1.getX() - e2.getX() > sensitive) {
            if (flag) {
                //右滑
                aigpic.nextContent();
                navImag.next();
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PgyUpdateManager.unregister();
    }

    @SuppressLint("HandlerLeak")
    private class InnerHandler extends Handler {
        private ProjectUtil.ToolNetwork mNetwork;

        @SuppressWarnings("static-access")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    tvMsgShow.setText("正在检查网络……");
                    mNetwork = ProjectUtil.ToolNetwork.getInstance();
                    mNetwork.validateNetWork();
                    mHandler.sendEmptyMessageDelayed(1, 500);
                    break;
                case 1:
                    if (mNetwork == null) {
                        finish();
                    }
                    T("当前网络: " + mNetwork.getNetworkType());
                    mHandler.sendEmptyMessageDelayed(2, 1000);
                    break;
                case 2:
                    tvMsgShow.setText("检查版本……");
                    mHandler.sendEmptyMessageDelayed(3, 500);
                    break;
                case 3:
                    tvMsgShow.setText("检查数据库……");
                    mHandler.sendEmptyMessageDelayed(4, 500);
                    break;
                case 4:
                    mDialog.dismiss();
                    // //检测版本更新
                    checkEdition();
                    break;
                case 5:
                    mDialog.dismiss();
                    if (!TextUtils.isEmpty(DButils.getString("once"))) {
                        goToMainActivity();
                    } else {
                        startOnce();
                    }
                    break;
            }
        }
    }

    //检测版本更新
    public void checkEdition() {
        // 版本检测方式2：带更新回调监听
        PgyUpdateManager.register(context, new UpdateManagerListener() {
            @Override
            public void onUpdateAvailable(final String result) {

                final AppBean appBean = getAppBeanFromString(result);
                new android.support.v7.app.AlertDialog.Builder(context)
                        .setMessage("新版本：" + appBean.getVersionCode() + " 是否更新？")
                        .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startDownloadTask(context, appBean.getDownloadURL());
                            }
                        })
                        .setNegativeButton("暂不", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mHandler.sendEmptyMessage(5);
                            }
                        })
                        .setCancelable(false)
                        .create().show();
            }

            @Override
            public void onNoUpdateAvailable() {
                T("已经是最新版本！");
                mHandler.sendEmptyMessage(5);
            }
        });
    }

}
