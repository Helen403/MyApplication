package com.example.snoy.myapplication.lib.Utils;

import java.util.HashMap;

/**
 * 用于网络请求的HashMap<String, String>
 */
public class Param extends HashMap<String, String> {

    @Override
    public String put(String key, String value) {
        return super.put(key, value);
    }

    public void put(String key, int value) {
        put(key, value + "");
    }

    public void put(String key, double value) {
        put(key, value + "");
    }

    public void put(String key, float value) {
        put(key, value + "");
    }

    public void put(String key, boolean value) {
        put(key, value + "");
    }
}
