package com.example.snoy.myapplication.fragmentNav;


import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;

import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.lib.JDPullToRefresh.JDPullToRefreshRecyclerview;
import com.example.snoy.myapplication.lib.base.BaseFragment;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class test3Fragment extends BaseFragment {
    private JDPullToRefreshRecyclerview mRecyclerview;
    private List<String> mDatas = new ArrayList<>();
    Handler handler  = new Handler();

    @Override
    public int getContentView() {
        return R.layout.activity_sex;
    }

    @Override
    public void findViews() {

        initDatas();
        mRecyclerview = (JDPullToRefreshRecyclerview) contentView.findViewById(R.id.custom_pull_recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        final CommonAdapter adapter = new CommonAdapter<String>(getActivity(), R.layout.list_item, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.item_tv, s);
                holder.setImageResource(R.id.item_iv, R.mipmap.ic_launcher);
            }
        };
        mRecyclerview.setAdapter(adapter);
        mRecyclerview.setOnRefreshListener(new JDPullToRefreshRecyclerview.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mDatas.clear();
                        refreshDatas();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                mRecyclerview.onRefreshComplete();
                            }
                        });
                    }
                }).start();
            }
        });

    }



    private void initDatas()
    {
        for (int i = 0; i < 10; i++)
        {
            mDatas.add("买买买!");
        }
    }

    private void refreshDatas()
    {
        for (int i = 0; i < 10; i++)
        {
            mDatas.add("剁剁剁!");
        }
    }

    @Override
    public void initData() {

    }


    @Override
    public void setListeners() {

    }




}
