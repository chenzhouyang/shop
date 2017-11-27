package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 创建日期 2017/6/15on 10:00.
 * 描述：
 * 作者：姜贺YSKJ-JH
 */

public class SeedsMakeOrderEntity implements Serializable{

    /**
     * code : 0
     * message : 成功
     * data : {"id":2,"createDate":"2017-06-15 10:00:08","userId":2,"orderNo":"s2017061510000841612","orderAmount":700,"payType":0,"orderType":2}
     */

    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public DataBean data;

    public static class DataBean implements Serializable{
        /**
         * id : 2
         * createDate : 2017-06-15 10:00:08
         * userId : 2
         * orderNo : s2017061510000841612
         * orderAmount : 700
         * payType : 0
         * orderType : 2
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
        public double orderAmount;
        @SerializedName("payType")
        public int payType;
        @SerializedName("orderType")
        public int orderType;
    }
}
