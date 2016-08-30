package com.example.snoy.myapplication.constant;

/**
 * Created by SNOY on 2016/8/24.
 */
public interface Constants {


    /**
     * 啊叶
     */
    interface JSON {
        final String jsonApartment = "http://www.ahjmall.com/cloud/get_apartment_list.json";
    }

    /******************************************************************************************/

    /**
     * 聪
     */
    interface CONG {
        final String region = "http://member.ahjmall.com/cloud/get_region_list.json";
    }

    /******************************************************************************************/

    /**
     * 海龙
     */
    interface Helen {
        final String location = "http://member.ahjmall.com/cloud/get_region_by_location.json/query?location=%s,%s";
    }

    /******************************************************************************************/
    /**
     * 请求次数只有加载引导页的时候加载一遍，开线程网络请求存放到数据库
     * 把那些请求一遍和请求一百遍不变的json的URL   存放在这里  满足的条件需要  1.不要请求参数
     * URL不管存放的位置---------------随便放
     */
    String[] JSON = new String[]{
            "http://www.ahjmall.com/cloud/get_product_group_v3.json"
    };


}
