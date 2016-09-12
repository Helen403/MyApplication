package com.example.snoy.myapplication.activity;

import android.view.View;
import android.widget.TextView;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.Utils.ControlUtils;
import com.example.snoy.myapplication.Utils.DButils;
import com.example.snoy.myapplication.base.BaseActivity;
import com.example.snoy.myapplication.bean.TestBean;
import com.example.snoy.myapplication.fragment.test_1_fragment;
import com.example.snoy.myapplication.fragment.test_2_fragment;

import java.util.ArrayList;


public class StartActivityActivity extends BaseActivity {

    private TextView tv1;
    private TextView tv2;


    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_start;
    }


    @Override
    public void findViews() {
        tv1 = (TextView) findViewById(R.id.tv_1);
        tv2 = (TextView) findViewById(R.id.tv_2);
    }

    @Override
    public void initData() {
        fragmentId = R.id.frame_id;
        test_1_fragment test_1_fragment = new test_1_fragment();
        test_2_fragment test_2_fragment = new test_2_fragment();
        fragmentList.add(test_1_fragment);
        fragmentList.add(test_2_fragment);
        switchFragment(0);
    }


    @Override
    public void setListeners() {

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(0);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(1);
            }
        });
        setOnListeners(tv1, tv2);
    }



}
