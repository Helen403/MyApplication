package com.example.snoy.myapplication.base;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snoy.myapplication.MainActivity;
import com.example.snoy.myapplication.Utils.HttpUtils;
import com.example.snoy.myapplication.Utils.ImageUtils;
import com.example.snoy.myapplication.custemview.BufferCircleView;
import com.example.snoy.myapplication.custemview.MyNetFailView;

import java.util.ArrayList;


/**
 * Created by mcs on 2015/11/3.
 */
public abstract class BaseFragment extends Fragment {
    //外面传入的View
    protected View view;
    //网络失败的View
    protected MyNetFailView myNetFailView;
    //总布局
    protected RelativeLayout content;
    /**
     * TextView  ImageView
     * 提供自动查找ID的数组  布局命名规范符合
     * TextView tv_0 ,tv_1....
     * ImageView iv_0,iv_1.....
     */
    protected TextView[] tv;
    protected ImageView[] iv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dealLogicBeforeFindView();
        //总布局
        content = new RelativeLayout(getActivity());
        LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        content.setLayoutParams(rl);
        //外面传入的View
        view = inflater.inflate(getContentView(), content, false);
        if (getArguments() != null)
            onGetBundle(getArguments());
        view.setClickable(true);
        content.addView(view);
        //加载无网络的View
        myNetFailView = new MyNetFailView(getActivity());
        LinearLayout.LayoutParams llNet = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        myNetFailView.setLayoutParams(llNet);
        content.addView(myNetFailView);
        return content;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClick();
        attachMyRecycleViewAdapter();
        //检测网络状态
        checkNet();
    }

    /**
     * 解决MyRecyclerViewBug
     * findViewById  也是在这里操作
     * 特殊提供
     */
    protected void attachMyRecycleViewAdapter() {
    }

    /**
     * 检测网络状态
     */
    private void checkNet() {
        if (isConnected()) {
            myNetFailView.setVisibility(View.GONE);
            findViews();
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

    public abstract void dealLogicBeforeFindView();

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
    protected BufferCircleView getLoding() {
        BufferCircleView bufferCircleView = null;
        if (getActivity() instanceof MainActivity) {
            bufferCircleView = (BufferCircleView) ((MainActivity) getActivity()).getLoding();
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
        TextView textView = (TextView) view.findViewById(resId);
        textView.setText(text);
    }

    /**
     * 设置图片数据  使用自己定义的图片加载器
     */
    public void setImageByUrl(String url, int resId) {
        ImageView imageView = (ImageView) view.findViewById(resId);
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
     * 根据名字填充tv数组  iv数组
     */
    private void fillLayout() {
        String packageName = getActivity().getPackageName();
        ArrayList<TextView> textViews = new ArrayList<>();
        TextView tvTmp;
        ArrayList<ImageView> imageViews = new ArrayList<>();
        ImageView ivTmp;

        //填充TextView
        int i = 0;
        int resId;
        do {
            resId = getResources().getIdentifier("tv_" + i, "id", packageName);
            if (resId != 0) {
                tvTmp = (TextView) view.findViewById(resId);
                textViews.add(tvTmp);
            } else {
                break;
            }
            ++i;
        } while (tvTmp != null);
        int sizeTv = textViews.size();
        if (sizeTv > 0)
            tv = textViews.toArray(new TextView[sizeTv]);

        //填充ImageView  i归零
        i = 0;
        do {
            resId = getResources().getIdentifier("iv_" + i, "id", packageName);
            if (resId != 0) {
                ivTmp = (ImageView) view.findViewById(resId);
                imageViews.add(ivTmp);
            } else {
                break;
            }
            ++i;
        } while (ivTmp != null);
        int sizeIv = imageViews.size();
        if (sizeIv > 0)
            iv = imageViews.toArray(new ImageView[sizeIv]);
    }


}
