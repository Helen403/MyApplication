package com.example.snoy.myapplication.lib.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.snoy.myapplication.lib.Utils.ImageUtils;
import com.example.snoy.myapplication.lib.custemview.MyRecycleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SNOY on 2016/8/13.
 */
public abstract class MyBaseRecycleAdapter<T> extends RecyclerView.Adapter<MyBaseRecycleAdapter<T>.RecycleViewHolder> implements View.OnClickListener {

    private Context contextApplication = BaseApplication.context;
    private ArrayList<T> data;
    private MyRecycleView mRecyclerView;
    private Context context;


    private OnItemClickListener<T> mListener;

    public void setOnItemClickListener(OnItemClickListener<T> li) {
        mListener = li;
    }

    public MyBaseRecycleAdapter(Context context, MyRecycleView mRecyclerView) {
        this.context = context;
        this.data = new ArrayList<>();
        this.mRecyclerView = mRecyclerView;
        init();
    }


    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(getContentView(), parent, false);
        return new RecycleViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecycleViewHolder holder, final int position) {

        final T t = data.get(position);
        onInitView(holder, t, position);

        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(holder.itemView, position, t);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    /**
     * 留给调用者去实现
     */

    public abstract int getContentView();

    public abstract void onInitView(RecycleViewHolder holder, T t, int position);

    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T t);
    }


    /******************************************************************************************/

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        /**
         * 这个稀疏数组，网上说的是提高效率的
         */
        private final SparseArray<View> views;
        private View convertView;


        public RecycleViewHolder(View itemView) {
            super(itemView);
            views = new SparseArray<>();
            convertView = itemView;
        }

        /**
         * 返回一个具体的view对象
         */
        public <T extends View> T getViewById(int viewId) {
            View view = views.get(viewId);
            if (view == null) {
                view = convertView.findViewById(viewId);
                views.put(viewId, view);
            }
            return (T) view;
        }

        /**
         * 获取当前的View
         */
        public View getView() {
            return convertView;
        }

        /**
         * 获取当前的上下文
         */
        public Context getContext() {
            return context;
        }

        /**
         * 设置文字数据
         */
        public void setText(String text, int resId) {
            TextView view = getViewById(resId);
            view.setText(text);
        }

        /**
         * 下面的这个是加载显示图片的
         */
        public void setImageByUrl(String url, int resId) {
            ImageView imageView = getViewById(resId);
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

        /**************************************************************/
        //配合自动生成的方法
        public void setImageByUrl(String url, ImageView imageView) {
            ImageUtils.getInstance().setImageByUrl(url, imageView);
        }


    }


    /*************************************************************/
    private void init() {

        // 设置刷新动画的颜色
        mRecyclerView.setColor(Color.RED, Color.BLUE);
        // 设置头部恢复动画的执行时间，默认500毫秒
        mRecyclerView.setHeaderImageDurationMillis(300);
        // 设置拉伸到最高时头部的透明度，默认0.5f
        mRecyclerView.setHeaderImageMinAlpha(0.6f);


        // 设置刷新和加载更多数据的监听，分别在onRefresh()和onLoadMore()方法中执行刷新和加载更多操作
        mRecyclerView.setLoadDataListener(new MyRecycleView.LoadDataListener() {
            @Override
            public void onRefresh() {
                new Thread(new MyRunnable(true)).start();
            }

            @Override
            public void onLoadMore() {
                new Thread(new MyRunnable(false)).start();
            }
        });
    }

    private Handler mHandler = new Handler();

    class MyRunnable implements Runnable {

        boolean isRefresh;

        public MyRunnable(boolean isRefresh) {
            this.isRefresh = isRefresh;
        }

        @Override
        public void run() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isRefresh) {
                        onrefresh.onRefresh();
                        //在UI线程中调用
//                        refreshComplate();
                    } else {
                        onrefresh.onAddData();
                        // 加载更多完成后调用，必须在UI线程中
//                        loadMoreComplate();
                    }
                }
            }, 1200);
        }
    }

    protected void refreshComplate() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
        // 刷新完成后调用，必须在UI线程中
        mRecyclerView.refreshComplate();
    }

    protected void loadMoreComplate() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mRecyclerView.loadMoreComplate();
    }

    /**
     * 添加数据
     */
    public void setAddData(List<T> dataTmp) {
        if (dataTmp != null) {
            data.addAll(dataTmp);
        }
        loadMoreComplate();
    }

    public void setRefresh(List<T> dataTmp) {
        if (dataTmp != null) {
            data.clear();
            data.addAll(dataTmp);
        }
        refreshComplate();
    }

    /*********************************************************************/
    public void T(String msg) {
        Toast.makeText(contextApplication, msg, Toast.LENGTH_SHORT).show();
    }

    public void T(float msg) {
        Toast.makeText(contextApplication, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(double msg) {
        Toast.makeText(contextApplication, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(int msg) {
        Toast.makeText(contextApplication, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(boolean msg) {
        Toast.makeText(contextApplication, msg + "", Toast.LENGTH_SHORT).show();
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

    /********************************************************************************************/
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
        context.sendBroadcast(intent);
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



    /*****************************************************************************************************/


    private OnRefresh onrefresh;


    public void setOnRefresh(OnRefresh onrefresh) {
        this.onrefresh = onrefresh;
    }

    public interface OnRefresh {
        void onRefresh();

        void onAddData();
    }


}