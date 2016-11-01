package com.example.snoy.myapplication.activity;

import android.view.View;
import android.widget.TextView;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.lib.base.BaseActivity;
import com.pgyersdk.feedback.PgyFeedback;

/**
 * Created by Administrator on 2016/9/20.
 */
public class threeActivity extends BaseActivity {

    TextView tv;

    @Override
    public int getContentView() {
        return R.layout.activity_three;
    }

    @Override
    public void findViews() {
        tv = (TextView) findViewById(R.id.tv_11);
    }

    @Override
    public void initData() {


    }

    @Override
    public void setListeners() {
        setOnListeners(tv);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id){
                    case R.id.tv_11:

//                        PgyFeedback.getInstance().showActiivty(context);
                        PgyFeedback.getInstance().showActivity(context);


                        break;
                }
            }
        });
    }

}
