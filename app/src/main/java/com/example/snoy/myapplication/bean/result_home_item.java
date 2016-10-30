package com.example.snoy.myapplication.bean;

import java.util.List;

/**
 * Created by SNOY on 2016/10/30.
 */
public class result_home_item {


    /**
     * status : 10028
     * msg : 获取数据成功
     * data : [{"goods_id":"17","goods_name":"苹果i5s","goods_img":"http://i1.hexunimg.cn/2014-08-15/167580248.jpg","area":"西区街道 远洋广场","distance":"4.097千米"},{"goods_id":"12","goods_name":"XIAOMI电视","goods_img":"http://i1.hexunimg.cn/2014-08-15/167580248.jpg","area":"环城街道 京华","distance":"4.579千米"},{"goods_id":"12","goods_name":"XIAOMI电视","goods_img":"http://i1.hexunimg.cn/2014-08-15/167580248.jpg","area":"环城街道 京华","distance":"4.579千米"},{"goods_id":"12","goods_name":"XIAOMI电视","goods_img":"http://i1.hexunimg.cn/2014-08-15/167580248.jpg","area":"环城街道 京华","distance":"4.579千米"},{"goods_id":"12","goods_name":"XIAOMI电视","goods_img":"http://i1.hexunimg.cn/2014-08-15/167580248.jpg","area":"环城街道 京华","distance":"4.579千米"},{"goods_id":"12","goods_name":"XIAOMI电视","goods_img":"http://i1.hexunimg.cn/2014-08-15/167580248.jpg","area":"环城街道 京华","distance":"4.579千米"},{"goods_id":"12","goods_name":"XIAOMI电视","goods_img":"http://i1.hexunimg.cn/2014-08-15/167580248.jpg","area":"环城街道 京华","distance":"4.579千米"},{"goods_id":"12","goods_name":"XIAOMI电视","goods_img":"http://i1.hexunimg.cn/2014-08-15/167580248.jpg","area":"环城街道 京华","distance":"4.579千米"}]
     */

    private int status;
    private String msg;
    /**
     * goods_id : 17
     * goods_name : 苹果i5s
     * goods_img : http://i1.hexunimg.cn/2014-08-15/167580248.jpg
     * area : 西区街道 远洋广场
     * distance : 4.097千米
     */

    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String goods_id;
        private String goods_name;
        private String goods_img;
        private String area;
        private String distance;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}
