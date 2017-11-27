package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 创建日期 2017/7/1on 1:46.
 * 描述：
 * 作者：姜贺YSKJ-JH
 */

public class SeedsRobEntity implements Serializable{

    /**
     * code : 825
     * message : 升级订单才有可能抢到种子！
     * data : {"id":10,"createDate":"2017-07-01 00:16:21","userId":10,"orderNo":"s2017070100162089662","orderAmount":800,"payType":1,"orderType":2,"semenOrderPay":0,"semenTypeId":1,"paidAmount":800}
     */

    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public DataBean data;

    public static class DataBean implements Serializable{
        /**
         * id : 10
         * createDate : 2017-07-01 00:16:21
         * userId : 10
         * orderNo : s2017070100162089662
         * orderAmount : 800
         * payType : 1
         * orderType : 2
         * semenOrderPay : 0
         * semenTypeId : 1
         * paidAmount : 800
         */

        @SerializedName("id")
        public int id;
        @SerializedName("createDate")
        public String createDate;
        @SerializedName("userId")
        public int userId;
        @SerializedName("orderNo")
        public String orderNo;
        @SerializedName("orderAmount")
        public int orderAmount;
        @SerializedName("payType")
        public int payType;
        @SerializedName("orderType")
        public int orderType;
        @SerializedName("semenOrderPay")
        public int semenOrderPay;
        @SerializedName("semenTypeId")
        public int semenTypeId;
        @SerializedName("paidAmount")
        public int paidAmount;
    }
}
