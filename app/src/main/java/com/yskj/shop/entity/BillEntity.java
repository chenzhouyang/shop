package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 创建日期 2017/6/18on 16:34.
 * 描述：
 * 作者：姜贺YSKJ-JH
 */

public class BillEntity  {

    /**
     * code : 0
     * message : 成功
     * data : [{"id":10,"createDate":"2017-06-18 13:00:27","modifyDate":null,"enable":false,"deleted":false,"billAmount":10,"type":5,"open":null,"sendId":null,"userId":5},{"id":11,"createDate":"2017-06-18 13:03:30","modifyDate":null,"enable":false,"deleted":false,"billAmount":10,"type":5,"open":null,"sendId":null,"userId":5},{"id":12,"createDate":"2017-06-18 13:04:17","modifyDate":null,"enable":false,"deleted":false,"billAmount":10,"type":5,"open":null,"sendId":null,"userId":5}]
     */

    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 10
         * createDate : 2017-06-18 13:00:27
         * modifyDate : null
         * enable : false
         * deleted : false
         * billAmount : 10
         * type : 5
         * open : null
         * sendId : null
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
        public Object open;
        @SerializedName("sendId")
        public Object sendId;
        @SerializedName("userId")
        public int userId;
    }
}
