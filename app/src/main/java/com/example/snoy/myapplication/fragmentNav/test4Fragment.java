package com.example.snoy.myapplication.fragmentNav;

import android.util.Log;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.lib.base.BaseFragment;
import com.example.snoy.myapplication.lib.shoppoing.ShoppingView;


/**
 * Created by Administrator on 2016/8/22.
 */
public class test4Fragment extends BaseFragment {


    @Override
    public int getContentView() {
        return R.layout.activity_four;
    }


    @Override
    public void findViews() {

        ShoppingView mSv1 = (ShoppingView) contentView.findViewById(R.id.sv_1);
        mSv1.setOnShoppingClickListener(new ShoppingView.ShoppingClickListener() {
            @Override
            public void onAddClick(int num) {
                Log.d("@=>", "add.......num=> " + num);
            }

            @Override
            public void onMinusClick(int num) {
                Log.d("@=>", "minus.......num=> " + num);
            }
        });

        ShoppingView mSv2 = (ShoppingView) contentView.findViewById(R.id.sv_2);
        mSv2.setTextNum(1);
    }

    @Override
    public void initData() {
    }


    @Override
    public void setListeners() {
    }

}
