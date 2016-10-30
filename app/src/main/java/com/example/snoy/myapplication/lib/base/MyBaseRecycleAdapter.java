package com.example.snoy.myapplication.lib.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by SNOY on 2016/8/13.
 */
public abstract class MyBaseRecycleAdapter<T> extends RecyclerView.Adapter<MyBaseRecycleAdapter<T>.RecycleViewHolder> implements View.OnClickListener {

    private Context contextApplication = BaseApplication.context;
    private ArrayList<T> data;
    public Context context = BaseActivity.context;


    private OnItemClickListener<T> mListener;

    public void setOnItemClickListener(OnItemClickListener<T> li) {
        mListener = li;
    }

    public MyBaseRecycleAdapter() {
        this.data = new ArrayList<>();
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

    /**************************************************************/


    /**
     * 添加数据
     */
    public void setAddData(List<T> dataTmp) {
        if (dataTmp != null) {
            data.addAll(dataTmp);
        }
    }

    public void setRefresh(List<T> dataTmp) {
        if (dataTmp != null) {
            data.clear();
            data.addAll(dataTmp);
        }
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

    /*************************************************************************************/
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

    /*********************************************************************/
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

    /********************************************************************************************/
}