package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YSKJ-02 on 2017/1/20.
 */

public class BankListEntity {


    /**
     * code : 200
     * data : {"endRow":2,"firstPage":1,"hasNextPage":true,"hasPreviousPage":false,"isFirstPage":true,"isLastPage":false,"lastPage":7,"list":[{"address":"许昌","bankBranch":"尚集支行","cardName":"测试","cardNo":"6227005145124578112","createTime":1508738274000,"id":14,"name":"中国建设银行4","realNameId":18,"status":0,"userId":1},{"address":"许昌","bankBranch":"尚集支行","cardName":"测试","cardNo":"6227005145124578112","createTime":1508738272000,"id":13,"name":"中国建设银行3","realNameId":18,"status":0,"userId":1}],"navigateFirstPage":1,"navigateLastPage":7,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7],"nextPage":2,"orderBy":"","pageNum":1,"pageSize":2,"pages":7,"prePage":0,"size":2,"startRow":1,"total":13}
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
         * endRow : 2
         * firstPage : 1
         * hasNextPage : true
         * hasPreviousPage : false
         * isFirstPage : true
         * isLastPage : false
         * lastPage : 7
         * list : [{"address":"许昌","bankBranch":"尚集支行","cardName":"测试","cardNo":"6227005145124578112","createTime":1508738274000,"id":14,"name":"中国建设银行4","realNameId":18,"status":0,"userId":1},{"address":"许昌","bankBranch":"尚集支行","cardName":"测试","cardNo":"6227005145124578112","createTime":1508738272000,"id":13,"name":"中国建设银行3","realNameId":18,"status":0,"userId":1}]
         * navigateFirstPage : 1
         * navigateLastPage : 7
         * navigatePages : 8
         * navigatepageNums : [1,2,3,4,5,6,7]
         * nextPage : 2
         * orderBy :
         * pageNum : 1
         * pageSize : 2
         * pages : 7
         * prePage : 0
         * size : 2
         * startRow : 1
         * total : 13
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
        public List<Integer> navigatepageNums;

        public static class ListBean {
            /**
             * address : 许昌
             * bankBranch : 尚集支行
             * cardName : 测试
             * cardNo : 6227005145124578112
             * createTime : 1508738274000
             * id : 14
             * name : 中国建设银行4
             * realNameId : 18
             * status : 0
             * userId : 1
             */

            @SerializedName("address")
            public String address;
            @SerializedName("bankBranch")
            public String bankBranch;
            @SerializedName("cardName")
            public String cardName;
            @SerializedName("cardNo")
            public String cardNo;
            @SerializedName("createTime")
            public long createTime;
            @SerializedName("id")
            public int id;
            @SerializedName("name")
            public String name;
            @SerializedName("realNameId")
            public int realNameId;
            @SerializedName("status")
            public int status;
            @SerializedName("userId")
            public int userId;
        }
    }
}
