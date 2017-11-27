package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 创建日期 2017/6/12on 15:40.
 * 描述：
 * 作者：姜贺YSKJ-JH
 */

public class SeedsTitleEntity implements Serializable{


    /**
     * code : 0
     * message : 成功
     * data : [{"id":73,"number":0,"semenTypeId":1,"userId":22,"semenAmuont":0,"gainType":0},{"id":74,"number":0,"semenTypeId":2,"userId":22,"semenAmuont":0,"gainType":0},{"id":75,"number":0,"semenTypeId":3,"userId":22,"semenAmuont":0,"gainType":0},{"id":76,"number":0,"semenTypeId":1,"userId":22,"semenAmuont":0,"gainType":1},{"id":77,"number":0,"semenTypeId":2,"userId":22,"semenAmuont":0,"gainType":1},{"id":78,"number":0,"semenTypeId":3,"userId":22,"semenAmuont":0,"gainType":1}]
     */

    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public List<DataBean> data;

    public static class DataBean implements Serializable{
        /**
         * id : 73
         * number : 0.0
         * semenTypeId : 1
         * userId : 22
         * semenAmuont : 0.0
         * gainType : 0
         */

        @SerializedName("id")
        public int id;
        @SerializedName("number")
        public double number;
        @SerializedName("semenTypeId")
        public int semenTypeId;
        @SerializedName("userId")
        public int userId;
        @SerializedName("semenAmuont")
        public double semenAmuont;
        @SerializedName("gainType")
        public int gainType;
    }
}
