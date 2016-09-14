package com.example.snoy.myapplication.fragment;

import android.widget.TextView;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.lib.base.BaseFragment;

/**
 * Created by Administrator on 2016/8/31.
 */
public class test_2_fragment extends BaseFragment {

    TextView tv0;
    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_test_xxx;
    }

    @Override
    public void findViews() {
        tv0= (TextView) contentView.findViewById(R.id.tv_0);

    }

    @Override
    public void initData() {
        tv0.setText("第二个Fragment");
    }

    @Override
    public void setListeners() {

    }
}
