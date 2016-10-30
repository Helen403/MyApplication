package com.example.snoy.myapplication.activity;

import android.widget.TextView;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.bean.result_home_item;
import com.example.snoy.myapplication.lib.Utils.ControlUtils;
import com.example.snoy.myapplication.lib.base.BaseActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/8/23.
 */
public class oneActivity extends BaseActivity {

    private TextView click;


    @Override
    public int getContentView() {
        return R.layout.fragment_test_1;
    }


    @Override
    public void findViews() {
    }

    @Override
    public void initData() {

//        Param param = new Param();
//        param.put("keyWord", "天");
//        param.put("page", 1);
//        param.put("rows", 10);
//
//        ControlUtils.posts("http://test.51ujf.cn/businessStore!search.do", param, BusinessBean.class, new ControlUtils.OnControlUtils<BusinessBean>() {
//            @Override
//            public void onSuccess(String url, BusinessBean obj, ArrayList<BusinessBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
//
//            }
//
//            @Override
//            public void onFailure(String url) {
//
//            }
//        });

        ControlUtils.postsEveryTime("", null, result_home_item.class, new ControlUtils.OnControlUtils<result_home_item>() {
            @Override
            public void onSuccess(String url, result_home_item result_home_item, ArrayList<result_home_item> list, String result, JSONObject jsonObject, JSONArray jsonArray) {

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
