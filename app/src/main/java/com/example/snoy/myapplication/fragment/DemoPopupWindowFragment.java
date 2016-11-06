package com.example.snoy.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.lib.PopupWindowFragment.PopupWindowFragment;


/**
 * Created by chendingwei on 16/11/4.
 */

public class DemoPopupWindowFragment extends PopupWindowFragment {
    @Override
    protected View onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_pop_window,null);
    }
}
