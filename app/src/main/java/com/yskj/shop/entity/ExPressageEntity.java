package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：陈宙洋
 * 日期：2017/8/17.
 * 描述：快递列表
 */

public class ExPressageEntity {
    /**
     * data : [{"id":1,"name":"圆通速递","code":"yuantong"},{"id":2,"name":"宅急送","code":"zhaijisong"},{"id":3,"name":"中通速递","code":"zhongtong"},{"id":4,"name":"申通快递","code":"shentong"},{"id":5,"name":"顺丰速递","code":"shunfeng"}]
     * draw : 1
     * recordsFiltered : 5
     * recordsTotal : 5
     */

    @SerializedName("draw")
    public int draw;
    @SerializedName("recordsFiltered")
    public int recordsFiltered;
    @SerializedName("recordsTotal")
    public int recordsTotal;
    @SerializedName("data")
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 1
         * name : 圆通速递
         * code : yuantong
         */

        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("code")
        public String code;
    }
}
