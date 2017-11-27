package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 创建日期 2017/6/14on 18:50.
 * 描述：
 * 作者：姜贺YSKJ-JH
 */

public class LandEntity implements Serializable{


    /**
     * code : 0
     * message : 成功
     * data : [{"id":1,"createDate":"2017-06-14 18:28:15","modifyDate":null,"enable":true,"deleted":false,"sluggish":0,"disaster":2,"leisureType":1,"crops":0,"water":0,"userId":2,"plantTypeId":null,"plantTime":"2017-06-14 18:36:51","stealType":0,"stealNumber":0}]
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
         * createDate : 2017-06-14 18:28:15
         * modifyDate : null
         * enable : true
         * deleted : false
         * sluggish : 0
         * disaster : 2
         * leisureType : 1
         * crops : 0
         * water : 0
         * userId : 2
         * plantTypeId : null
         * plantTime : 2017-06-14 18:36:51
         * stealType : 0
         * stealNumber : 0
         */

        @SerializedName("id")
        public int id;
        @SerializedName("createDate")
        public String createDate;
        @SerializedName("modifyDate")
        public String modifyDate;
        @SerializedName("enable")
        public boolean enable;
        @SerializedName("deleted")
        public boolean deleted;
        @SerializedName("sluggish")
        public int sluggish;
        @SerializedName("disaster")
        public int disaster;
        @SerializedName("leisureType")
        public int leisureType;
        @SerializedName("crops")
        public int crops;
        @SerializedName("water")
        public int water;
        @SerializedName("userId")
        public int userId;
        @SerializedName("plantTypeId")
        public int plantTypeId;
        @SerializedName("plantTime")
        public String plantTime;
        @SerializedName("stealType")
        public int stealType;
        @SerializedName("stealNumber")
        public int stealNumber;
        @SerializedName("shifei")
        public int shifei;
    }
}
