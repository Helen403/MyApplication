package com.example.snoy.myapplication.fragmentNav;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.lib.Utils.LocationUtils;
import com.example.snoy.myapplication.lib.base.BaseFragment;


/**
 * Created by Administrator on 2016/8/22.
 */
public class test4Fragment extends BaseFragment {

    private TextView click;


    @Override
    public int getContentView() {
        return R.layout.fragment_test_1;
    }

    @Override
    protected void onShowMessage(RelativeLayout relativeLayout) {
        super.onShowMessage(relativeLayout);

    }


    @Override
    public void findViews() {
        click = (TextView) contentView.findViewById(R.id.click);
    }

    @Override
    public void initData() {
        click.setText("这是第四个个Fragment");
        LocationUtils.getBDLocation(new LocationUtils.OnLocationUtils() {
            @Override
            public void onSuccess(BDLocation location) {
                T(location.getLatitude()+","+location.getLongitude());
            }
        });
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
