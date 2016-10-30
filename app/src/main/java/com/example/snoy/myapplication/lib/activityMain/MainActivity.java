package com.example.snoy.myapplication.lib.activityMain;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.fragmentNav.test1Fragment;
import com.example.snoy.myapplication.fragmentNav.test2Fragment;
import com.example.snoy.myapplication.fragmentNav.test3Fragment;
import com.example.snoy.myapplication.fragmentNav.test4Fragment;
import com.example.snoy.myapplication.fragmentNav.test5Fragment;
import com.example.snoy.myapplication.lib.base.BaseActivity;
import com.example.snoy.myapplication.service.CoreService;


/**
 * Created by Administrator on 2016/8/23.
 */
public final class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    /**
     * 需要配置的东西
     */
    private Fragment[] fragments = {new test1Fragment(), new test2Fragment(), new test3Fragment(), new test4Fragment(), new test5Fragment()};
    private String titles[] = {"首页", "分类", "中心", "积分", "个人"};
    private int[] bg_normal = {R.mipmap.custermview_commodity_normal, R.mipmap.custermview_commodity_normal, R.mipmap.custermview_commodity_normal, R.mipmap.custermview_commodity_normal, R.mipmap.custermview_commodity_normal};
    private int[] bg_pressed = {R.mipmap.custermview_commodity_pressed, R.mipmap.custermview_commodity_pressed, R.mipmap.custermview_commodity_pressed, R.mipmap.custermview_commodity_pressed, R.mipmap.custermview_commodity_pressed};
    /******************************************/
    //底部
    private RadioGroup rgTab;
    private RadioButton[] radioButtons = new RadioButton[fragments.length];
    /********************************************/
    //点击返回键
    private static long mExitTime = 0;
    //点击返回键的时间差
    private static final int EXIT_TIME_GAP = 2000;
    /******************************************/
    // 记录当前radioButtons对应的资源id
    private int currentIndex = 0;

    @Override
    public int getContentView() {
        return R.layout.custermview_control_nav;
    }

    @Override
    public void findViews() {
        /**
         * 根据名字动态获取资源
         */
        for (int i = 0; i < radioButtons.length; i++) {
            int resId = getResources().getIdentifier("rb_" + i, "id", getPackageName());
            radioButtons[i] = (RadioButton) findViewById(resId);
        }
        rgTab = (RadioGroup) findViewById(R.id.rg_tab);
    }

    @Override
    public void initData() {
        //设置文字和图片   R.mipmap.ic_launcher为背景选择  自己设
        int count = fragments.length;
        for (int i = 0; i < count; i++) {
            initTabView(radioButtons[i], titles[i], bg_normal[i], bg_pressed[i]);
        }

        //正常启动时调用
        for (int i = 0; i < count; i++) {
            //添加Fragment
            ft.add(R.id.main_activity_id, fragments[i], radioButtons[i].getId() + "");
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

    @Override
    public void setListeners() {

    }

    /****************************************************************************************/


    /**
     * 开启服务
     */
    private void initService() {
        Intent intent = new Intent(MainActivity.this, CoreService.class);
        startService(intent);
    }

    /****************************************************************************************/
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

    /****************************************************************************************/
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

    /****************************************************************************************/
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


}

