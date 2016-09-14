package com.example.snoy.myapplication.lib.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.lib.Utils.ImageUtils;
import com.example.snoy.myapplication.lib.Utils.SystemBarUtils;
import com.example.snoy.myapplication.lib.custemview.BufferCircleView;
import com.example.snoy.myapplication.lib.custemview.MyNetFailView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by SNOY on 2016/8/20.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    //配置一下
    protected Context contextAppliction = BaseApplication.context;
    /******************************************/
    //沉侵的颜色  和导航栏颜色
    private static final int color = Color.rgb(228, 238, 249);
    /*************************************/
    protected RelativeLayout content;
    //全布局
    protected LinearLayout ly_content;
    // 外界传入内容区域的布局
    protected ViewGroup contentView;
    //头部View
    protected LinearLayout head_view;
    protected TextView tv_left, tv_title, tv_right;
    /*******************************************/
    //设置左边默认图片
    protected static final int leftDrawable = R.mipmap.ic_launcher;
    //设置右边默认图片
    private static final int RightDrawable = R.mipmap.ic_launcher;
    /*******************************************/
    //判断是否铺满全屏
    protected boolean isAllowFullScreen;

    /*******************************************/
    //加载中的View
    protected BufferCircleView bufferCircleView;

    //网络失败的View
    protected MyNetFailView myNetFailView;

    /******************************************/
    protected LayoutInflater inflater;
    /*****************************************/
    protected TextView tv[];
    protected ImageView iv[];

    protected int tvId[];
    protected int ivId[];
    /*****************************************/
    protected FragmentManager fm;
    protected FragmentTransaction ft;
    //跳转的id
    protected int fragmentId;
    //存放子Fragment跳转的集合
    protected ArrayList<BaseFragment> fragmentList = new ArrayList<>();
    //跳转的记录
    protected int indexFragment = 0;
    /*********************************************/
    //广播注册
    protected IntentFilter filter = new IntentFilter();

    /**
     * 关闭Activity的广播，放在自定义的基类中，让其他的Activity继承这个Activity就行
     */
    protected BroadcastReceiver finishAppReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("net.loonggg.exitapp")) {
                finish();
            } else {
                onReceiveByBroadcast(context, intent);
            }
        }
    };

    /**
     * 广播注册类名的方法
     * 使用要响应的Activity类名为Action
     */
    protected void setBroadCast(Class<?> cls) {
        String actionTmp = cls.getCanonicalName();
        filter.addAction(actionTmp);
    }

    /**
     * 广播回来的数据
     */
    protected void onReceiveByBroadcast(Context context, Intent intent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = this.getSupportFragmentManager();
        dealLogicBeforeFindView();
        setFullScreen(isAllowFullScreen);
        SystemBarUtils.initSystemBarElse(this, color);
        getBuildContentView();
        onShowMessage(content);
        setContentView(content);
        onAttachMyRecycleViewAdapter();
        setBack();
        //检测网络状态
        checkNet();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void setBroadCastFinish() {
        // 在当前的activity中注册广播
        filter.addAction("net.loonggg.exitapp");
        this.registerReceiver(this.finishAppReceiver, filter);
    }

    protected void sendBroadCast(Class<?> cls) {
        Intent intent = new Intent();
        intent.setAction(cls.getCanonicalName());
        this.sendBroadcast(intent);
    }


    /**
     * 添加自定义的遮盖提示
     * 特殊提供
     */
    protected void onShowMessage(RelativeLayout relativeLayout) {
    }


    /**
     * 解决MyRecyclerViewBug
     * findViewById  也是在这里操作
     * 特殊提供
     */
    protected void onAttachMyRecycleViewAdapter() {
    }

    /**
     * 检测网络状态
     */
    private void checkNet() {
        if (isConnected()) {
            myNetFailView.setVisibility(View.GONE);
            findViews();
            fillView();
            initData();
            setListeners();

            //注册广播
            setBroadCastFinish();
        } else {
            myNetFailView.setVisibility(View.VISIBLE);
        }
    }

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
        tv_title.setText("中间的标题");
        tv_title.setTextSize(16);
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

        //从外面传来的View添加进入
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout.LayoutParams viewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        contentView = (ViewGroup) inflater.inflate(getContentView(), content, false);
        contentView.setLayoutParams(viewLayoutParams);
        ly_content.addView(contentView);

        //添加一层加载中的View
        bufferCircleView = new BufferCircleView(contextAppliction);
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        bufferCircleView.setLayoutParams(ll);
        bufferCircleView.setVisibility(View.GONE);
        content.addView(bufferCircleView);

        //加载断网的View
        myNetFailView = new MyNetFailView(contextAppliction);
        LinearLayout.LayoutParams llNet = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        myNetFailView.setLayoutParams(llNet);
        ly_content.addView(myNetFailView);
    }


    public abstract void dealLogicBeforeFindView();

    /***
     * 设置内容区域
     */
    public abstract int getContentView();

    public abstract void findViews();

    public abstract void initData();

    public abstract void setListeners();

    /******************************************************************/
    /**
     * 处理点击返回按钮--------退出当前的Activity 返回到上一个Activity
     */
    private void setBack() {
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //点击重新检测网络
        myNetFailView.setOnrefreshCallBack(new MyNetFailView.OnMyNetFailViewClick() {
            @Override
            public void onClick() {
                checkNet();
            }
        });
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

    public BaseActivity() {
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
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }


    /***********************************************************************/
    /**
     * 跳转到另一个Activity，不携带数据，不设置flag
     */
    public void goToActivityByClass(Context context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    /**
     * 跳转到另一个Activity，携带数据
     */
    public void goToActivityByClass(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    /**
     * 延迟去往新的Activity
     */
    public void delayToActivity(final Context context, final Class<?> cls, long delay) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                context.startActivity(new Intent(context, cls));
            }
        }, delay);
    }


    /*******************************************************************/
    /**
     * 设置文本数据
     */
    public void setText(String text, int resId) {
        TextView view = (TextView) findViewById(resId);
        view.setText(text);
    }

    /**
     * 设置图片数据  使用自己定义的图片加载器
     */
    public void setImageByUrl(String url, int resId) {
        ImageView imageView = (ImageView) findViewById(resId);
        ImageUtils.getInstance().setImageByUrl(url, imageView);
    }

    /**
     * 设置文本数据
     */
    public void setText(View view, String text, int resId) {
        TextView textView = (TextView) view.findViewById(resId);
        textView.setText(text);
    }

    /**
     * 设置图片数据  使用自己定义的图片加载器
     */
    public void setImageByUrl(View view, String url, int resId) {
        ImageView imageView = (ImageView) view.findViewById(resId);
        ImageUtils.getInstance().setImageByUrl(url, imageView);
    }


    /*********************************************************************/
    //配合自动生成的方法
    public void setImageByUrl(String url, ImageView imageView) {
        ImageUtils.getInstance().setImageByUrl(url, imageView);
    }

    /*********************************************************************/

    /**
     * 监听返回键 取消正在加载中
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && bufferCircleView.isVisibility()) {
            bufferCircleView.hide();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /*********************************************************************/
    @Override
    public void onResume() {
        super.onResume();

        bufferCircleView.startAnimation();
    }

    /**
     * 当前的Activity不可见  取消正在下载的任务
     * 隐藏加载中的
     */
    @Override
    protected void onPause() {
        super.onPause();
        ImageUtils.getInstance().cancelTask();
        bufferCircleView.stopAnimation();
    }

    /**
     * 就算当前的Activity finish() 内存自动回收机制也不会马上回收资源
     * 所以哥强制性的回收资源  别恋哥  哥只是哥传说
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(this.finishAppReceiver);
        //感觉不靠谱加多一句
        System.gc();
    }

    /*********************************************************************/
    public void T(String msg) {
        Toast.makeText(contextAppliction, msg, Toast.LENGTH_SHORT).show();
    }

    public void T(float msg) {
        Toast.makeText(contextAppliction, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(double msg) {
        Toast.makeText(contextAppliction, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(int msg) {
        Toast.makeText(contextAppliction, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(boolean msg) {
        Toast.makeText(contextAppliction, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void L(String msg) {
        Log.d("Helen", msg);
    }

    public void L(float msg) {
        Log.d("Helen", msg + "");
    }

    public void L(double msg) {
        Log.d("Helen", msg + "");
    }

    public void L(int msg) {
        Log.d("Helen", msg + "");
    }

    public void L(boolean msg) {
        Log.d("Helen", msg + "");
    }


    /*************************************************************************/
    /**
     * 判断网络是否连接
     */
    public boolean isConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) contextAppliction
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }


    /****************************************************************************************/
    /**
     * 寻找特定规则的ImageView，TextView 填充数组
     */
    private void fillView() {
        ArrayList<TextView> textViews = new ArrayList<>();
        ArrayList<ImageView> imageViews = new ArrayList<>();
        if (tvId != null) {
            int countTv = tvId.length;
            for (int i = 0; i < countTv; i++) {
                TextView tvTmp = (TextView) contentView.findViewById(tvId[i]);
                textViews.add(tvTmp);
            }
            tv = textViews.toArray(new TextView[countTv]);
        }
        if (ivId != null) {
            int countIv = ivId.length;
            for (int i = 0; i < countIv; i++) {
                ImageView ivTmp = (ImageView) contentView.findViewById(ivId[i]);
                imageViews.add(ivTmp);
            }
            iv = imageViews.toArray(new ImageView[countIv]);
        }
    }

    /****************************************************************************************/

    /**
     * 内部嵌入Fragment 转换  隐藏当前Fragment
     */
    public void switchFragment(int checkIndex) {
        ft = fm.beginTransaction();
        Fragment currentFragment = fragmentList.get(indexFragment);
        Fragment targetFragment = fragmentList.get(checkIndex);
        if (currentFragment != targetFragment) {
            if (!targetFragment.isAdded()) {
                ft.hide(currentFragment).add(fragmentId, targetFragment);
            } else {
                ft.hide(currentFragment).show(targetFragment);
            }
        } else {
            if (!targetFragment.isAdded()) {
                ft.add(fragmentId, targetFragment).show(targetFragment);
            }
        }
        ft.commit();
        indexFragment = checkIndex;
    }

    /*************************************************************************/
    /**
     * 提供特殊寻找的方法
     */
    protected <T extends View> T getViewById(int id) {
        return (T) findViewById(id);
    }


    /**
     * 添加点击事件
     */
    protected void setOnListeners(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    onClick click;

    public void setOnClick(onClick click) {
        this.click = click;
    }

    public interface onClick {
        void onClick(View v, int id);
    }

    @Override
    public void onClick(View v) {
        click.onClick(v, v.getId());
    }

    /******************************************************************************/
    /**
     * 通过反射获取资源 R.id
     * 根据给定的类型名和字段名，返回R文件中的字段的值
     *
     * @param typeName  属于哪个类别的属性 （id,layout,drawable,string,color,attr......）
     * @param fieldName 字段名
     * @return 字段的值
     */
    public int getFieldValue(String typeName, String fieldName, Context context) {
        int i;
        try {
            Class<?> clazz = Class.forName(context.getPackageName() + ".R$" + typeName);
            i = clazz.getField(fieldName).getInt(null);
        } catch (Exception e) {
            return -1;
        }
        return i;
    }


}
