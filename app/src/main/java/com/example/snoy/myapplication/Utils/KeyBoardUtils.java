package com.example.snoy.myapplication.Utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.snoy.myapplication.base.BaseApplication;


/**
 * 打开软键盘
 * public static void openKeybord(EditText editText)
 * 关闭软键盘
 * public static void closeKeybord(EditText editText)
 */
public final class KeyBoardUtils {

    //需要配置一下Context
    private static Context context = BaseApplication.context;

    /**
     * 打开软键盘
     */
    public static void openKeybord(EditText editText) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeybord(EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
