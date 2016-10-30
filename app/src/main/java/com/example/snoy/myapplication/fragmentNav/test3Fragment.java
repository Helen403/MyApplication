package com.example.snoy.myapplication.fragmentNav;


import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.lib.base.BaseFragment;
import com.example.snoy.myapplication.lib.custemview.MyRecycleView;

/**
 * Created by Administrator on 2016/8/22.
 */
public class test3Fragment extends BaseFragment {
    MyRecycleView myRecycleView;

    @Override
    public int getContentView() {
        return R.layout.activity_three;
    }

    @Override
    public void findViews() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onAttachMyRecycleViewAdapter() {
        super.onAttachMyRecycleViewAdapter();
        myRecycleView = (MyRecycleView) contentView.findViewById(R.id.myrecycleview);
    }

    @Override
    public void setListeners() {

    }




}
