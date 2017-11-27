package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YSKJ-JH on 2017/1/14.
 */

public class ShoppingCartEntity implements Serializable {



    @SerializedName("result")
    public int result;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public DataBean data;

    public static class DataBean {

        @SerializedName("total")
        public double total;
        @SerializedName("count")
        public int count;
        @SerializedName("items")
        public List<ItemsBean> items;

        public static class ItemsBean {
            @SerializedName("id")
            public int id;
            @SerializedName("product_id")
            public int productId;
            @SerializedName("goods_id")
            public int goodsId;
            @SerializedName("name")
            public String name;
            @SerializedName("mktprice")
            public double mktprice;
            @SerializedName("price")
            public double price;
            @SerializedName("coupPrice")
            public double coupPrice;
            @SerializedName("subtotal")
            public double subtotal;
            @SerializedName("num")
            public int num;
            @SerializedName("limitnum")
            public Object limitnum;
            @SerializedName("image_default")
            public String imageDefault;
            @SerializedName("point")
            public int point;
            @SerializedName("itemtype")
            public int itemtype;
            @SerializedName("sn")
            public String sn;
            @SerializedName("addon")
            public String addon;
            @SerializedName("specs")
            public String specs;
            @SerializedName("catid")
            public int catid;
            @SerializedName("others")
            public OthersBean others;
            @SerializedName("exchange")
            public Object exchange;
            @SerializedName("unit")
            public String unit;
            @SerializedName("goods_type")
            public int goodsType;
            @SerializedName("weight")
            public int weight;
            @SerializedName("activity_id")
            public int activityId;
            @SerializedName("is_check")
            public int isCheck;

            public int getIsCheck() {
                return isCheck;
            }

            public void setIsCheck(int isCheck) {
                this.isCheck = isCheck;
            }

            @SerializedName("snapshot_id")
            public Object snapshotId;
            @SerializedName("pmtList")
            public List<?> pmtList;

            public static class OthersBean {
                @SerializedName("specList")
                public List<SpecListBean> specList;

                public static class SpecListBean {
                    /**
                     * name : 尺寸
                     * value : XXS
                     */

                    @SerializedName("name")
                    public String name;
                    @SerializedName("value")
                    public String value;
                }
            }
        }
    }
}
