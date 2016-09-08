package com.example.snoy.myapplication.activity;

import android.view.View;
import android.widget.TextView;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.Utils.ControlUtils;
import com.example.snoy.myapplication.base.BaseActivity;
import com.example.snoy.myapplication.bean.Result;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/8/23.
 */
public class testActivity extends BaseActivity {

    private TextView click;

    @Override
    public void dealLogicBeforeFindView() {
    }


    @Override
    public int setContentLayout() {
        return R.layout.fragment_test_1;
    }

    @Override
    public void findViews() {
        click = (TextView) findViewById(R.id.click);
    }

    @Override
    public void initData() {
        click.setText("这是测试Activity");



        tv[0].setText("成功");
        L(tv.length);
        L(iv.length);
        tv[1].setText("测试成功");
        setImageByUrl("http://mmbiz.qpic.cn/mmbiz_jpg/X2Vhfqvibrba9EAlvv5ZMwlgnA5diaGQE6kPgVwpltLQDrdxnYtuXbJvJovQErq9CQC94vFaF4Q2MPR3ib7aiagZ1g/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1", iv[0]);
    }

    @Override
    public void setListeners() {
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //不要声明mContext  因为回收内存的时候因为mContext回收不了
                goToActivityByClass(testActivity.this, StartActivityActivity.class);
            }
        });

    }


}
