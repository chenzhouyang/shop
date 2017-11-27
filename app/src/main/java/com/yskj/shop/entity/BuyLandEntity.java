package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 创建日期 2017/6/14on 16:15.
 * 描述：
 * 作者：姜贺YSKJ-JH
 */

public class BuyLandEntity  implements Serializable{
    /**
     * code : 0
     * message : 成功
     * data : {"id":1,"createDate":"2017-06-14 16:14:27","userId":2,"orderNo":"s2017061416142769413","orderAmount":100,"payType":0,"orderType":1}
     */

    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public DataBean data;

    public static class DataBean implements Serializable{
        /**
         * id : 1
         * createDate : 2017-06-14 16:14:27
         * userId : 2
         * orderNo : s2017061416142769413
         * orderAmount : 100
         * payType : 0
         * orderType : 1
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
