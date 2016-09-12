package com.example.snoy.myapplication.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snoy.myapplication.Utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 因为经过超简的优化后  就不要新建一个类 放在Adapter包  直接使用内部类的形式（加快维修的速度和阅读的速度）
 * 设置数据
 * public void setData(ArrayList<T> data);
 * 清除数据
 * public void clearData();
 * 在原有数据的基础上再添加数据
 * public void addMoreByData(ArrayList<T> data)
 * ************************************************************
 * 设置文本数据
 * public void setText(int resId, String text)
 * 设置图片数据  使用自己定义的图片加载器
 * public void setImageByUrl(int resId, String url)
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    //需要配置一下Context
    protected Context contextApplication = BaseApplication.context;
    //用于跳转的Context
    protected Context context;
    protected ArrayList<T> data;
    protected View view;

    public MyBaseAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
    }

    /**
     * 设置数据
     */
    public void setData(List<T> data) {
        if (data!=null){
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }


    /**
     * 在原有数据的基础上再添加数据
     */
    public void addMoreByData(List<T> data) {
        if (data != null) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }
    
    /**
     * 清除数据
     */
    public void clearData() {
        this.data.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflate(getContentView());
        }
        onInitView(convertView, data.get(position), position);
        return convertView;
    }

    /**
     * 加载布局
     */
    private View inflate(int layoutResID) {
        LayoutInflater layoutInflater = (LayoutInflater) contextApplication
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layoutResID, null);
        return view;
    }

    public abstract int getContentView();

    public abstract void onInitView(View view, T t, int position);

    /**
     * view converView
     *
     * @param id 控件的id
     * @return 返回
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewById(int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (null == viewHolder) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (null == childView) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    /*******************************************************************/
    /**
     * 设置文本数据
     */
    public void setText(String text, int resId) {
        TextView view = getViewById(resId);
        view.setText(text);
    }

    /**
     * 设置图片数据  使用自己定义的图片加载器
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
    /*********************************************************************/
    /**
     * 跳转到另一个Activity，不携带数据，不设置flag
     */
    public void goToActivityByClass( Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    /**
     * 跳转到另一个Activity，携带数据
     */
    public void goToActivityByClass( Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    /**
     * 延迟去往新的Activity
     */
    public void delayToActivity( final Class<?> cls, long delay) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                context.startActivity(new Intent(context, cls));
            }
        }, delay);
    }






}