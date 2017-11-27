package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：陈宙洋
 * 日期：2017/8/17.
 * 描述：修改购物车数量实体
 */

public class UpdateNumEntity {
    /**
     * result : 1
     * message : null
     * data : {"store":330}
     */

    @SerializedName("result")
    public int result;
    @SerializedName("message")
    public Object message;
    @SerializedName("data")
    public DataBean data;

    public static class DataBean {
        /**
         * store : 330
         */

        @SerializedName("store")
        public int store;
    }
}
