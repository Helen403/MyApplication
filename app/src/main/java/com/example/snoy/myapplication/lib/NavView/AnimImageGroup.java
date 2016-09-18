package com.example.snoy.myapplication.lib.NavView;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.snoy.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ken on 2015/10/20.
 */
public final class AnimImageGroup extends FrameLayout implements Animation.AnimationListener {

    private List<Integer> datasIds;
    private Animation loutAnim, rinAnim, linAnim, routAnim;
    private LayoutParams layoutParams;
    private int index = -1;//当前需要显示的内容下标

    public AnimImageGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        paresAttr(attrs);
        initView();
    }

    private void initView() {
        layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        if(datasIds != null && datasIds.size() > 0){
            for(int i = 0; i < datasIds.size(); i++){
                ImageView iv = new ImageView(getContext());
                iv.setVisibility(INVISIBLE);
                iv.setLayoutParams(layoutParams);
                iv.setImageResource(datasIds.get(i));
                this.addView(iv);
            }
        }
    }

    /**
     * 下一个内容
     */
    public void nextContent(){
        if(datasIds != null && datasIds.size() > 0 && index != (datasIds.size() - 1)){
            index++;

            if(index > 0){
                //上一张图片从左侧划出
                ImageView aiv = (ImageView) this.getChildAt(index - 1);
                aiv.startAnimation(loutAnim);
            }

            ImageView niv = (ImageView) this.getChildAt(index);
            niv.startAnimation(rinAnim);
        }

    }

    /**
     * 上一个内容
     */
    public void aboveContent(){
        if(datasIds != null && datasIds.size() > 0 && index > 0){
            index--;

            //下一张图片从左侧划出
            ImageView niv = (ImageView) this.getChildAt(index + 1);
            niv.startAnimation(routAnim);

            ImageView iv = (ImageView) this.getChildAt(index);
            iv.startAnimation(linAnim);
        }
    }

    /**
     * 解析自定义属性
     * @param attrs
     */
    private void paresAttr(AttributeSet attrs){
        TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.animimageattr);
        int datas = ta.getResourceId(R.styleable.animimageattr_datas, 0);

        TypedArray datastp = getResources().obtainTypedArray(datas);
        datasIds = new ArrayList<Integer>();
        for(int i = 0; i < datastp.length(); i++){
            datasIds.add(datastp.getResourceId(i, 0));
        }
        datastp.recycle();

        int loutid = ta.getResourceId(R.styleable.animimageattr_loutanim, 0);
        int rinid = ta.getResourceId(R.styleable.animimageattr_rinanim, 0);
        int linid = ta.getResourceId(R.styleable.animimageattr_linanim, 0);
        int routid = ta.getResourceId(R.styleable.animimageattr_routanim, 0);

        loutAnim = AnimationUtils.loadAnimation(getContext(), loutid);
        rinAnim = AnimationUtils.loadAnimation(getContext(), rinid);
        linAnim = AnimationUtils.loadAnimation(getContext(), linid);
        routAnim = AnimationUtils.loadAnimation(getContext(), routid);

        loutAnim.setAnimationListener(this);
        rinAnim.setAnimationListener(this);
        linAnim.setAnimationListener(this);
        routAnim.setAnimationListener(this);
    }

    /**
     * 动画的监听事件
     * @param animation
     */
    @Override
    public void onAnimationStart(Animation animation) {
        //动画开始
        if(animation == rinAnim){
            ImageView niv = (ImageView) this.getChildAt(index);
            niv.setVisibility(VISIBLE);
        } else if(animation == linAnim){
            ImageView niv = (ImageView) this.getChildAt(index);
            niv.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //动画结束
        if(animation == loutAnim){
            //隐藏控件
            hideImgs(index);
        } else if(animation == routAnim){
            //隐藏控件
            hideImgs(index);
        }
    }

    private void hideImgs(int index){
        for(int i = 0; i < this.getChildCount(); i++){
            if(i != index){
                this.getChildAt(i).setVisibility(INVISIBLE);
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        //动画再次开始
    }
}
