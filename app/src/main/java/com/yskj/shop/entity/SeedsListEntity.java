package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 创建日期 2017/6/14on 11:53.
 * 描述：
 * 作者：姜贺YSKJ-JH
 */

public class SeedsListEntity implements Serializable{
    /**
     * data : {"semenTypes":[{"id":1,"unitPrice":10,"name":"桂圆"},{"id":2,"unitPrice":30,"name":"枇杷"},{"id":3,"unitPrice":60,"name":"椰子"}],"reserve":0}
     * message : 成功
     * code : 0
     */

    @SerializedName("data")
    public DataBean data;
    @SerializedName("message")
    public String message;
    @SerializedName("code")
    public int code;

    public static class DataBean {
        /**
         * semenTypes : [{"id":1,"unitPrice":10,"name":"桂圆"},{"id":2,"unitPrice":30,"name":"枇杷"},{"id":3,"unitPrice":60,"name":"椰子"}]
         * reserve : 0  //0.没有预定订单  1.有预定订单
         */

        @SerializedName("reserve")
        public int reserve;
        @SerializedName("semenTypes")
        public List<SemenTypesBean> semenTypes;

        public static class SemenTypesBean {
            /**
             * id : 1
             * unitPrice : 10
             * name : 桂圆
             */

            @SerializedName("id")
            public int id;
            @SerializedName("unitPrice")
            public double unitPrice;
            @SerializedName("name")
            public String name;
        }
    }
}
