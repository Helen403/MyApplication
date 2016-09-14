package com.example.snoy.myapplication.lib.Utils;
import com.example.snoy.myapplication.lib.ThreadPool.ThreadPoolFactory;

/**
 * 线程池
 */
public final class ThreadPoolUtils {
    /**
     * 在非UI线程中执行
     */
    public static void runTaskInThread(Runnable task) {
        //线程池
        ThreadPoolFactory.getCommonThreadPool().execute(task);
    }
}
