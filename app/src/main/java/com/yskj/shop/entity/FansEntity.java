package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：  陈宙洋
 * 描述：粉丝实体类
 * 日期： 2017/11/10.
 */

public class FansEntity {
    /**
     * code : 200
     * data : {"endRow":3,"firstPage":0,"hasNextPage":false,"hasPreviousPage":false,"isFirstPage":false,"isLastPage":true,"lastPage":0,"list":[{"amountPass":"b4d592b9170ac1d3c6d3756b4804d896","availableFrozenIntegral":0,"availableIntegral":0,"buybackFrozenIntegral":0,"buybackIntegral":0,"consumeIntegral":0,"createTime":1510279544000,"deleted":false,"depth":",1,6","ecologyFrozenIntegral":0,"ecologyIntegral":0,"enable":true,"generalFrozenIntegral":0,"generalIntegral":0,"id":6,"investmentFund":0,"level":3,"mobile":"15936365921","nickName":"15936365921","originalIntegral":0,"password":"b4d592b9170ac1d3c6d3756b4804d896","referrerId":1,"sellFrozenIntegral":0,"sellIntegral":0,"spreadTotal":0,"tier":1},{"amountPass":"f2d5ad221474f5377535502b484042cc","availableFrozenIntegral":0,"availableIntegral":0,"buybackFrozenIntegral":0,"buybackIntegral":0,"consumeIntegral":0,"createTime":1510279276000,"deleted":false,"depth":",1,3","ecologyFrozenIntegral":0,"ecologyIntegral":0,"enable":true,"generalFrozenIntegral":0,"generalIntegral":0,"id":3,"investmentFund":0,"level":3,"mobile":"18697373102","nickName":"18697373102","originalIntegral":0,"password":"f2d5ad221474f5377535502b484042cc","referrerId":1,"sellFrozenIntegral":0,"sellIntegral":0,"spreadTotal":0,"tier":1},{"amountPass":"d3593aaab9d1a8b01d5a2ea558bc30b5","availableFrozenIntegral":0,"availableIntegral":0,"buybackFrozenIntegral":0,"buybackIntegral":0,"consumeIntegral":0,"createTime":1510279257000,"deleted":false,"depth":",1,2","ecologyFrozenIntegral":0,"ecologyIntegral":0,"enable":true,"generalFrozenIntegral":0,"generalIntegral":0,"id":2,"investmentFund":0,"level":3,"mobile":"18697373101","nickName":"18697373101","originalIntegral":0,"password":"d3593aaab9d1a8b01d5a2ea558bc30b5","referrerId":1,"sellFrozenIntegral":0,"sellIntegral":0,"spreadTotal":0,"tier":1}],"navigateFirstPage":0,"navigateLastPage":0,"navigatePages":8,"navigatepageNums":[],"nextPage":0,"orderBy":"","pageNum":0,"pageSize":0,"pages":0,"prePage":0,"size":3,"startRow":1,"total":3}
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
         * endRow : 3
         * firstPage : 0
         * hasNextPage : false
         * hasPreviousPage : false
         * isFirstPage : false
         * isLastPage : true
         * lastPage : 0
         * list : [{"amountPass":"b4d592b9170ac1d3c6d3756b4804d896","availableFrozenIntegral":0,"availableIntegral":0,"buybackFrozenIntegral":0,"buybackIntegral":0,"consumeIntegral":0,"createTime":1510279544000,"deleted":false,"depth":",1,6","ecologyFrozenIntegral":0,"ecologyIntegral":0,"enable":true,"generalFrozenIntegral":0,"generalIntegral":0,"id":6,"investmentFund":0,"level":3,"mobile":"15936365921","nickName":"15936365921","originalIntegral":0,"password":"b4d592b9170ac1d3c6d3756b4804d896","referrerId":1,"sellFrozenIntegral":0,"sellIntegral":0,"spreadTotal":0,"tier":1},{"amountPass":"f2d5ad221474f5377535502b484042cc","availableFrozenIntegral":0,"availableIntegral":0,"buybackFrozenIntegral":0,"buybackIntegral":0,"consumeIntegral":0,"createTime":1510279276000,"deleted":false,"depth":",1,3","ecologyFrozenIntegral":0,"ecologyIntegral":0,"enable":true,"generalFrozenIntegral":0,"generalIntegral":0,"id":3,"investmentFund":0,"level":3,"mobile":"18697373102","nickName":"18697373102","originalIntegral":0,"password":"f2d5ad221474f5377535502b484042cc","referrerId":1,"sellFrozenIntegral":0,"sellIntegral":0,"spreadTotal":0,"tier":1},{"amountPass":"d3593aaab9d1a8b01d5a2ea558bc30b5","availableFrozenIntegral":0,"availableIntegral":0,"buybackFrozenIntegral":0,"buybackIntegral":0,"consumeIntegral":0,"createTime":1510279257000,"deleted":false,"depth":",1,2","ecologyFrozenIntegral":0,"ecologyIntegral":0,"enable":true,"generalFrozenIntegral":0,"generalIntegral":0,"id":2,"investmentFund":0,"level":3,"mobile":"18697373101","nickName":"18697373101","originalIntegral":0,"password":"d3593aaab9d1a8b01d5a2ea558bc30b5","referrerId":1,"sellFrozenIntegral":0,"sellIntegral":0,"spreadTotal":0,"tier":1}]
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
         * size : 3
         * startRow : 1
         * total : 3
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
            @SerializedName("level")
            public int level;
            @SerializedName("mobile")
            public String mobile;
        }
    }
}
