package com.example.snoy.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.lib.Utils.ControlUtils;
import com.example.snoy.myapplication.lib.Utils.Param;
import com.example.snoy.myapplication.lib.base.BaseActivity;
import com.example.snoy.myapplication.bean.BusinessBean;

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
        click = (TextView) findViewById(R.id.click);
        tvId = new int[]{R.id.tv_0, R.id.tv_1};
        ivId = new int[]{};
    }

    @Override
    public void initData() {
        click.setText("这是测试Activity");

        Param param = new Param();
        param.put("keyWord", "天");
        param.put("page", 1);
        param.put("rows", 10);

        ControlUtils.posts("http://test.51ujf.cn/businessStore!search.do", param, BusinessBean.class, new ControlUtils.OnControlUtils<BusinessBean>() {
            @Override
            public void onSuccess(String url, BusinessBean obj, ArrayList<BusinessBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {

            }

            @Override
            public void onFailure(String url) {

            }
        });

        setBroadCast(oneActivity.class, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(oneActivity.class.getCanonicalName())) {
                    L("广播成功");
                }
            }
        });
    }

    @Override
    public void setListeners() {
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                goActivityForResult(oneActivity.this, twoActivity.class, null, new ActivityResultAction() {
//                    @Override
//                    public void onSuccess(Intent data) {
//
//                    }
//
//                    @Override
//                    protected void onCancel() {
//
//                    }
//                });
                goToActivityByClass(oneActivity.this,twoActivity.class);

            }
        });




        L(oneActivity.this.hashCode());
//        L();




    }


}
