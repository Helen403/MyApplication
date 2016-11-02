package com.example.snoy.myapplication.lib.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.lib.Utils.CipherUtils;
import com.example.snoy.myapplication.lib.Utils.DateUtils;
import com.example.snoy.myapplication.lib.Utils.ImageUtils;
import com.example.snoy.myapplication.lib.custemview.BufferCircleView;
import com.example.snoy.myapplication.lib.custemview.MyNetFailView;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by SNOY on 2016/8/20.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    public static BaseActivity context;
    //配置一下
    protected Context contextAppliction = BaseApplication.context;
    /******************************************/
    //沉侵的颜色  和导航栏颜色
    private static final int color = Color.rgb(228, 238, 249);
    /*************************************/
    protected RelativeLayout content;
    //状态栏
    protected ImageView status;
    //全布局
    protected LinearLayout ly_content;
    // 外界传入内容区域的布局
    protected ViewGroup contentView;
    //头部View
    protected RelativeLayout head_view;
    protected TextView tv_title, tv_right;
    protected ImageView tv_left;
    /*******************************************/
    //设置左边默认图片
    protected static final int leftDrawable = R.mipmap.ic_launcher;
    //设置右边默认图片
    private static final int RightDrawable = R.mipmap.ic_launcher;
    /*******************************************/
    //加载中的View
    protected BufferCircleView bufferCircleView;
    //网络失败的View
    protected MyNetFailView myNetFailView;
    /******************************************/
    protected LayoutInflater inflater;
    /*****************************************/
    //特殊提供节约空间的方法
    protected TextView tv[];
    protected ImageView iv[];
    //特定的
    protected int tvId[];
    protected int ivId[];
    /*****************************************/
    protected FragmentManager fm;
    protected FragmentTransaction ft;
    //跳转的id
    protected int fragmentId;
    //存放子Fragment跳转的集合
    protected ArrayList<BaseFragment> fragmentList = new ArrayList<>();
    //跳转的记录标记
    protected int indexFragment = 0;
    /*********************************************/
    //从外界传入的广播
    private BroadcastReceiver broadcastReceiver;
    //加载更多
    protected int page = 1;
    protected int rows = 10;


    /**
     * 关闭Activity的广播，放在自定义的基类中，让其他的Activity继承这个Activity就行
     */
    protected BroadcastReceiver finishAppReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("net.loonggg.exitapp")) {
                finish();
            }
        }
    };

    /****************************************************************************************************/

    /**
     * 广播注册类名的方法
     * 使用要响应的Activity类名为Action
     */
    private void setBroadCast(Class<?> cls, String action, BroadcastReceiver broadcastReceiver) {
        this.broadcastReceiver = broadcastReceiver;
        IntentFilter filter = new IntentFilter();
        if (cls != null) {
            filter.addAction(cls.getCanonicalName());
        }
        if (!TextUtils.isEmpty(action)) {
            filter.addAction(action);
        }
        this.registerReceiver(broadcastReceiver, filter);
    }

    /**
     * 广播特定类方法
     */
    protected void setBroadCast(Class<?> cls, BroadcastReceiver broadcastReceiver) {
        setBroadCast(cls, "", broadcastReceiver);
    }

    /**
     * 广播特定字符方法
     */
    protected void setBroadCast(String action, BroadcastReceiver broadcastReceiver) {
        setBroadCast(null, action, broadcastReceiver);
    }

    /**
     * 发送广播信号 自己选择类方法或者字符方法
     */
    private void onSendBroadCast(Class<?> cls, String action, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null)
            intent.putExtras(bundle);
        if (cls != null) {
            intent.setAction(cls.getCanonicalName());
        }
        if (!TextUtils.isEmpty(action)) {
            intent.setAction(action);
        }
        this.sendBroadcast(intent);
    }


    /**
     * 发送广播特定的类方法
     */
    protected void onSendBroadCast(Class<?> cls, Bundle bundle) {
        onSendBroadCast(cls, "", bundle);
    }

    /**
     * 发送广播特定的字符方法
     */
    protected void onSendBroadCast(String action, Bundle bundle) {
        onSendBroadCast(null, action, bundle);
    }

    /****************************************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PgyCrashManager.register(this);
        PgyFeedbackShakeManager.register(this, true);
        context = this;
        fm = this.getSupportFragmentManager();
        ft = fm.beginTransaction();
        dealLogicBeforeFindView();
        setFullScreen();
        getBuildContentView();
        onShowMessage(content);
        setContentView(content);
        setBack();
        //注册广播
        setBroadCastFinish();
        //检测网络状态
        checkNet();
    }


    /**
     * 设置结束当前Activity广播
     */
    private void setBroadCastFinish() {
        // 在当前的activity中注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("net.loonggg.exitapp");
        this.registerReceiver(this.finishAppReceiver, filter);
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
     * 特殊提供用于切换子Fragment的方法
     */
    protected void onFragmentChange(int fragmentId) {
        this.fragmentId = fragmentId;
    }


    /**
     * 检测网络状态
     */
    private void checkNet() {
        if (isConnected()) {
            myNetFailView.setVisibility(View.GONE);
            findViews();
            fillView();
            onFragmentChange(0);
            initData();
            setListeners();
            onAttachMyRecycleViewAdapter();
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
        content.addView(ly_content);

        status = new ImageView(this);
        status.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dip2px(this, 20)));
        status.setBackgroundColor(color);
        ly_content.addView(status);

        //创建一个头部View 高度为44
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        head_view = (RelativeLayout) inflater.inflate(R.layout.custermview_head_view, ly_content, false);
        head_view.setBackgroundColor(color);
        ly_content.addView(head_view);

        //添加左边的按钮
        tv_left = (ImageView) head_view.findViewById(R.id.tv_left);

        //添加中间的文本
        tv_title = (TextView) head_view.findViewById(R.id.tv_title);
        tv_title.setGravity(Gravity.CENTER);
        tv_title.setText("中间的标题");
        tv_title.setTextSize(18);
        tv_title.setTextColor(Color.WHITE);

        //添加右边的按钮
        tv_right = (TextView) head_view.findViewById(R.id.tv_right);
        Drawable drawableRight = ContextCompat.getDrawable(this, RightDrawable);
        assert drawableRight != null;
        drawableRight.setBounds(0, 0, dip2px(this, 30), dip2px(this, 30));//第一0是距左边距离，第二0是距上边距离，40分别是长宽
//        tv_right.setCompoundDrawables(null, drawableRight, null, null);//只放上边

        tv_right.setTextColor(Color.WHITE);


        //从外面传来的View添加进入
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


    protected void dealLogicBeforeFindView() {
    }

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
     * 显示上方的标题栏
     */
    public void showHeadView() {
        if (null != head_view) {
            head_view.setVisibility(View.VISIBLE);
        }
    }


    /***********************************************************************/

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
    public void setFullScreen() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = this.getWindow();
        //设置透明状态栏,这样才能让 ContentView 向上
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }
        this.getWindow().setBackgroundDrawable(null);
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

    /*********************************************************************************************/

    /**
     * 带返回结果的跳转
     */
//    protected SparseArray<ActivityResultAction> mResultHandlers;
//
//    public void goActivityForResult(Context context, Class<?> cls, Bundle bundle, ActivityResultAction action) {
//        Intent intent = new Intent(context, cls);
//        if (bundle != null) {
//            intent.putExtras(bundle);
//        }
//        int rc;
//        if (action != null) {
//            if (mResultHandlers == null) {
//                mResultHandlers = new SparseArray<ActivityResultAction>();
//            }
//            rc = action.hashCode();
//            rc &= 0x0000ffff;
//            mResultHandlers.append(rc, action);
//            startActivityForResult(intent, rc);
//        } else {
//            startActivity(intent);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        ActivityResultAction activityResultAction = mResultHandlers.get(requestCode);
//        if (null != activityResultAction) {
//            activityResultAction.invoke(resultCode, data);
//        }
//    }
//
//    public abstract class ActivityResultAction {
//        private void invoke(Integer resultCode, Intent data) {
//            switch (resultCode.intValue()) {
//                case Activity.RESULT_OK:
//                    onSuccess(data);
//                    break;
//                case Activity.RESULT_CANCELED:
//                    onCancel();
//                    break;
//                default:
//                    break;
//            }
//        }
//
//        public abstract void onSuccess(Intent data);
//
//        protected abstract void onCancel();
//    }


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
        PgyCrashManager.unregister();
        if (this.broadcastReceiver != null) {
            this.unregisterReceiver(this.broadcastReceiver);
        }
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

    /********************************************************************************/
    /**
     * sumScale  总的比例   以竖屏为参考  屏幕宽为比例总
     * ScaleW    宽占总的比例多少
     * ScaleH    高占总的比例多少
     */
    public void setScaleView(View view, int sumScale, int ScaleW, int ScaleH) {
        //屏幕的宽
        int screenW = 0;
        //屏幕的高
        int screenH = 0;
        WindowManager wm = (WindowManager) contextAppliction.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        screenW = display.getWidth();
        screenH = display.getHeight();
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) view.getLayoutParams();
        /************************************************/
        int tmpW = ScaleW / sumScale * screenW;
        int tmpH = ScaleH / sumScale * screenW;
        params.width = tmpW;
        params.height = tmpH;
        view.setLayoutParams(params);
    }


    /**********************************************************************************/


    /**
     * 显示popupWindow
     * parent    需要挂上的View
     * showView  需要显示的View
     */
    public PopupWindow showPopupWindow(View parent, View showView) {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int screenW = windowManager.getDefaultDisplay().getWidth();
        int screenH = windowManager.getDefaultDisplay().getHeight();
        PopupWindow popupWindow = new PopupWindow(showView, screenW, screenH);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(parent, 0, 0);
        return popupWindow;
    }


    /*************************************************************************************/

    /**
     * 高德地图导航,坐标要转换
     */
    private void openGaodeNavi() {
//    double[] lon = bdToGaoDe(storeInfo.latitude, storeInfo.longitude);
//        try {
//            String address = "androidamap://viewMap?sourceApplication="
//                    + storeInfo.businessName + "&poiname=" + storeInfo.address
//                    + "&lat=" + lon[1] + "&lon="
//                    + lon[0] + "&dev=0";
//            Intent intent = Intent.getIntent(address);
//            if (isInstallByread("com.autonavi.minimap")) {
//                startActivity(intent); // 启动调用
//                navipopupWindow.dismiss();
//            } else {
//                T("未发现高德地图");
//            }
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }

    }

    /**
     * 打开百度地图导航
     */
    private void openBaiduNavi() {
//        try {
//            String address = "intent://map/marker?location="
//                    + storeInfo.latitude + "," + storeInfo.longitude
//                    + "&title=" + storeInfo.businessName + "&content="
//                    + storeInfo.address
//                    + "&src=yourCompanyName|yourAppName#Intent;"
//                    + "scheme=bdapp;package=com.baidu.BaiduMap;end";
//            Intent intent = Intent.getIntent(address);
//            if (isInstallByread("com.baidu.BaiduMap")) {
//                startActivity(intent); // 启动调用
//                navipopupWindow.dismiss();
//                // Log.v("GasStation", "百度地图客户端已经安装");
//            } else {
//                // Log.v("GasStation", "没有安装百度地图客户端");
//                ToastUtils.ToastShowShort(mContext, "未发现百度地图");
//            }
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }

    }

    /**
     * bd09转成GCJ-02
     */
    private double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;
    }

    /**
     * GCJ-02转成bd09
     */
    private double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }

    /**
     * 判断地图客户端是否安装
     */
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }


    //生成密匙
    public String getMd5Password(String str) {

        //第几天
        int i = DateUtils.getDayOfWeek();

        String week = "";
        //星期一
        if (i == 1) {
            week = "Mon";
        } else if (i == 2) {
            week = "Tue";
        } else if (i == 3) {
            week = "Wed";
        } else if (i == 4) {
            week = "Thu";
        } else if (i == 5) {
            week = "Fri";
        } else if (i == 6) {
            week = "Sat";
        } else if (i == 7) {
            week = "Sun";
        }
        //获取当前年
        int month = Integer.parseInt(DateUtils.formatDataMonth(System.currentTimeMillis()));
        String mon = "";
        switch (month) {
            case 1:
                mon = "Jan";
                break;
            case 2:
                mon = "Feb";
                break;
            case 3:
                mon = "Mar";
                break;
            case 4:
                mon = "Apr";
                break;
            case 5:
                mon = "May";
                break;
            case 6:
                mon = "Jun";
                break;
            case 7:
                mon = "Jul";
                break;
            case 8:
                mon = "Aug";
                break;
            case 9:
                mon = "Sep";
                break;
            case 10:
                mon = "Oct";
                break;
            case 11:
                mon = "Nov";
                break;
            case 12:
                mon = "Dec";
                break;
        }

        String time = getTime();
        if (time.length() == 1) {
            time = "0" + time;
        }

        //先弄D和H的和
        String password = CipherUtils.md5L(CipherUtils.md5L(week + time) + str + CipherUtils.md5L(DateUtils.formatDataYear(System.currentTimeMillis()) + mon));
        return password;
    }

    /**
     * getTime 获取系统时间
     */
    public static String getTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return cal.get(Calendar.HOUR_OF_DAY) + "";
    }


}
