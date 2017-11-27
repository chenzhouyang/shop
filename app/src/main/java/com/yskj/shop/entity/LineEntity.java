package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：  陈宙洋
 * 描述：折线图数据
 * 日期： 2017/10/25.
 */

public class LineEntity {
    /**
     * code : 200
     * data : {"endRow":10,"firstPage":1,"hasNextPage":true,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":false,"lastPage":3,"list":[{"count":0,"createTime":1508834142000,"fee":0,"id":43,"money":0,"num":0,"orderId":45,"price":2,"type":1,"userId":1},{"count":0,"createTime":1508833811000,"fee":0,"id":41,"money":0,"num":0,"orderId":43,"price":2,"type":1,"userId":1},{"count":0,"createTime":1508833810000,"fee":0,"id":39,"money":0,"num":0,"orderId":41,"price":2,"type":1,"userId":1},{"count":0,"createTime":1508833658000,"fee":0,"id":37,"money":0,"num":0,"orderId":39,"price":2,"type":1,"userId":1},{"count":0,"createTime":1508833644000,"fee":0,"id":35,"money":0,"num":0,"orderId":37,"price":2,"type":1,"userId":1},{"count":0,"createTime":1508833580000,"fee":0,"id":33,"money":0,"num":0,"orderId":35,"price":2,"type":1,"userId":1},{"count":0,"createTime":1508833483000,"fee":0,"id":31,"money":0,"num":0,"orderId":33,"price":2,"type":1,"userId":1},{"count":0,"createTime":1508832259000,"fee":0,"id":29,"money":84040,"num":44000,"orderId":31,"price":1.91,"type":1,"userId":1},{"count":0,"createTime":1508832181000,"fee":0,"id":27,"money":444000,"num":400000,"orderId":29,"price":1.11,"type":1,"userId":1},{"count":0,"createTime":1508832142000,"fee":0,"id":25,"money":41200,"num":40000,"orderId":27,"price":1.03,"type":1,"userId":1}],"navigateFirstPage":1,"navigateLastPage":3,"navigatePages":8,"navigatepageNums":[1,2,3],"nextPage":2,"orderBy":"","pageNum":1,"pageSize":10,"pages":3,"prePage":0,"size":10,"startRow":1,"total":22}
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
         * endRow : 10
         * firstPage : 1
         * hasNextPage : true
         * hasPreviousPage : false
         * isFirstPage : true
         * isLastPage : false
         * lastPage : 3
         * list : [{"count":0,"createTime":1508834142000,"fee":0,"id":43,"money":0,"num":0,"orderId":45,"price":2,"type":1,"userId":1},{"count":0,"createTime":1508833811000,"fee":0,"id":41,"money":0,"num":0,"orderId":43,"price":2,"type":1,"userId":1},{"count":0,"createTime":1508833810000,"fee":0,"id":39,"money":0,"num":0,"orderId":41,"price":2,"type":1,"userId":1},{"count":0,"createTime":1508833658000,"fee":0,"id":37,"money":0,"num":0,"orderId":39,"price":2,"type":1,"userId":1},{"count":0,"createTime":1508833644000,"fee":0,"id":35,"money":0,"num":0,"orderId":37,"price":2,"type":1,"userId":1},{"count":0,"createTime":1508833580000,"fee":0,"id":33,"money":0,"num":0,"orderId":35,"price":2,"type":1,"userId":1},{"count":0,"createTime":1508833483000,"fee":0,"id":31,"money":0,"num":0,"orderId":33,"price":2,"type":1,"userId":1},{"count":0,"createTime":1508832259000,"fee":0,"id":29,"money":84040,"num":44000,"orderId":31,"price":1.91,"type":1,"userId":1},{"count":0,"createTime":1508832181000,"fee":0,"id":27,"money":444000,"num":400000,"orderId":29,"price":1.11,"type":1,"userId":1},{"count":0,"createTime":1508832142000,"fee":0,"id":25,"money":41200,"num":40000,"orderId":27,"price":1.03,"type":1,"userId":1}]
         * navigateFirstPage : 1
         * navigateLastPage : 3
         * navigatePages : 8
         * navigatepageNums : [1,2,3]
         * nextPage : 2
         * orderBy :
         * pageNum : 1
         * pageSize : 10
         * pages : 3
         * prePage : 0
         * size : 10
         * startRow : 1
         * total : 22
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
             * count : 0
             * createTime : 1508834142000
             * fee : 0
             * id : 43
             * money : 0
             * num : 0
             * orderId : 45
             * price : 2
             * type : 1
             * userId : 1
             */

            @SerializedName("count")
            public int count;
            @SerializedName("createTime")
            public String createTime;
            @SerializedName("fee")
            public int fee;
            @SerializedName("id")
            public int id;
            @SerializedName("money")
            public double money;
            @SerializedName("num")
            public double num;
            @SerializedName("orderId")
            public int orderId;
            @SerializedName("price")
            public float price;
            @SerializedName("type")
            public int type;
            @SerializedName("userId")
            public int userId;
        }
    }
}
