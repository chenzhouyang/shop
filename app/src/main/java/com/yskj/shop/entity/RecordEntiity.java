package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YSKJ-02 on 2017/1/20.
 */

public class RecordEntiity {


    /**
     * code : 200
     * data : {"endRow":1,"firstPage":0,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":false,"isLastPage":true,"lastPage":0,"list":[{"bankId":10,"createTime":1509174103000,"fee":0.1,"id":4,"money":1,"status":0,"userId":17}],"navigateFirstPage":0,"navigateLastPage":0,"navigatePages":8,"navigatepageNums":[],"nextPage":0,"orderBy":"","pageNum":0,"pageSize":0,"pages":0,"prePage":0,"size":1,"startRow":1,"total":1}
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
         * list : [{"bankId":10,"createTime":1509174103000,"fee":0.1,"id":4,"money":1,"status":0,"userId":17}]
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
        public ArrayList<ListBean> list;
        @SerializedName("navigatepageNums")
        public List<?> navigatepageNums;

        public static class ListBean {
            /**
             * bankId : 10
             * createTime : 1509174103000
             * fee : 0.1
             * id : 4
             * money : 1
             * status : 0
             * userId : 17
             */

            @SerializedName("bankId")
            public int bankId;
            @SerializedName("createTime")
            public long createTime;
            @SerializedName("fee")
            public double fee;
            @SerializedName("id")
            public int id;
            @SerializedName("money")
            public double money;
            @SerializedName("status")
            public int status;
            @SerializedName("userId")
            public int userId;
        }
    }
}
