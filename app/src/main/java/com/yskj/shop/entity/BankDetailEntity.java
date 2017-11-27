package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：  陈宙洋
 * 描述：银行卡信息
 * 日期： 2017/10/25.
 */

public class BankDetailEntity {
    /**
     * code : 200
     * data : {"address":"许昌","bankBranch":"尚集支行","cardName":"测试","cardNo":"6227005145124578112","createTime":1508738274000,"id":14,"name":"中国建设银行4","realNameId":18,"status":0,"userId":1}
     * message : SUCCESS
     */

    @SerializedName("code")
    public int code;
    @SerializedName("data")
    public DataBean data;
    @SerializedName("message")
    public String message;

    public static class DataBean {
        /**
         * address : 许昌
         * bankBranch : 尚集支行
         * cardName : 测试
         * cardNo : 6227005145124578112
         * createTime : 1508738274000
         * id : 14
         * name : 中国建设银行4
         * realNameId : 18
         * status : 0
         * userId : 1
         */

        @SerializedName("address")
        public String address;
        @SerializedName("bankBranch")
        public String bankBranch;
        @SerializedName("cardName")
        public String cardName;
        @SerializedName("cardNo")
        public String cardNo;
        @SerializedName("createTime")
        public long createTime;
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("realNameId")
        public int realNameId;
        @SerializedName("status")
        public int status;
        @SerializedName("userId")
        public int userId;
    }
}
