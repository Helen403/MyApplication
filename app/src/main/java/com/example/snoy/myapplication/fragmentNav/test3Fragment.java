package com.example.snoy.myapplication.fragmentNav;


import com.example.snoy.myapplication.lib.MyRecycleView.MyLinearLayoutManager;
import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.base.BaseFragment;
import com.example.snoy.myapplication.base.MyBaseRecycleAdapter;
import com.example.snoy.myapplication.lib.custemview.MyRecycleView;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;

/**
 * Created by Administrator on 2016/8/22.
 */
public class test3Fragment extends BaseFragment {
    private ArrayList<String> datas;
    MyRecycleView myRecycleView;
    RecycleAdapter recycleAdapter;

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_three;
    }

    @Override
    protected void onAttachMyRecycleViewAdapter() {
        super.onAttachMyRecycleViewAdapter();
        myRecycleView = (MyRecycleView) contentView.findViewById(R.id.myrecycleview);
        datas = new ArrayList<>();
        // 使用重写后的线性布局管理器
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getActivity());
        myRecycleView.setLayoutManager(manager);

//        mRecyclerView.addFootView(footerView);

//        myRecycleView.addHeaderView(headerView);

        //添加分割线
//        mRecyclerView.addItemDecoration(new MyLinearLayoutManager.DividerItemDecoration(getContext(), manager.getOrientation(), true));

        // 使用重写后的格子布局管理器
//            mRecyclerView.setLayoutManager(new MyGridLayoutManager(this, 2));


        // 使用重写后的瀑布流布局管理器
//            mRecyclerView.setLayoutManager(new MyStaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));


        // 设置分割线
//            mRecyclerView.addItemDecoration(new MyGridLayoutManager.DividerGridItemDecoration(this, true));


        recycleAdapter = new RecycleAdapter(getActivity(), myRecycleView);
        // 设置适配器
        myRecycleView.setAdapter(recycleAdapter);
    }


    @Override
    public void findViews() {

    }


    Handler handler = new Handler();


    @Override
    public void initData() {

        // 刷新
        myRecycleView.setRefresh(true);

        recycleAdapter.setOnRefresh(new MyBaseRecycleAdapter.OnRefresh() {
            @Override
            public void onRefresh() {
                datas.clear();
                for (int i = 0; i < 4; i++) {
                    datas.add("刷新后aaaa" + i);
                }
                //模拟网络差的时候
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recycleAdapter.setRefresh(datas);
                    }
                }, 2000);

            }

            @Override
            public void onAddData() {
                datas.clear();
                for (int i = 0; i < 4; i++) {
                    datas.add("加载后xxxx  " + i);
                }
                //模拟网络差的时候
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recycleAdapter.setAddData(datas);
                    }
                }, 2000);

            }
        });
    }

    @Override
    public void setListeners() {

    }


    class RecycleAdapter extends MyBaseRecycleAdapter<String> {


        public RecycleAdapter(Context context, MyRecycleView mRecyclerView) {
            super(context, mRecyclerView);
        }

        @Override
        public int getContentView() {
            return R.layout.custermview_sample_item_card;
        }

        @Override
        public void onInitView(RecycleViewHolder holder, String s, int position) {
            //获取当前view的方法
//        View view = holder.getView();
            holder.setText(s, R.id.info_text);
        }
    }


}
