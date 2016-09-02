package com.example.snoy.myapplication.activity;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.Utils.ControlUtils;
import com.example.snoy.myapplication.Utils.DButils;
import com.example.snoy.myapplication.base.BaseActivity;
import com.example.snoy.myapplication.bean.TestBean;

import java.util.ArrayList;


public class StartActivityActivity extends BaseActivity {


    @Override
    public void dealLogicBeforeFindView() {
       
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_start;
    }

    @Override
    public void findViews() {

    }

    @Override
    public void initData() {
        String str = DButils.queryStringForeverBySql("http://www.ahjmall.com/cloud/get_product_group_v3.json");
        L(str);
        ControlUtils.postsForever(bufferCircleView, "http://www.ahjmall.com/cloud/get_product_group_v3.json", null, TestBean.class, new ControlUtils.OnControlUtilsListener<TestBean>() {
            @Override
            public void onSuccess(String url, TestBean obj, ArrayList<TestBean> list, String result) {
                L(result);
            }

            @Override
            public void onFailure(String url) {

            }
        });
    }

    @Override
    public void setListeners() {

    }

}
