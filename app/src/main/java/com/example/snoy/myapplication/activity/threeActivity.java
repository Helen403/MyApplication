package com.example.snoy.myapplication.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
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
        return R.layout.activity_four;
    }

    @Override
    public void findViews() {
        tv = (TextView) findViewById(R.id.tv);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindow();
            }
        });
    }


    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popupwindow, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);


        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);


        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(threeActivity.this.findViewById(R.id.start),
                Gravity.BOTTOM, 0, 0);

        // 这里检验popWindow里的button是否可以点击
        Button first = (Button) view.findViewById(R.id.first);
        first.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                System.out.println("第一个按钮被点击了");
            }
        });

        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });

    }
}
