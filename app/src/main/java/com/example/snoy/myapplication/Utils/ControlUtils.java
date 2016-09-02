package com.example.snoy.myapplication.Utils;


import android.content.Context;
import android.widget.Toast;

import com.example.snoy.myapplication.base.BaseApplication;
import com.example.snoy.myapplication.custemview.BufferCircleView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 向外界提供的方法
 * get请求数据并转化
 * public static <T, E> void gets(String url, final E e, final Class<T> cls, final OnControlUtilsListener<T> onControlUtilsListener)
 * post请求数据并转化
 * public static <T, E> void posts(String url, final E e, final Class<T> cls, final OnControlUtilsListener onControlUtilsListener)
 * ***********************************************************************
 * 参数方面请参考demo
 * <p/>
 * <p/>
 * ***********************************************************************
 * ---------------------------存储到另一张表格  数据不会改变的
 * get请求数据并转化
 * public static <T, E> void getsForever(String url, final E e, final Class<T> cls, final OnControlUtilsListener<T> onControlUtilsListener)
 * post请求数据并转化
 * public static <T, E> void postsForever(String url, final E e, final Class<T> cls, final OnControlUtilsListener onControlUtilsListener)
 * ----------------------------不存储到数据库  每一次都请求服务器
 * get请求数据并转化
 * public static <T, E> void getsEveryTime(String url, final E e, final Class<T> cls, final OnControlUtilsListener<T> onControlUtilsListener)
 * post请求数据并转化
 * public static <T, E> void postsEveryTime(String url, final E e, final Class<T> cls, final OnControlUtilsListener onControlUtilsListener)
 */
public final class ControlUtils {

    //配置
    private static Context context = BaseApplication.context;

    /**
     * 单位是秒  60 * 60 * 12 半日  测试为30秒         --------根据项目需求来自己修改
     * 时间超过规定的时间就删除数据库的数据  图片的数据就不删除  就删除json数据
     * 只是删除临时的表单
     * 最好的办法就是与后台沟通  后台每次修改数据就发个请求判断  根据判断来删除
     */
    private final static int upDataTime = 30;

    private static Gson gson = null;


    private ControlUtils() {
    }

    static {
        if (gson == null) {
            synchronized (ControlUtils.class) {
                if (gson == null) {
                    gson = new Gson();
                    upData();
                }
            }
        }
    }

    /*****************************************************************/


    /**
     * 时间超过规定的时间就删除数据库的数据  图片的数据就不删除  就删除json数据
     * 扩展：做一些定时操作
     */
    private static void upData() {
        if (getBooleanTime()) {
            DButils.delect();
        }
    }


    /**
     * 保存当前时间  获取上次的时间  并返回是否要删除数据库
     */
    private static boolean getBooleanTime() {
        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        String lastTime = DButils.get("time");
        Boolean UpFlag;
        if (lastTime == null) {
            //存储当前时间
            DButils.put("time", time);
            return false;
        }
        //判读时间差
        UpFlag = difference(time, lastTime);
        //存储当前时间
        DButils.put("time", time);
        return UpFlag;
    }

    /**
     * 判断当前时间和上一次时间之间的差是否大于规定的时间 大于返回true 小于就返回false
     */
    private static boolean difference(String time, String lastTime) {
        boolean result = false;
        if (!lastTime.isEmpty()) {
            try {
                java.util.Date nowdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
                java.util.Date setdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lastTime);
                long between = (nowdate.getTime() - setdate.getTime());
                result = between > 1000 * upDataTime;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /*****************************************************************/


    /**
     * json转Bean
     */
    private static <T> T getEntityFromJson(String gsonString, Class<T> cls) {
        T t = null;
        try {
            if (gson != null) {
                t = gson.fromJson(gsonString.trim(), cls);
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * json转成ArrayList
     */
    private static <T> ArrayList<T> getListFromJson(String gsonString) {
        ArrayList<T> list;
        try {
            if (gson != null) {
                TypeToken<ArrayList<T>> tt = new TypeToken<ArrayList<T>>() {
                };
                list = gson.fromJson(gsonString.trim(), tt.getType());
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象转成Json
     */
    private static <T> String getJsonFromEntity(T t) {
        if (gson != null) {
            return gson.toJson(t);
        }
        return null;
    }


    /**
     * Json转成map的
     */
    private static <T> HashMap<String, T> GsonToMaps(String gsonString) {
        HashMap<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    /**
     * 集合转成Json
     */
    public static <T> String getJsonFromList(List<T> t) {
        if (gson != null) {
            return gson.toJson(t);
        }
        return null;
    }


    /******************************************************************/

    /**
     * get请求数据并转化
     */
    public static <T, E> void gets(final BufferCircleView bufferCircleView, String url, final E e, final Class<T> cls, final OnControlUtilsListener<T> onControlUtilsListener) {
        if (bufferCircleView != null && !bufferCircleView.isVisibility()) {
            bufferCircleView.show();
        }
        HashMap<String, String> map = null;
        String params = "";
        if (e != null) {
            params = getJsonFromEntity(e);
            map = GsonToMaps(params);
        }
        //先从数据库中获取如果没有再请求
        assert params != null;
        String tmp = DButils.get(url + params);
        if (tmp != null) {
            onSuccess(bufferCircleView, url, cls, onControlUtilsListener, tmp);
            return;
        }
        final String finalParams = params;
        HttpUtils.gets(url, map, new HttpUtils.OnHttpUtilsResultListener() {
            @Override
            public void onHttpSuccess(String url, String result) {
                onSuccess(bufferCircleView,url, cls, onControlUtilsListener, result);
                //数据成功返回就存储到数据库
                DButils.put(url + finalParams, result);
            }

            @Override
            public void onHttpFailure(String url) {
                onFailure(bufferCircleView, url, onControlUtilsListener);
            }
        });
    }


    /**
     * post请求数据并转化
     */
    public static <T, E> void posts(final BufferCircleView bufferCircleView, String url, final E e, final Class<T> cls, final OnControlUtilsListener<T> onControlUtilsListener) {
        if (bufferCircleView != null && !bufferCircleView.isVisibility()) {
            bufferCircleView.show();
        }
        String params = "";
        HashMap<String, String> map = null;
        if (e != null) {
            params = getJsonFromEntity(e);
            map = GsonToMaps(params);
        }
        //先从数据库中获取如果没有再请求
        assert params != null;
        String tmp = DButils.get(url + params);
        if (tmp != null) {
            onSuccess(bufferCircleView, url, cls, onControlUtilsListener, tmp);
            return;
        }
        final String finalParams = params;
        HttpUtils.posts(url, map, new HttpUtils.OnHttpUtilsResultListener() {
            @Override
            public void onHttpSuccess(String url, String result) {
                onSuccess(bufferCircleView,url, cls, onControlUtilsListener, result);
                //数据成功返回就存储到数据库
                DButils.put(url + finalParams, result);
            }

            @Override
            public void onHttpFailure(String url) {
                onFailure(bufferCircleView, url, onControlUtilsListener);
            }
        });
    }

    /**
     * get请求数据并转化    缓存到不变的表
     */
    public static <T, E> void getsForever(final BufferCircleView bufferCircleView, String url, final E e, final Class<T> cls, final OnControlUtilsListener<T> onControlUtilsListener) {
        if (bufferCircleView != null && !bufferCircleView.isVisibility()) {
            bufferCircleView.show();
        }
        String params = "";
        HashMap<String, String> map = null;
        if (e != null) {
            params = getJsonFromEntity(e);
            map = GsonToMaps(params);
        }
        //先从数据库中获取如果没有再请求
        assert params != null;
        String tmp = DButils.queryStringForeverBySql(url + params);
        if (tmp != null) {
            onSuccess(bufferCircleView, url, cls, onControlUtilsListener, tmp);
            return;
        }
        final String finalParams = params;
        HttpUtils.gets(url, map, new HttpUtils.OnHttpUtilsResultListener() {
            @Override
            public void onHttpSuccess(String url, String result) {
                onSuccess(bufferCircleView,url, cls, onControlUtilsListener, result);
                //数据成功返回就存储到数据库
                DButils.insertStringForeverBySql(url + finalParams, result);
            }

            @Override
            public void onHttpFailure(String url) {
                onFailure(bufferCircleView, url, onControlUtilsListener);
            }
        });
    }


    /**
     * post请求数据并转化   缓存到不变的表
     */
    public static <T, E> void postsForever(final BufferCircleView bufferCircleView, String url, final E e, final Class<T> cls, final OnControlUtilsListener<T> onControlUtilsListener) {
        if (bufferCircleView != null && !bufferCircleView.isVisibility()) {
            bufferCircleView.show();
        }
        HashMap<String, String> map = null;
        String params = "";
        if (e != null) {
            params = getJsonFromEntity(e);
            map = GsonToMaps(params);
        }
        //先从数据库中获取如果没有再请求
        assert params != null;
        String tmp = DButils.queryStringForeverBySql(url + params);
        if (tmp != null) {
            onSuccess(bufferCircleView, url, cls, onControlUtilsListener, tmp);
            return;
        }
        final String finalParams = params;
        HttpUtils.posts(url, map, new HttpUtils.OnHttpUtilsResultListener() {
            @Override
            public void onHttpSuccess(String url, String result) {
                onSuccess(bufferCircleView,url, cls, onControlUtilsListener, result);
                //数据成功返回就存储到数据库
                DButils.insertStringForeverBySql(url + finalParams, result);
            }

            @Override
            public void onHttpFailure(String url) {
                onFailure(bufferCircleView, url, onControlUtilsListener);
            }
        });
    }


    /**
     * get请求数据并转化
     */
    public static <T, E> void getsEveryTime(final BufferCircleView bufferCircleView, String url, final E e, final Class<T> cls, final OnControlUtilsListener<T> onControlUtilsListener) {
        if (bufferCircleView != null && !bufferCircleView.isVisibility()) {
            bufferCircleView.show();
        }
        HashMap<String, String> map = null;
        String params = "";
        if (e != null) {
            params = getJsonFromEntity(e);
            map = GsonToMaps(params);
        }
        HttpUtils.gets(url, map, new HttpUtils.OnHttpUtilsResultListener() {
            @Override
            public void onHttpSuccess(String url, String result) {
                onSuccess(bufferCircleView, url, cls, onControlUtilsListener, result);
            }

            @Override
            public void onHttpFailure(String url) {
                onFailure(bufferCircleView, url, onControlUtilsListener);
            }
        });
    }


    /**
     * post请求数据并转化
     */
    public static <T, E> void postsEveryTime(final BufferCircleView bufferCircleView, String url, final E e, final Class<T> cls, final OnControlUtilsListener<T> onControlUtilsListener) {
        if (bufferCircleView != null && !bufferCircleView.isVisibility()) {
            bufferCircleView.show();
        }
        HashMap<String, String> map = null;
        String params = "";
        if (e != null) {
            params = getJsonFromEntity(e);
            map = GsonToMaps(params);
        }
        HttpUtils.posts(url, map, new HttpUtils.OnHttpUtilsResultListener() {
            @Override
            public void onHttpSuccess(String url, String result) {
                onSuccess(bufferCircleView, url, cls, onControlUtilsListener, result);
            }

            @Override
            public void onHttpFailure(String url) {
                onFailure(bufferCircleView, url, onControlUtilsListener);
            }
        });
    }


    /**
     * 为了方便切换网络请求框架而提出来的方法
     */
    private static <T> void onSuccess(BufferCircleView bufferCircleView, String url, Class<T> cls, OnControlUtilsListener<T> onControlUtilsListener, String result) {
        if (cls != null) {
            T entity = getEntityFromJson(result, cls);
            ArrayList<T> list = getListFromJson(result);
            if (entity != null || list != null) {
                onControlUtilsListener.onSuccess(url, entity, list, result);
                if (bufferCircleView != null && bufferCircleView.isVisibility()) {
                    bufferCircleView.hide();
                }
            } else {
                onControlUtilsListener.onFailure(url);
                ToastUtils("请检测网络");
                if (bufferCircleView != null && bufferCircleView.isVisibility()) {
                    bufferCircleView.hide();
                }
            }
        } else {
            onControlUtilsListener.onFailure(url);
            ToastUtils("请检测网络");
            if (bufferCircleView != null && bufferCircleView.isVisibility()) {
                bufferCircleView.hide();
            }
        }
    }

    private static <T> void onFailure(BufferCircleView bufferCircleView, String url, OnControlUtilsListener<T> onControlUtilsListener) {
        onControlUtilsListener.onFailure(url);
        if (bufferCircleView != null && bufferCircleView.isVisibility()) {
            bufferCircleView.hide();
        }
    }

    /*********************************************************************/
    public static void ToastUtils(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    /*********************************************************************/

    /**
     * 异步下载数据的回调接口
     */
    public interface OnControlUtilsListener<T> {
        void onSuccess(String url, T obj, ArrayList<T> list, String result);
        void onFailure(String url);
    }

}
