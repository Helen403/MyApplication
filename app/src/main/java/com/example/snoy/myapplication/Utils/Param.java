package com.example.snoy.myapplication.Utils;

import java.util.HashMap;

/**
 * Created by SNOY on 2016/9/11.
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
