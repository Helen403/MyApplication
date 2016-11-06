package com.example.snoy.myapplication.activity;

import android.view.View;
import android.widget.TextView;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.lib.base.BaseActivity;

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
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T("asdsadsa");
//                Message msg = Message.obtain();
//                msg.obj = new BusinessBean();
//                handler.sendMessage(msg);
            }
        });

    }

    @Override
    public void setListeners() {
        setOnListeners(tv);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id){
                    case R.id.tv_11:

//                        handler.setHandler(new IHandler() {
//                            @Override
//                            public void handleMessage(Message msg) {
//                               // msg.obj;
//                            }
//                        });

//                        PgyFeedback.getInstance().showActiivty(context);
//                        PgyFeedback.getInstance().showActivity(context);
//                        try {
//                            throw new Exception();
//                        } catch (Exception e) {
//                            PgyCrashManager.reportCaughtException(context, e);
//                        }


                        break;
                }
            }
        });
    }

}
