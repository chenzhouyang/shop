package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：  陈宙洋
 * 描述：我的挂单
 * 日期： 2017/10/26.
 */

public class PutEntity {

    /**
     * code : 200
     * data : {"endRow":1,"firstPage":1,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":true,"lastPage":1,"list":[{"createTime":1509155862000,"currency":0,"fee":0,"id":11,"num":100,"price":1.62,"status":1,"tradeNum":0,"tradeTime":null,"type":2,"userId":1}],"navigateFirstPage":1,"navigateLastPage":1,"navigatePages":8,"navigatepageNums":[1],"nextPage":0,"orderBy":"","pageNum":1,"pageSize":10,"pages":1,"prePage":0,"size":1,"startRow":1,"total":1}
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
         * endRow : 1
         * firstPage : 1
         * hasNextPage : false
         * hasPreviousPage : false
         * isFirstPage : true
         * isLastPage : true
         * lastPage : 1
         * list : [{"createTime":1509155862000,"currency":0,"fee":0,"id":11,"num":100,"price":1.62,"status":1,"tradeNum":0,"tradeTime":null,"type":2,"userId":1}]
         * navigateFirstPage : 1
         * navigateLastPage : 1
         * navigatePages : 8
         * navigatepageNums : [1]
         * nextPage : 0
         * orderBy :
         * pageNum : 1
         * pageSize : 10
         * pages : 1
         * prePage : 0
         * size : 1
         * startRow : 1
         * total : 1
         */

        @SerializedName("endRow")
        public int endRow;
        @SerializedName("firstPage")
        public int firstPage;
        @SerializedName("hasNextPage")
        public boolean hasNextPage;
        @SerializedName("hasPreviousPage")
        public boolean hasPreviousPage;
        @SerializedName("isFirstPage")
        public boolean isFirstPage;
        @SerializedName("isLastPage")
        public boolean isLastPage;
        @SerializedName("lastPage")
        public int lastPage;
        @SerializedName("navigateFirstPage")
        public int navigateFirstPage;
        @SerializedName("navigateLastPage")
        public int navigateLastPage;
        @SerializedName("navigatePages")
        public int navigatePages;
        @SerializedName("nextPage")
        public int nextPage;
        @SerializedName("orderBy")
        public String orderBy;
        @SerializedName("pageNum")
        public int pageNum;
        @SerializedName("pageSize")
        public int pageSize;
        @SerializedName("pages")
        public int pages;
        @SerializedName("prePage")
        public int prePage;
        @SerializedName("size")
        public int size;
        @SerializedName("startRow")
        public int startRow;
        @SerializedName("total")
        public int total;
        @SerializedName("list")
        public List<ListBean> list;
        @SerializedName("navigatepageNums")
        public List<Integer> navigatepageNums;

        public static class ListBean {
            /**
             * createTime : 1509155862000
             * currency : 0
             * fee : 0
             * id : 11
             * num : 100
             * price : 1.62
             * status : 1
             * tradeNum : 0
             * tradeTime : null
             * type : 2
             * userId : 1
             */

            @SerializedName("createTime")
            public long createTime;
            @SerializedName("currency")
            public int currency;
            @SerializedName("fee")
            public int fee;
            @SerializedName("id")
            public int id;
            @SerializedName("num")
            public double num;
            @SerializedName("price")
            public double price;
            @SerializedName("status")
            public int status;
            @SerializedName("tradeNum")
            public double tradeNum;
            @SerializedName("tradeTime")
            public Object tradeTime;
            @SerializedName("type")
            public int type;
            @SerializedName("userId")
            public int userId;
        }
    }
}
