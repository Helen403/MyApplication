package com.example.snoy.myapplication.fragmentNav;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.base.BaseFragment;


/**
 * Created by Administrator on 2016/8/22.
 */
public class test4Fragment extends BaseFragment {

    private TextView click;

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_test_1;
    }

    @Override
    protected void showMessage(RelativeLayout relativeLayout) {
        super.showMessage(relativeLayout);

    }

    @Override
    public void findViews() {
        click = (TextView) view.findViewById(R.id.click);
    }

    @Override
    public void initData() {
        //自己手动控制
//        getLoding().show();
        click.setText("这是第四个个Fragment");
    }

    @Override
    public void setListeners() {
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoding().hide();
            }
        });
    }

}
