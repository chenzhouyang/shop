package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 创建日期 2017/6/12on 17:59.
 * 描述：
 * 作者：姜贺YSKJ-JH
 */

public class AreCancelListEntity implements Serializable{

    /**
     * code : 0
     * message : 成功
     * data : [{"id":1,"createDate":"2017-07-06 04:22:31","modifyDate":null,"enable":true,"deleted":false,"userId":4,"amount":150,"stealAmount":0,"landHistoryId":1,"pmId":9,"semenTypeId":0,"status":1}]
     */

    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public List<DataBean> data;

    public static class DataBean implements Serializable{
        /**
         * id : 1
         * createDate : 2017-07-06 04:22:31
         * modifyDate : null
         * enable : true
         * deleted : false
         * userId : 4
         * amount : 150.0
         * stealAmount : 0.0
         * landHistoryId : 1
         * pmId : 9
         * semenTypeId : 0
         * status : 1
         */

        @SerializedName("id")
        public int id;
        @SerializedName("createDate")
        public String createDate;
        @SerializedName("enable")
        public boolean enable;
        @SerializedName("deleted")
        public boolean deleted;
        @SerializedName("userId")
        public int userId;
        @SerializedName("amount")
        public double amount;
        @SerializedName("stealAmount")
        public double stealAmount;
        @SerializedName("landHistoryId")
        public int landHistoryId;
        @SerializedName("pmId")
        public int pmId;
        @SerializedName("semenTypeId")
        public int semenTypeId;
        @SerializedName("status")
        public int status;
    }
}
