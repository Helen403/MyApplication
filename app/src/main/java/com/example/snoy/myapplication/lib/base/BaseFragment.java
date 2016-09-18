package com.example.snoy.myapplication.lib.base;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snoy.myapplication.lib.activityMain.MainActivity;
import com.example.snoy.myapplication.lib.Utils.ImageUtils;
import com.example.snoy.myapplication.lib.custemview.BufferCircleView;
import com.example.snoy.myapplication.lib.custemview.MyNetFailView;

import java.util.ArrayList;


/**
 * Created by mcs on 2015/11/3.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    //外面传入的View
    protected View contentView;
    //网络失败的View
    protected MyNetFailView myNetFailView;
    //总布局
    protected RelativeLayout content;
    //填充器
    LayoutInflater inflater;

    /*****************************************/
    //用于特殊节约空间的
    protected TextView tv[];
    protected ImageView iv[];

    protected int tvId[];
    protected int ivId[];

    /*****************************************/
    //从外界传入的广播
    private BroadcastReceiver broadcastReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        dealLogicBeforeFindView();
        //总布局
        content = new RelativeLayout(getActivity());
        LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        content.setLayoutParams(rl);
        //外面传入的View
        contentView = inflater.inflate(getContentView(), content, false);
        if (getArguments() != null)
            onGetBundle(getArguments());
        contentView.setClickable(true);
        content.addView(contentView);
        //加载无网络的View
        myNetFailView = new MyNetFailView(getActivity());
        LinearLayout.LayoutParams llNet = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        myNetFailView.setLayoutParams(llNet);
        content.addView(myNetFailView);
        onShowMessage(content);
        return content;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClick();
        onAttachMyRecycleViewAdapter();
        //检测网络状态
        checkNet();
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
        } else {
            myNetFailView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 处理点击返回按钮--------退出当前的Activity 返回到上一个Activity
     */
    private void setClick() {
        //点击重新检测网络
        myNetFailView.setOnrefreshCallBack(new MyNetFailView.OnMyNetFailViewClick() {
            @Override
            public void onClick() {
                checkNet();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        this.setRetainInstance(true);
    }

    protected void dealLogicBeforeFindView() {
    }

    /***
     * 给调用者
     */
    public abstract int getContentView();

    public abstract void findViews();

    public abstract void initData();

    public abstract void setListeners();

    /************************************************************************/

    /**
     * 获取加载的图片
     */
    protected BufferCircleView getLoading() {
        BufferCircleView bufferCircleView = null;
        if (getActivity() instanceof MainActivity) {
            bufferCircleView = (BufferCircleView) ((MainActivity) getActivity()).getLoading();
        }
        return bufferCircleView;
    }

    /**
     * 得到左边的按钮
     */
    protected TextView getLeftBtn() {
        TextView textView = null;
        if (getActivity() instanceof MainActivity) {
            textView = ((MainActivity) getActivity()).getLeftBtn();
        }
        return textView;
    }

    /**
     * 得到中间的TextView
     */
    protected TextView getCenterView() {
        TextView textView = null;
        if (getActivity() instanceof MainActivity) {
            textView = ((MainActivity) getActivity()).getCenterView();
        }
        return textView;
    }


    /**
     * 得到右边的按钮
     */
    protected TextView getRightBtn() {
        TextView textView = null;
        if (getActivity() instanceof MainActivity) {
            textView = ((MainActivity) getActivity()).getRightBtn();
        }
        return textView;
    }

    /**
     * 设置标题
     */
    protected void setTitle(String title) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setTitle(title);
        }

    }

    /**
     * 设置左边按钮的图片资源
     */
    protected void setLeftRes(int resId) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setLeftRes(resId);
        }

    }

    /**
     * 设置左边按钮的图片资源
     */
    protected void setRightRes(int resId) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setRightRes(resId);
        }

    }

    /**
     * 隐藏上方的标题栏
     */
    protected void hideHeadView() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).hideHeadView();
        }
    }

    /**
     * 隐藏左边的按钮
     */
    protected void hidebtn_left() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).hidebtn_left();
        }
    }

    /***
     * 隐藏右边的按钮
     */
    protected void hidebtn_right() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).hidebtn_right();
        }

    }


    /**
     * 显示上方的标题栏
     */
    protected void showHeadView() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showHeadView();
        }
    }

    /**
     * 显示左边的按钮
     */
    protected void showbtn_left() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showbtn_left();
        }

    }

    /***
     * 显示右边的按钮
     */
    protected void showbtn_right() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showbtn_right();
        }
    }


    /****************************************************************************/


    /**
     * 获取上一个Fragment传来的数据
     */
    protected void onGetBundle(Bundle bundle) {

    }

    //容易崩溃 小心配置  暂不提供
//    /**
//     * 设置横屏
//     */
//    protected void setOrientationLandscape() {
//        if (getActivity() instanceof MainActivity) {
//            if (getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            }
//        }
//
//    }
//
//
//    /**
//     * 设置竖屏
//     */
//    protected void setOrientationPortrait() {
//        if (getActivity() instanceof MainActivity) {
//            if (getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
//                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            }
//        }
//    }


    /**
     * 这种方式只限于再viewpager时使用
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
        } else {
            //相当于Fragment的onPause
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //不可见
            //还原标题栏
            showHeadView();
            showbtn_left();
            showbtn_right();
            //取消加载图片的任务
            ImageUtils.getInstance().cancelTask();
        } else {
            //当前可见
        }
    }

    /**
     * 网上说这个方法跟Activity不一样  可能不起作用所以使用上面的方法
     */
    @Override
    public void onPause() {
        super.onPause();
    }
    /****************************************************************************/

    /**
     * 跳转到另一个Activity，不携带数据，不设置flag
     */
    public void goToActivityByClass(Class<?> cls) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).goToActivityByClass(cls);
        }
    }

    /**
     * 跳转到另一个Activity，携带数据
     */
    public void goToActivityByClass(Class<?> cls, Bundle bundle) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).goToActivityByClass(cls, bundle);
        }
    }


    /**
     * 延迟去往新的Activity
     */
    public void delayToActivity(final Class<?> cls, long delay) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).delayToActivity(cls, delay);
        }
    }

    /******************************************************************/
    /**
     * 设置文本数据
     */
    public void setText(String text, int resId) {
        TextView textView = (TextView) contentView.findViewById(resId);
        textView.setText(text);
    }

    /**
     * 设置图片数据  使用自己定义的图片加载器
     */
    public void setImageByUrl(String url, int resId) {
        ImageView imageView = (ImageView) contentView.findViewById(resId);
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


    /*************************************************************************/

    /**
     * 从Fragment跳转到Activity 返回数据的设置
     */
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        String result = intent.getStringExtra("result");
        Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
    }

    /*********************************************************************/
    public void T(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public void T(float msg) {
        Toast.makeText(getActivity(), msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(double msg) {
        Toast.makeText(getActivity(), msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(int msg) {
        Toast.makeText(getActivity(), msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(boolean msg) {
        Toast.makeText(getActivity(), msg + "", Toast.LENGTH_SHORT).show();
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


    /***********************************************************************/
    /**
     * 判断网络是否连接
     */
    public boolean isConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) getActivity()
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

    /*************************************************************************/

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


    /*************************************************************************/
    /**
     * 提供特殊寻找的方法
     */
    protected <T extends View> T getViewById(int id) {
        return (T) contentView.findViewById(id);
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
        getActivity().registerReceiver(broadcastReceiver, filter);
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
        getActivity().sendBroadcast(intent);
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

}
