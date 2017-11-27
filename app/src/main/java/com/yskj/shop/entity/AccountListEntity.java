package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：  陈宙洋
 * 描述：
 * 日期： 2017/11/23.
 */

public class AccountListEntity {
    /**
     * code : 200
     * data : {"endRow":1,"firstPage":0,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":false,"isLastPage":true,"lastPage":0,"list":[{"createTime":1511402240000,"fromId":2,"id":3,"money":100,"toId":4,"toMobile":"15936365921"}],"navigateFirstPage":0,"navigateLastPage":0,"navigatePages":8,"navigatepageNums":[],"nextPage":0,"orderBy":"","pageNum":0,"pageSize":0,"pages":0,"prePage":0,"size":1,"startRow":1,"total":1}
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
         * firstPage : 0
         * hasNextPage : false
         * hasPreviousPage : false
         * isFirstPage : false
         * isLastPage : true
         * lastPage : 0
         * list : [{"createTime":1511402240000,"fromId":2,"id":3,"money":100,"toId":4,"toMobile":"15936365921"}]
         * navigateFirstPage : 0
         * navigateLastPage : 0
         * navigatePages : 8
         * navigatepageNums : []
         * nextPage : 0
         * orderBy :
         * pageNum : 0
         * pageSize : 0
         * pages : 0
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
        public List<?> navigatepageNums;

        public static class ListBean {
            /**
             * createTime : 1511402240000
             * fromId : 2
             * id : 3
             * money : 100
             * toId : 4
             * toMobile : 15936365921
             */

            @SerializedName("createTime")
            public String createTime;
            @SerializedName("fromId")
            public int fromId;
            @SerializedName("id")
            public int id;
            @SerializedName("money")
            public double money;
            @SerializedName("toId")
            public int toId;
            @SerializedName("toMobile")
            public String toMobile;
        }
    }
}
