package com.example.snoy.myapplication.activity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.base.BaseActivity;
import com.example.snoy.myapplication.fragment.test_1_fragment;
import com.example.snoy.myapplication.fragment.test_2_fragment;

import java.util.ArrayList;


public class StartActivityActivity extends BaseActivity {



    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_start;
    }


    @Override
    public void findViews() {
        tvId = new int[]{R.id.tv_1, R.id.tv_2};
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
        setOnListeners(tv[0], tv[1]);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id){
                    case R.id.tv_1:
                        switchFragment(0);
                        break;
                    case R.id.tv_2:
                        switchFragment(1);
                        break;
                }
            }
        });
    }

}
