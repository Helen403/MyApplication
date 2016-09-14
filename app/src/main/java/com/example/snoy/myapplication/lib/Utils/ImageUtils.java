package com.example.snoy.myapplication.lib.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.ImageView;


import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.lib.base.BaseApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 向外界提供方法  用来加载图片数据
 * public void setImageByUrl(String url, ImageView imageView);
 * ****************************************************************
 * 获取url对应Bitmap, 内存没有就去sd卡中获取，SD卡没有就返回null
 * public Bitmap getBitmapByCache(String url) ;
 * 从网上获取一张Bitmap  异步操作
 * public void getBitmapByUrl(final String url,final OnImageUtilsListener listener)
 * 加载中显示的图片
 * int loading = R.mipmap.ic_launcher;
 * 加载失败显示的图片
 * int failing = R.mipmap.ic_launcher;
 * 获取文件的大小  返回是 bit  有些手机是除以1000 kb
 * public long getFileSize() ;
 * 删除SD卡缓存图片
 * public void deleteFile()
 * 从SD卡获取图片
 * public Bitmap getBitmapToSDCardByUrl(String url)
 * 保存图片到SD卡
 * public void saveBitmapToSDCardByUrl(String url, Bitmap bitmap)
 */
public final class ImageUtils {

    //需要配置一下Context
    private Context context = BaseApplication.context;

    //单例模式
    private volatile static ImageUtils imageDownLoader;

    //目标路径 SD卡路径 用来存储图片
    private static final String IMAGE_PATH = Environment.getExternalStorageDirectory() + "/project_helen/images/";

    //外界传入的ImageView
    private ImageView imageView;

    //加载中显示的图片
    private static final int loading = R.mipmap.ic_launcher;

    //加载失败显示的图片
    private static final int failing = R.mipmap.ic_launcher;

    //线程池的数量  默认2条
    private static final int ThreadPoolNum = 2;

    //内存的分配  默认4M
    private static final int MemoryNum = 4;


    public static ImageUtils getInstance() {
        if (imageDownLoader == null) {
            synchronized (ImageUtils.class) {
                if (imageDownLoader == null) {
                    imageDownLoader = new ImageUtils();
                }
            }
        }
        return imageDownLoader;
    }


    /**
     * 缓存Image的类，当存储Image的大小大于LruCache设定的值，系统自动释放内存
     */
    private LruCache<String, Bitmap> lruCache;

    /**
     * 下载Image的线程池
     */
    private ExecutorService mImageThreadPool = null;

    //因为是单例所以只会初始化一次
    private ImageUtils() {
        // 给LruCache分配4M内存
        lruCache = new LruCache<String, Bitmap>(1024 * 1024 * MemoryNum) {
            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                if (evicted) {
                    //如果被一级缓存移除，则放入二级缓存
                    softCache.put(key, new SoftReference<Bitmap>(oldValue));
                }
            }

            // 必须重写此方法，来测量Bitmap的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    /**
     * 二级缓存
     */
    private static HashMap<String, SoftReference<Bitmap>> softCache = new HashMap<String, SoftReference<Bitmap>>();

    /**
     * 获取线程池的方法，因为涉及到并发的问题，我们加上同步锁
     */
    private ExecutorService getThreadPool() {
        if (mImageThreadPool == null) {
            synchronized (ExecutorService.class) {
                if (mImageThreadPool == null) {
                    // 为了下载图片更加的流畅，我们用了2个线程来下载图片
                    mImageThreadPool = Executors.newFixedThreadPool(ThreadPoolNum);
                }
            }
        }
        return mImageThreadPool;
    }

    /**
     * 添加Bitmap到内存缓存
     */
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null && bitmap != null) {
            lruCache.put(key, bitmap);
        }
    }

    /**
     * 从内存缓存中获取一个Bitmap
     */
    private Bitmap getBitmapFromMemCache(String key) {
        return lruCache.get(key);
    }

    /**
     * 先从内存缓存中获取Bitmap,如果没有就从SD卡或者手机缓存中获取，SD卡或者手机缓存 没有就去下载
     */
    private static Handler handler = new Handler();

    private void downloadImage(final String url) {
        getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //从网络获取图片
                final Bitmap bitmap = getBitmapByUrl(url);
                if (bitmap != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            setBitmap(bitmap);
                        }
                    });
                    // 保存在SD卡或者手机目录
                    saveBitmapToSDCardByUrl(url, bitmap);
                    // 将Bitmap 加入内存缓存
                    addBitmapToMemoryCache(url, bitmap);
                } else {
                    //加载失败
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            setfailingBitmap();
                        }
                    });

                }
            }
        });
    }

    /**
     * 获取Bitmap, 内存中没有就去手机或者sd卡中获取
     */
    public Bitmap getBitmapByCache(String url) {
        //从一级缓存中取
        Bitmap bitmap = getBitmapFromMemCache(url);
        //从二级缓存中取
        if (bitmap == null) {
            if (softCache.containsKey(url)) {
                SoftReference<Bitmap> soft = softCache.get(url);
                bitmap = soft.get();
                if (bitmap != null) {
                    //放回一级缓存
                    lruCache.put(url, bitmap);
                    softCache.remove(url);
                }
            }
        }
        // 从SD卡获取手机里面获取Bitmap
        if (bitmap == null) {
            bitmap = getBitmapToSDCardByUrl(url);
            // 将Bitmap 加入内存缓存
            addBitmapToMemoryCache(url, bitmap);
        }
        return bitmap;
    }


    /**
     * 取消正在下载的任务
     */
    public synchronized void cancelTask() {
        if (mImageThreadPool != null) {
            mImageThreadPool.shutdownNow();
            mImageThreadPool = null;
        }
    }


    /**
     * 异步下载图片的回调设置图片
     */
    private void setBitmap(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }


    /**
     * 向外界提供方法  用来加载图片数据
     */
    public void setImageByUrl(String url, ImageView imageView) {
        this.imageView = imageView;
        //加载中显示的图片
        setLoadingBitmap();
        //内存或者SD卡中有图片直接设置
        Bitmap bitmap = getBitmapByCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            downloadImage(url);
        }
    }

    /**
     * 加载中显示的图片
     */
    private void setLoadingBitmap() {
        this.imageView.setImageResource(loading);
    }

    /**
     * 加载失败显示的图片
     */
    private void setfailingBitmap() {
        this.imageView.setImageResource(failing);
    }


    /*******************************************************************************/

    /**
     * 判断拓展卡是否装载
     */
    private static boolean isMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    /**
     * 保存图片到拓展卡
     */
    public void saveBitmapToSDCardByUrl(String url, Bitmap bitmap) {
        if (!isMounted())
            return;
        File file = new File(IMAGE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            File temp = new File(file, "" + url.hashCode());
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(temp));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片从扩展卡
     */
    public Bitmap getBitmapToSDCardByUrl(String url) {
        if (!isMounted())
            return null;
        File file = new File(IMAGE_PATH, "" + url.hashCode());
        if (file.exists()) {
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            } catch (OutOfMemoryError outOfMemoryError) {
                //超大的图片造成内存溢出 显示默认图片
                bitmap = BitmapFactory.decodeResource(context.getResources(), failing);
            }
            return bitmap;
        }
        return null;
    }


    /**
     * 递归
     * 获取文件的大小
     */
    public long getFileSize() {
        if (!isMounted())
            return 0;
        File file = new File(IMAGE_PATH);
        if (file.exists()) {
            return getFolderSize(file);
        }
        return 0;
    }

    /**
     * 得到文件的大小
     */
    private long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                // 如果下面还有文件
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }


    /**
     * 删除SD卡缓存图片
     */
    public void deleteFile() {
        if (!isMounted())
            return;
        File dirFile = new File(IMAGE_PATH);
        if (!dirFile.exists()) {
            return;
        }
        if (dirFile.isDirectory()) {
            String[] children = dirFile.list();
            for (String aChildren : children) {
                new File(dirFile, aChildren).delete();
            }
        }
        dirFile.delete();
    }


    /*****************************************************************************/

    /**
     * GET请求获取数据和图片
     */
    private byte[] getByteInfoByUrl(String url) {
        HttpURLConnection conn = null;
        ByteArrayOutputStream out = null;
        InputStream in = null;
        try {
            URL ul = new URL(url);
            conn = (HttpURLConnection) ul.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            out = new ByteArrayOutputStream();
            in = conn.getInputStream();
            int len;
            byte[] buffer = new byte[1024 * 2];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 从网上获取一张Bitmap
     */
    private Bitmap getBitmapByUrl(String url) {
        Bitmap bitmap = null;
        byte[] buffer = getByteInfoByUrl(url);
        if (buffer != null) {
            bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
        }
        return bitmap;
    }


    public void getBitmapByUrl(final String url, final OnImageUtilsListener listener) {
        getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = getBitmapByUrl(url);
                if (bitmap != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onImageSuccess(url, bitmap);
                        }
                    });
                } else {
                    //下载失败
                    listener.onImageFailure(url);
                }
            }
        });
    }

    /**
     * 根据view来生成bitmap图片，可用于截图功能
     */
    public Bitmap getBitmapByView(View view) {
        view.clearFocus(); //
        view.setPressed(false); //
        // 能画缓存就返回false
        boolean willNotCache = view.willNotCacheDrawing();
        view.setWillNotCacheDrawing(false);
        int color = view.getDrawingCacheBackgroundColor();
        view.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            view.destroyDrawingCache();
        }
        view.buildDrawingCache();
        Bitmap cacheBitmap = view.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        view.destroyDrawingCache();
        view.setWillNotCacheDrawing(willNotCache);
        view.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }

    /**
     * 异步下载图片的回调接口
     */
    public interface OnImageUtilsListener {
        void onImageSuccess(String url, Bitmap bitmap);

        void onImageFailure(String url);
    }

}
