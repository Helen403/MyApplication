package com.example.snoy.myapplication.base;

import android.content.Context;
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
    protected Context context = BaseApplication.context;
    protected ArrayList<T> data;
    protected View view;

    /******************************************/
    /**
     * TextView  ImageView
     * 提供自动查找ID的数组  布局命名规范符合
     * TextView tv_0 ,tv_1....
     * ImageView iv_0,iv_1.....
     */
    protected TextView[] tv;
    protected ImageView[] iv;


    public MyBaseAdapter(ArrayList<T> data) {
        this.data = data;
    }

    /**
     * 设置数据
     */
    public void setData(ArrayList<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clearData() {
        this.data.clear();
        notifyDataSetChanged();
    }

    /**
     * 在原有数据的基础上再添加数据
     */
    public void addMoreByData(ArrayList<T> data) {
        if (data != null) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }
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
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layoutResID, null);
        fillLayout();
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
    /*********************************************************************/

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
                tvTmp = getViewById(resId);
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
                ivTmp = getViewById(resId);
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