package com.example.snoy.myapplication.fragmentNav;

import android.widget.TextView;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.base.BaseFragment;

/**
 * Created by SNOY on 2016/8/23.
 */
public class test5Fragment extends BaseFragment {
    private TextView click;


    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_test_1;
    }

    @Override
    public void findViews() {
        click = (TextView) view.findViewById(R.id.click);
    }

    @Override
    public void initData() {
        click.setText("这是第五个Fragment");
    }

    @Override
    public void setListeners() {

    }

}
