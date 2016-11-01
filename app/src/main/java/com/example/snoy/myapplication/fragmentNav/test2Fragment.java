package com.example.snoy.myapplication.fragmentNav;

import android.view.View;
import android.widget.TextView;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.activity.threeActivity;
import com.example.snoy.myapplication.lib.base.BaseFragment;

/**
 * Created by Administrator on 2016/8/22.
 */
public class test2Fragment extends BaseFragment {


    TextView tv_1;

    @Override
    public int getContentView() {
        return R.layout.activity_five;
    }

    @Override
    public void findViews() {
        tv_1 = (TextView) contentView.findViewById(R.id.tv_1);
    }

    @Override
    public void initData() {
    }

    @Override
    public void setListeners() {
        setOnListeners(tv_1);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    case R.id.tv_1:
                        goToActivityByClass(threeActivity.class);
                        break;
                }
            }
        });
    }

}
