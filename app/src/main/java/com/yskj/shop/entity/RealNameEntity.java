package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：  陈宙洋
 * 描述：查询实名认证数据
 * 日期： 2017/10/25.
 */

public class RealNameEntity {
    /**
     * code : 200
     * data : {"createTime":1508914964000,"deleted":false,"enable":true,"id":26,"identityCardNo":"411002151545457845","name":"测试","userId":1}
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
         * createTime : 1508914964000
         * deleted : false
         * enable : true
         * id : 26
         * identityCardNo : 411002151545457845
         * name : 测试
         * userId : 1
         */

        @SerializedName("createTime")
        public long createTime;
        @SerializedName("deleted")
        public boolean deleted;
        @SerializedName("enable")
        public boolean enable;
        @SerializedName("id")
        public int id;
        @SerializedName("identityCardNo")
        public String identityCardNo;
        @SerializedName("name")
        public String name;
        @SerializedName("userId")
        public int userId;
    }
}
