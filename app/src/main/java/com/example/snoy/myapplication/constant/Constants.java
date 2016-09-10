package com.example.snoy.myapplication.constant;

/**
 * Created by SNOY on 2016/8/24.
 */
public interface Constants {




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
     * 只会加载一次
     * 请求次数只有加载引导页的时候加载一遍，开线程网络请求存放到数据库
     * 把那些请求一遍和请求一百遍不变的json的URL   存放在这里  满足的条件需要  1.不要请求参数
     * URL不管存放的位置---------------随便放
     */
    String[] JSON = new String[]{
            "http://www.ahjmall.com/cloud/get_product_group_v3.json"
    };

    /**
     * 这里就存放特定图片的url  下载完直接存储到SD卡
     * 例如下面的标题栏的图片 或者超大的图片
     * 只会加载一次
     */
    String[] BITMAP = new String[]{
           "http://mmbiz.qpic.cn/mmbiz_jpg/X2Vhfqvibrba9EAlvv5ZMwlgnA5diaGQE6kPgVwpltLQDrdxnYtuXbJvJovQErq9CQC94vFaF4Q2MPR3ib7aiagZ1g/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1"
    };


}
