package com.example.snoy.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snoy.myapplication.Utils.SystemBarUtils;
import com.example.snoy.myapplication.lib.custemview.BufferCircleView;
import com.example.snoy.myapplication.fragmentNav.test1Fragment;
import com.example.snoy.myapplication.fragmentNav.test2Fragment;
import com.example.snoy.myapplication.fragmentNav.test3Fragment;
import com.example.snoy.myapplication.fragmentNav.test4Fragment;
import com.example.snoy.myapplication.fragmentNav.test5Fragment;
import com.example.snoy.myapplication.service.CoreService;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2016/8/23.
 */
public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private FragmentManager fm;

    private FragmentTransaction ft;

    private Context context;

    /***********************************/
    /**
     * 需要配置的东西
     */
    private Fragment[] fragments = {new test1Fragment(), new test2Fragment(), new test3Fragment(), new test4Fragment(), new test5Fragment()};
    private String titles[] = {"首页", "分类", "中心", "积分", "个人"};
    private int[] bg_normal = {R.drawable.commodity_normal, R.drawable.commodity_normal, R.drawable.commodity_normal, R.drawable.commodity_normal, R.drawable.commodity_normal};
    private int[] bg_pressed = {R.drawable.commodity_pressed, R.drawable.commodity_pressed, R.drawable.commodity_pressed, R.drawable.commodity_pressed, R.drawable.commodity_pressed};

    //导航栏的布局ID
    private static final int controlNavID = R.layout.custermview_control_nav;
    //沉侵的颜色  和导航栏颜色
    private static final int color = Color.rgb(228, 238, 249);

    /******************************************/
    private RelativeLayout content;
    //全布局  //头部View
    private LinearLayout head_view, ly_content;
    private TextView tv_left, tv_title, tv_right;
    //底部
    private RadioGroup rgTab;
    private RadioButton[] radioButtons = new RadioButton[fragments.length];
    /*******************************************/
    //设置左边默认图片
    private static final int leftDrawable = R.mipmap.ic_launcher;
    //设置右边默认图片
    private static final int RightDrawable = R.mipmap.ic_launcher;

    /********************************************/
    //判断是否铺满全屏
    private boolean isAllowFullScreen;
    //点击返回键
    private static long mExitTime = 0;
    //点击返回键的时间差
    private static final int EXIT_TIME_GAP = 2000;

    /******************************************/
    // 记录当前radioButtons对应的资源id
    private int currentIndex = 0;

    /******************************************/
    private BufferCircleView bufferCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        context = this;
        setFullScreen(isAllowFullScreen);
        SystemBarUtils.initSystemBarElse(this, color);
        getBuildContentView();
        setContentView(content);
        fm = this.getSupportFragmentManager();
        ft = fm.beginTransaction();

        //设置文字和图片   R.mipmap.ic_launcher为背景选择  自己设
        int count = fragments.length;
        for (int i = 0; i < count; i++) {
            initTabView(radioButtons[i], titles[i], bg_normal[i], bg_pressed[i]);
        }

        //正常启动时调用
        for (int i = 0; i < count; i++) {
            //添加Fragment
            ft.add("Helen".hashCode(), fragments[i], radioButtons[i].getId() + "");
            ft.hide(fragments[i]);
        }
        ft.show(fragments[0]);
        ft.commit();

        //根据文本获取资源ID  第一个 解决初始化hide找不到问题
        currentIndex = getResources().getIdentifier("rb_0", "id", getPackageName());
        radioButtons[0].setSelected(true);
        rgTab.setOnCheckedChangeListener(this);
        initService();
        

    }

    /**
     * 开启服务
     */
    private void initService() {
        Intent intent = new Intent(MainActivity.this, CoreService.class);
        startService(intent);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //上一次点击跟这次点击一样     不做反应
        if (currentIndex == checkedId) return;
        //显示状态
        group.findViewById(checkedId).setSelected(true);
        group.findViewById(currentIndex).setSelected(false);
        ft = fm.beginTransaction();
        //隐藏上一个View
        ft.hide(fm.findFragmentByTag(currentIndex + ""));
        //显示被点击的Fragment
        ft.show(fm.findFragmentByTag(checkedId + ""));
        currentIndex = checkedId;
        ft.commit();

    }


    /**
     * 调整标题对应的文字和图片
     */
    private void initTabView(RadioButton radioButton, String title, int normal, int pressed) {

        radioButton.setText(title);
        radioButton.setBackgroundColor(Color.rgb(228, 238, 249));

        StateListDrawable drawable = new StateListDrawable();
        //Non focused states
        drawable.addState(new int[]{-android.R.attr.state_focused, -android.R.attr.state_selected, -android.R.attr.state_pressed},
                ContextCompat.getDrawable(this, normal));
        drawable.addState(new int[]{-android.R.attr.state_focused, android.R.attr.state_selected, -android.R.attr.state_pressed},
                ContextCompat.getDrawable(this, pressed));
        //Focused states
        drawable.addState(new int[]{android.R.attr.state_focused, -android.R.attr.state_selected, -android.R.attr.state_pressed},
                ContextCompat.getDrawable(this, normal));
        drawable.addState(new int[]{android.R.attr.state_focused, android.R.attr.state_selected, -android.R.attr.state_pressed},
                ContextCompat.getDrawable(this, pressed));
        //Pressed
        drawable.addState(new int[]{android.R.attr.state_selected, android.R.attr.state_pressed},
                ContextCompat.getDrawable(this, normal));
        drawable.addState(new int[]{android.R.attr.state_pressed},
                ContextCompat.getDrawable(this, pressed));
        drawable.setBounds(0, 0, 80, 80);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        radioButton.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
    }


    /**
     * 添加布局
     */
    private void getBuildContentView() {
        //总布局
        content = new RelativeLayout(this);
        LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        content.setLayoutParams(rl);

        //创建一个总布局
        ly_content = new LinearLayout(this);
        ly_content.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ly_content.setOrientation(LinearLayout.VERTICAL);
        ly_content.setClipToPadding(true);
        ly_content.setFitsSystemWindows(true);
        content.addView(ly_content);

        //创建一个头部View 高度为44
        head_view = new LinearLayout(this);
        head_view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dip2px(this, 44)));
        head_view.setBackgroundColor(color);
        ly_content.addView(head_view);

        //添加左边的按钮
        tv_left = new TextView(this);
        LinearLayout.LayoutParams btnLeftLayoutParams = new LinearLayout.LayoutParams(dip2px(this, 44), LinearLayout.LayoutParams.MATCH_PARENT);
        tv_left.setLayoutParams(btnLeftLayoutParams);
        tv_left.setBackgroundColor(color);
        tv_left.setGravity(Gravity.CENTER);
        tv_left.setPadding(dip2px(this, 10), dip2px(this, 10), dip2px(this, 10), dip2px(this, 10));
        Drawable drawableLeft = ContextCompat.getDrawable(this, leftDrawable);
        assert drawableLeft != null;
        drawableLeft.setBounds(0, 0, dip2px(this, 30), dip2px(this, 30));//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        tv_left.setCompoundDrawables(null, drawableLeft, null, null);//只放上边
        head_view.addView(tv_left);

        //添加中间的文本
        tv_title = new TextView(this);
        LinearLayout.LayoutParams TextViewLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        TextViewLayoutParams.setMargins(dip2px(this, 10), dip2px(this, 5), dip2px(this, 10), dip2px(this, 5));
        tv_title.setLayoutParams(TextViewLayoutParams);
        tv_title.setBackgroundColor(color);
        tv_title.setGravity(Gravity.CENTER);
        tv_title.setSingleLine();
        tv_title.setText("中间的标题");
        tv_title.setTextSize(sp2px(this, 16));
        head_view.addView(tv_title);

        //添加右边的按钮
        tv_right = new TextView(this);
        LinearLayout.LayoutParams btnRightLayoutParams = new LinearLayout.LayoutParams(dip2px(this, 44), LinearLayout.LayoutParams.MATCH_PARENT);
        tv_right.setLayoutParams(btnRightLayoutParams);
        tv_right.setBackgroundColor(color);
        tv_right.setGravity(Gravity.CENTER);
        tv_right.setPadding(dip2px(this, 10), dip2px(this, 10), dip2px(this, 10), dip2px(this, 10));
        Drawable drawableRight = ContextCompat.getDrawable(this, RightDrawable);
        assert drawableRight != null;
        drawableRight.setBounds(0, 0, dip2px(this, 30), dip2px(this, 30));//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        tv_right.setCompoundDrawables(null, drawableRight, null, null);//只放上边
        head_view.addView(tv_right);

        //添加用来替换的帧布局
        FrameLayout frameLayout = new FrameLayout(this);
        //创建中间的帧布局用来被替换  比例为1
        LinearLayout.LayoutParams viewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        frameLayout.setLayoutParams(viewLayoutParams);
        frameLayout.setId("Helen".hashCode());
        ly_content.addView(frameLayout);

        //底部标题栏
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RadioGroup control_nav = (RadioGroup) inflater.inflate(controlNavID, null);
        LinearLayout.LayoutParams controlNavLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dip2px(this, 49));
        control_nav.setLayoutParams(controlNavLayoutParams);
        /**
         * 根据名字动态获取资源
         */
        for (int i = 0; i < radioButtons.length; i++) {
            int resId = getResources().getIdentifier("rb_" + i, "id", getPackageName());
            radioButtons[i] = (RadioButton) control_nav.findViewById(resId);
        }
        rgTab = (RadioGroup) control_nav.findViewById(R.id.rg_tab);
        ly_content.addView(control_nav);


        //添加一层加载中的View
        bufferCircleView = new BufferCircleView(context);
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        bufferCircleView.setLayoutParams(ll);
        bufferCircleView.setVisibility(View.GONE);
        content.addView(bufferCircleView);
    }

    /**
     * 处理返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (SystemClock.uptimeMillis() - mExitTime > EXIT_TIME_GAP) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = SystemClock.uptimeMillis();
            } else {
                //发送广播发送给没finish()的Activity
                exitApp();
                MainActivity.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出应用程序的方法，发送退出程序的广播
     */
    private void exitApp() {
        Intent intent = new Intent();
        intent.setAction("net.loonggg.exitapp");
        this.sendBroadcast(intent);
    }


    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 判断是否铺满全屏
     */
    public void setFullScreen(boolean fullScreen) {
        if (fullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            this.getWindow().setBackgroundDrawable(null);
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setBackgroundDrawable(null);
        }
    }


    /***********************************************************************/

    /**
     * 获取加载的View
     */
    public BufferCircleView getLoding() {
        return bufferCircleView;
    }

    /**
     * 得到左边的按钮
     */
    public TextView getLeftBtn() {
        return tv_left;
    }

    /**
     * 得到中间的TextView
     */
    public TextView getCenterView() {
        return tv_title;
    }


    /**
     * 得到右边的按钮
     */
    public TextView getRightBtn() {
        return tv_right;
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        if (null != tv_title) {
            tv_title.setText(title);
        }
    }

    /**
     * 设置左边按钮的图片资源
     */
    public void setLeftRes(int resId) {
        if (null != tv_left) {
            Drawable drawableRight = ContextCompat.getDrawable(this, resId);
            assert drawableRight != null;
            drawableRight.setBounds(0, 0, 80, 80);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
            tv_left.setCompoundDrawables(null, drawableRight, null, null);//只放上边
        }
    }

    /**
     * 设置左边按钮的图片资源
     */
    public void setRightRes(int resId) {
        if (null != tv_right) {
            Drawable drawableRight = ContextCompat.getDrawable(this, resId);
            assert drawableRight != null;
            drawableRight.setBounds(0, 0, 80, 80);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
            tv_right.setCompoundDrawables(null, drawableRight, null, null);//只放上边
        }
    }

    /**
     * 隐藏上方的标题栏
     */
    public void hideHeadView() {
        if (null != head_view) {
            head_view.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏左边的按钮
     */
    public void hidebtn_left() {
        if (null != tv_left) {
            tv_left.setVisibility(View.GONE);
        }
    }

    /***
     * 隐藏右边的按钮
     */
    public void hidebtn_right() {
        if (null != tv_right) {
            tv_right.setVisibility(View.GONE);
        }
    }

    /**
     * 显示上方的标题栏
     */
    public void showHeadView() {
        if (null != head_view) {
            head_view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示左边的按钮
     */
    public void showbtn_left() {
        if (null != tv_left) {
            tv_left.setVisibility(View.VISIBLE);
        }

    }

    /***
     * 显示右边的按钮
     */
    public void showbtn_right() {
        if (null != tv_right) {
            tv_right.setVisibility(View.VISIBLE);
        }

    }

    /***********************************************************************/
    /**
     * 跳转到另一个Activity，不携带数据，不设置flag
     */
    public void goToActivityByClass(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    /**
     * 跳转到另一个Activity，携带数据
     */
    public void goToActivityByClass(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    /**
     * 延迟去往新的Activity
     */
    public void delayToActivity(final Class<?> cls, long delay) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                context.startActivity(new Intent(context, cls));
            }
        }, delay);
    }

    /**
     * 当前的Activity可见
     */
    @Override
    public void onResume() {
        super.onResume();
        bufferCircleView.startAnimation();
    }

    /**
     * 当前的Activity不可见
     */
    @Override
    protected void onPause() {
        super.onPause();
        bufferCircleView.stopAnimation();
    }
}

