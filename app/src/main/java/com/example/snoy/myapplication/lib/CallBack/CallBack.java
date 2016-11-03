package com.example.snoy.myapplication.lib.CallBack;

/**
 * Created by SNOY on 2016/11/2.
 */
public interface CallBack<T> {

    /**
     * 当操作，有结果时调用
     *
     * @param datas
     */
    void onResultOK(T datas);

}
