package com.example.snoy.myapplication.fragmentNav;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.Utils.ImageUtils;
import com.example.snoy.myapplication.base.BaseFragment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }




}
