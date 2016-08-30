package com.example.snoy.myapplication.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snoy.myapplication.Utils.ImageUtils;
import com.example.snoy.myapplication.custemview.MyRecycleView;

import java.util.ArrayList;

/**
 * Created by SNOY on 2016/8/13.
 */
public abstract class MyBaseRecycleAdapter<T> extends RecyclerView.Adapter<MyBaseRecycleAdapter<T>.RecycleViewHolder> {

    private Context context = BaseApplication.context;
    private ArrayList<T> data;
    private MyRecycleView mRecyclerView;


    private OnItemClickListener<T> mListener;

    public void setOnItemClickListener(OnItemClickListener<T> li) {
        mListener = li;
    }

    public MyBaseRecycleAdapter(MyRecycleView mRecyclerView) {
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

    public abstract int getHeadView();

    public abstract int getFootView();

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

        /******************************************/
        /**
         * TextView  ImageView
         * 提供自动查找ID的数组  布局命名规范符合
         * TextView tv_0 ,tv_1....
         * ImageView iv_0,iv_1.....
         */
        protected TextView[] tv;
        protected ImageView[] iv;


        /**
         * 如果用到了，比如我们用glide加载图片的时候，还有其他的需要用到上下文的时候
         */
        private Context mContext;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            views = new SparseArray<>();
            convertView = itemView;
        }

        /**
         * 返回一个具体的view对象
         */
        protected <T extends View> T getViewById(int viewId) {
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
            return convertView.getContext();
        }

        /**
         * 设置文字数据
         */
        public void setText(int resId, String text) {
            TextView view = getViewById(resId);
            view.setText(text);
        }

        /**
         * 下面的这个是加载显示图片的
         */
        public void setImageByUrl(int resId, String url) {
            ImageView imageView = getViewById(resId);
            ImageUtils.getInstance().setImageByUrl(url, imageView);
        }


        /**
         * 设置文本数据
         */
        public void setText(View view, int resId, String text) {
            TextView textView = (TextView) view.findViewById(resId);
            textView.setText(text);
        }

        /**
         * 设置图片数据  使用自己定义的图片加载器
         */
        public void setImageByUrl(View view, int resId, String url) {
            ImageView imageView = (ImageView) view.findViewById(resId);
            ImageUtils.getInstance().setImageByUrl(url, imageView);
        }

        /**************************************************************/
        //配合自动生成的方法
        public void setImageByUrl(String url, ImageView imageView) {
            ImageUtils.getInstance().setImageByUrl(url, imageView);
        }


        /*************************************************************************/
        /**
         * 根据名字填充tv数组  iv数组
         */
        private void fillLayout() {
            String packageName = context.getPackageName();
            ArrayList<TextView> textViews = new ArrayList<>();
            TextView tvTmp;
            ArrayList<ImageView> imageViews = new ArrayList<>();
            ImageView ivTmp;

            //填充TextView
            int i = 0;
            int resId;
            do {
                resId = context.getResources().getIdentifier("tv_" + i, "id", packageName);
                if (resId != 0) {
                    tvTmp = (TextView) convertView.findViewById(resId);
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
                resId = context.getResources().getIdentifier("iv_" + i, "id", packageName);
                if (resId != 0) {
                    ivTmp = (ImageView) convertView.findViewById(resId);
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


    /*************************************************************/


    protected View headerView;
    protected View footerView;

    private void init() {

        if (getHeadView() != 0) {
            // 头部
            headerView = LayoutInflater.from(context).inflate(getHeadView(), null);
            mRecyclerView.addHeaderView(headerView);
            onInitHeadView(headerView);
        }

        if (getFootView() != 0) {
            //脚部
            footerView = LayoutInflater.from(context).inflate(getFootView(), null);
            mRecyclerView.addFootView(footerView);
            onInitFootView(footerView);
        }

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
                        onrefreshCallBack.onRefresh();
                        refreshComplate();
                        // 刷新完成后调用，必须在UI线程中
                        mRecyclerView.refreshComplate();
                    } else {
                        onrefreshCallBack.onAddData();
                        loadMoreComplate();
                        // 加载更多完成后调用，必须在UI线程中
                        mRecyclerView.loadMoreComplate();
                    }
                }
            }, 1200);
        }
    }

    public void refreshComplate() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public void loadMoreComplate() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * 添加数据
     */
    public void setAddData(ArrayList<T> dataTmp) {
        if (dataTmp != null) {
            data.addAll(dataTmp);
        }
    }

    public void setRefresh(ArrayList<T> dataTmp) {
        if (dataTmp != null) {
            data.clear();
            data.addAll(dataTmp);
        }
    }

    /*********************************************************************/
    public void T(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void T(float msg) {
        Toast.makeText(context, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(double msg) {
        Toast.makeText(context, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(int msg) {
        Toast.makeText(context, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(boolean msg) {
        Toast.makeText(context, msg + "", Toast.LENGTH_SHORT).show();
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

    /*********************************************************************************/


    private OnRefreshCallBack onrefreshCallBack;


    public void setOnrefreshCallBack(OnRefreshCallBack onrefreshCallBack) {
        this.onrefreshCallBack = onrefreshCallBack;
    }

    /**
     * 留给调用者去实现
     * 复杂的头部View和复杂的尾部View
     */
    protected void onInitHeadView(View headerView) {
    }

    protected void onInitFootView(View footerView) {
    }

    public interface OnRefreshCallBack {
        void onRefresh();

        void onAddData();
    }


}