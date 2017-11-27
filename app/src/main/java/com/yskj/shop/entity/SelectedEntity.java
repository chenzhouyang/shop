package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：陈宙洋
 * 日期：2017/8/18.
 * 描述：
 */

public class SelectedEntity {


    @SerializedName("result")
    public int result;
    @SerializedName("data")
    public DataBean data;

    public static class DataBean {

        @SerializedName("orderPrice")
        public OrderPriceBean orderPrice;
        @SerializedName("defaultAddress")
        public DefaultAddressBean defaultAddress;
        @SerializedName("items")
        public List<ItemsBean> items;

        public static class OrderPriceBean {

            @SerializedName("actDiscount")
            public int actDiscount;
            @SerializedName("act_free_ship")
            public int actFreeShip;
            @SerializedName("activity_point")
            public int activityPoint;
            @SerializedName("bonus_id")
            public int bonusId;
            @SerializedName("credit_pay")
            public int creditPay;
            @SerializedName("discountItem")
            public DiscountItemBean discountItem;
            @SerializedName("discountPrice")
            public double discountPrice;
            @SerializedName("exchange_point")
            public int exchangePoint;
            @SerializedName("gift_id")
            public int giftId;
            @SerializedName("goodsPrice")
            public double goodsPrice;
            @SerializedName("is_free_ship")
            public int isFreeShip;
            @SerializedName("needPayMoney")
            public double needPayMoney;
            @SerializedName("orderPrice")
            public double orderPrice;
            @SerializedName("point")
            public double point;
            @SerializedName("shippingPrice")
            public double shippingPrice;
            @SerializedName("weight")
            public int weight;

            public static class DiscountItemBean {
            }
        }

        public static class DefaultAddressBean {
            /**
             * addr : vhh
             * addr_id : 9
             * addressToBeEdit :
             * city : 淮安市
             * city_id : 944
             * country :
             * def_addr : 1
             * isDel : 0
             * member_id : 5
             * mobile : 15936365921
             * name : ghkkkkk
             * province : 江苏省
             * province_id : 864
             * region : 涟水县
             * region_id : 950
             * remark :
             * shipAddressName :
             * tel :
             * town :
             * town_id : -1
             * zip :
             */

            @SerializedName("addr")
            public String addr;
            @SerializedName("addr_id")
            public int addrId;
            @SerializedName("addressToBeEdit")
            public String addressToBeEdit;
            @SerializedName("city")
            public String city;
            @SerializedName("city_id")
            public int cityId;
            @SerializedName("country")
            public String country;
            @SerializedName("def_addr")
            public int defAddr;
            @SerializedName("isDel")
            public int isDel;
            @SerializedName("member_id")
            public int memberId;
            @SerializedName("mobile")
            public String mobile;
            @SerializedName("name")
            public String name;
            @SerializedName("province")
            public String province;
            @SerializedName("province_id")
            public int provinceId;
            @SerializedName("region")
            public String region;
            @SerializedName("region_id")
            public int regionId;
            @SerializedName("remark")
            public String remark;
            @SerializedName("shipAddressName")
            public String shipAddressName;
            @SerializedName("tel")
            public String tel;
            @SerializedName("town")
            public String town;
            @SerializedName("town_id")
            public int townId;
            @SerializedName("zip")
            public String zip;
        }

        public static class ItemsBean {
            @SerializedName("activity_id")
            public int activityId;
            @SerializedName("addon")
            public String addon;
            @SerializedName("catid")
            public int catid;
            @SerializedName("coupPrice")
            public double coupPrice;
            @SerializedName("exchange")
            public String exchange;
            @SerializedName("goods_id")
            public int goodsId;
            @SerializedName("goods_type")
            public int goodsType;
            @SerializedName("id")
            public int id;
            @SerializedName("image_default")
            public String imageDefault;
            @SerializedName("is_check")
            public int isCheck;
            @SerializedName("itemtype")
            public int itemtype;
            @SerializedName("limitnum")
            public int limitnum;
            @SerializedName("mktprice")
            public double mktprice;
            @SerializedName("name")
            public String name;
            @SerializedName("num")
            public int num;
            @SerializedName("others")
            public OthersBean others;
            @SerializedName("point")
            public int point;
            @SerializedName("price")
            public double price;
            @SerializedName("product_id")
            public int productId;
            @SerializedName("sn")
            public String sn;
            @SerializedName("snapshot_id")
            public int snapshotId;
            @SerializedName("specs")
            public String specs;
            @SerializedName("subtotal")
            public double subtotal;
            @SerializedName("unit")
            public String unit;
            @SerializedName("weight")
            public int weight;
            @SerializedName("pmtList")
            public List<?> pmtList;

            public static class OthersBean {
            }
        }
    }
}
