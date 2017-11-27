package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * 创建日期 2017/6/14on 15:05.
 * 描述：
 * 作者：姜贺YSKJ-JH
 */

public class CollectionEntity  {

    /**
     * code : 0
     * message : 成功
     * data : [{"id":18,"createDate":"2017-06-18 17:20:29","modifyDate":null,"enable":true,"deleted":false,"billAmount":150,"type":3,"open":0,"sendId":0,"userId":5},{"id":19,"createDate":"2017-06-18 17:21:29","modifyDate":null,"enable":true,"deleted":false,"billAmount":2000,"type":4,"open":0,"sendId":1,"userId":5},{"id":20,"createDate":"2017-06-18 17:22:02","modifyDate":null,"enable":true,"deleted":false,"billAmount":900,"type":3,"open":0,"sendId":0,"userId":5}]
     */

    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public ArrayList<DataBean> data;

    public static class DataBean {
        /**
         * id : 18
         * createDate : 2017-06-18 17:20:29
         * modifyDate : null
         * enable : true
         * deleted : false
         * billAmount : 150
         * type : 3
         * open : 0
         * sendId : 0
         * userId : 5
         */

        @SerializedName("id")
        public int id;
        @SerializedName("createDate")
        public String createDate;
        @SerializedName("enable")
        public boolean enable;
        @SerializedName("deleted")
        public boolean deleted;
        @SerializedName("billAmount")
        public double billAmount;
        @SerializedName("type")
        public int type;
        @SerializedName("open")
        public int open;
        @SerializedName("sendId")
        public int sendId;
        @SerializedName("userId")
        public int userId;
    }
}
