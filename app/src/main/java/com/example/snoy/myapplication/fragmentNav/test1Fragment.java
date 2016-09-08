package com.example.snoy.myapplication.fragmentNav;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.Utils.DButils;
import com.example.snoy.myapplication.base.BaseFragment;
import com.example.snoy.myapplication.lib.custemview.ImageCycleView;
import com.example.snoy.myapplication.activity.testActivity;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/8/22.
 */
public class test1Fragment extends BaseFragment {
    private ImageCycleView viewpager;
    private TextView click;
    private ImageView imageView;

    @Override
    public void dealLogicBeforeFindView() {
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_test_1;
    }

    @Override
    public void findViews() {
        viewpager = (ImageCycleView) view.findViewById(R.id.viewpager);
        click = (TextView) view.findViewById(R.id.click);
        imageView = (ImageView) view.findViewById(R.id.iv);
    }

    @Override
    public void initData() {
        DButils.insertStringBySql("Helen","Helen");
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            data.add("1" + i);
        }
        viewpager.setImageResources(data, new ImageCycleView.ImageCycleViewListener() {
            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                imageView.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onImageClick(View imageView, int position) {
            }
        });


        click.setText("点击跳转Activity");
        //测试ImageView
        setImageByUrl("http://mmbiz.qpic.cn/mmbiz_jpg/X2Vhfqvibrba9EAlvv5ZMwlgnA5diaGQE6kPgVwpltLQDrdxnYtuXbJvJovQErq9CQC94vFaF4Q2MPR3ib7aiagZ1g/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1", imageView);

    }

    @Override
    public void setListeners() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), testActivity.class);
                startActivity(intent);
            }
        });
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               L(DButils.queryStringBySql("Helen"));
            }
        });
    }

    //Fragment从后台进入前面的时候再开启轮播  节约资源
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //不可见
            if (viewpager != null)
                viewpager.pushImageCycle();
        } else {
            //当前可见
            if (viewpager != null)
                viewpager.startImageCycle();
        }
    }

}
