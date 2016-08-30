package com.example.snoy.myapplication.fragmentNav;

import com.example.snoy.myapplication.lib.MyRecycleView.MyLinearLayoutManager;
import com.example.snoy.myapplication.R;
import com.example.snoy.myapplication.base.BaseFragment;
import com.example.snoy.myapplication.base.MyBaseRecycleAdapter;
import com.example.snoy.myapplication.custemview.MyRecycleView;

import java.util.ArrayList;


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
    protected void attachMyRecycleViewAdapter() {
        super.attachMyRecycleViewAdapter();
        myRecycleView = (MyRecycleView) view.findViewById(R.id.myrecycleview);
        datas = new ArrayList<>();
        // 使用重写后的线性布局管理器
        MyLinearLayoutManager manager = new MyLinearLayoutManager(getActivity());
        myRecycleView.setLayoutManager(manager);

        //添加分割线
//        mRecyclerView.addItemDecoration(new MyLinearLayoutManager.DividerItemDecoration(getContext(), manager.getOrientation(), true));

        // 使用重写后的格子布局管理器
//            mRecyclerView.setLayoutManager(new MyGridLayoutManager(this, 2));


        // 使用重写后的瀑布流布局管理器
//            mRecyclerView.setLayoutManager(new MyStaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));


        // 设置分割线
//            mRecyclerView.addItemDecoration(new MyGridLayoutManager.DividerGridItemDecoration(this, true));


        recycleAdapter = new RecycleAdapter(myRecycleView);
        // 设置适配器
        myRecycleView.setAdapter(recycleAdapter);
    }


    @Override
    public void findViews() {

    }


    @Override
    public void initData() {

        // 刷新
        myRecycleView.setRefresh(true);

        recycleAdapter.setOnrefreshCallBack(new MyBaseRecycleAdapter.OnRefreshCallBack() {
            @Override
            public void onRefresh() {
                datas.clear();
                for (int i = 0; i < 4; i++) {
                    datas.add("刷新后aaaa" + i);
                }
                recycleAdapter.setRefresh(datas);
            }

            @Override
            public void onAddData() {
                datas.clear();
                for (int i = 0; i < 4; i++) {
                    datas.add("加载后xxxx  " + i);
                }
                recycleAdapter.setAddData(datas);
            }
        });
    }


    class RecycleAdapter extends MyBaseRecycleAdapter<String> {


        public RecycleAdapter(MyRecycleView mRecyclerView) {
            super(mRecyclerView);
        }

        @Override
        public int getHeadView() {
            return 0;
        }

        @Override
        public int getFootView() {
            return 0;
        }

        @Override
        public int getContentView() {
            return R.layout.custemview_sample_item_card;
        }

        @Override
        public void onInitView(RecycleViewHolder holder, String s, int position) {
            //获取当前view的方法
//        View view = holder.getView();
            holder.setText(R.id.info_text, s);
        }
    }


    @Override
    public void setListeners() {

    }
}
