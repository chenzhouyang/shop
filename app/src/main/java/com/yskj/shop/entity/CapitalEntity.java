package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：陈宙洋
 * 日期：2017/8/17.
 * 描述：购物车资金实体
 */

public class CapitalEntity {
    /**
     * data : {"actDiscount":0,"act_free_ship":0,"activity_point":0,"bonus_id":0,"credit_pay":0,"discountItem":{},"discountPrice":0,"exchange_point":0,"gift_id":0,"goodsPrice":639.6,"is_free_ship":0,"needPayMoney":639.6,"orderPrice":639.6,"point":0,"shippingPrice":0,"weight":0}
     * message :
     * result : 1
     */

    @SerializedName("data")
    public DataBean data;
    @SerializedName("message")
    public String message;
    @SerializedName("result")
    public int result;

    public static class DataBean {
        /**
         * actDiscount : 0
         * act_free_ship : 0
         * activity_point : 0
         * bonus_id : 0
         * credit_pay : 0
         * discountItem : {}
         * discountPrice : 0
         * exchange_point : 0
         * gift_id : 0
         * goodsPrice : 639.6
         * is_free_ship : 0
         * needPayMoney : 639.6
         * orderPrice : 639.6
         * point : 0
         * shippingPrice : 0
         * weight : 0
         */

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
        public int discountPrice;
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
        public int point;
        @SerializedName("shippingPrice")
        public int shippingPrice;
        @SerializedName("weight")
        public int weight;

        public static class DiscountItemBean {
        }
    }
}
