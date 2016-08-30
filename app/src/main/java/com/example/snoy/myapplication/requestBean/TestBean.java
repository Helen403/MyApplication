package com.example.snoy.myapplication.requestBean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */
public class TestBean {

    /**
     * id : 260
     * thumb : http://u.ahjmall.com/img/fck_file/201605/1463556591_129b055b.jpg
     * name : 客厅家具
     * sub : [{"id":"261","thumb":"http://u.ahjmall.com","name":"皮艺沙发"},{"id":"262","thumb":"http://u.ahjmall.com","name":"布艺沙发"},{"id":"266","thumb":"http://u.ahjmall.com","name":"电视柜"},{"id":"267","thumb":"http://u.ahjmall.com","name":"茶几"},{"id":"268","thumb":"http://u.ahjmall.com","name":"鞋柜"},{"id":"269","thumb":"http://u.ahjmall.com","name":"换鞋凳"},{"id":"310","thumb":"http://u.ahjmall.com","name":"椅"}]
     */

    public String id;
    public String thumb;
    public String name;
    /**
     * id : 261
     * thumb : http://u.ahjmall.com
     * name : 皮艺沙发
     */

    public List<SubBean> sub;

    public static class SubBean {
        public String id;
        public String thumb;
        public String name;
    }
}
