package com.example.snoy.myapplication.fragment;

import android.os.Message;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.activity.threeActivity;
import com.example.snoy.myapplication.lib.base.BaseFragment;


/**
 * Created by Administrator on 2016/8/31.
 */
public class test_1_fragment extends BaseFragment {


    private static final int RC_SEARCH = 1;
    private static final int INTERVAL = 300; //输入时间间隔为300毫秒
    private EditText edittext;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == RC_SEARCH) {
                handlerSearch();
            }
        }
    };

    private void handlerSearch() {
        L(edittext.getText().toString());
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_test_xxx;
    }

    @Override
    public void findViews() {
        edittext = getViewById(R.id.edittext);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {


        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mHandler.hasMessages(RC_SEARCH)) {
                    mHandler.removeMessages(RC_SEARCH);
                }
                mHandler.sendEmptyMessageDelayed(RC_SEARCH, INTERVAL);
            }
        });


        setOnListeners(getViewById(R.id.tv_0));
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                L("点击成功");
                //  throw new RuntimeException("自定义异常");
                goToActivityByClass( threeActivity.class);

            }
        });


    }
}
