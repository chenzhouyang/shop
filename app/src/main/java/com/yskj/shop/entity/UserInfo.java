package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 创建日期 2017/6/12on 14:52.
 * 描述：
 * 作者：姜贺YSKJ-JH
 */

public class UserInfo implements Serializable{


    /**
     * code : 200
     * data : {"amountPass":"e10adc3949ba59abbe56e057f20f883e","availableFrozenIntegral":0,"availableIntegral":0,"buybackFrozenIntegral":0,"buybackIntegral":0,"consumeIntegral":0,"createTime":1508983380000,"deleted":false,"depth":",1","ecologyFrozenIntegral":0,"ecologyIntegral":11000,"enable":true,"generalFrozenIntegral":0,"generalIntegral":9987200,"id":1,"investmentFund":0,"mobile":"15936365921","nickName":"15936365921","originalIntegral":0,"password":"e10adc3949ba59abbe56e057f20f883e","referrerId":0,"sellFrozenIntegral":0,"sellIntegral":0,"spreadTotal":0,"tier":0}
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
         * amountPass : e10adc3949ba59abbe56e057f20f883e
         * availableFrozenIntegral : 0
         * availableIntegral : 0
         * buybackFrozenIntegral : 0
         * buybackIntegral : 0
         * consumeIntegral : 0
         * createTime : 1508983380000
         * deleted : false
         * depth : ,1
         * ecologyFrozenIntegral : 0
         * ecologyIntegral : 11000
         * enable : true
         * generalFrozenIntegral : 0
         * generalIntegral : 9987200
         * id : 1
         * investmentFund : 0
         * mobile : 15936365921
         * nickName : 15936365921
         * originalIntegral : 0
         * password : e10adc3949ba59abbe56e057f20f883e
         * referrerId : 0
         * sellFrozenIntegral : 0
         * sellIntegral : 0
         * spreadTotal : 0
         * tier : 0
         */

        @SerializedName("amountPass")
        public String amountPass;
        @SerializedName("availableFrozenIntegral")
        public double availableFrozenIntegral;
        @SerializedName("availableIntegral")
        public double availableIntegral;
        @SerializedName("buybackFrozenIntegral")
        public double buybackFrozenIntegral;
        @SerializedName("buybackIntegral")
        public double buybackIntegral;
        @SerializedName("consumeIntegral")
        public double consumeIntegral;
        @SerializedName("createTime")
        public long createTime;
        @SerializedName("deleted")
        public boolean deleted;
        @SerializedName("depth")
        public String depth;
        @SerializedName("ecologyFrozenIntegral")
        public int ecologyFrozenIntegral;
        @SerializedName("ecologyIntegral")
        public double ecologyIntegral;
        @SerializedName("enable")
        public boolean enable;
        @SerializedName("generalFrozenIntegral")
        public double generalFrozenIntegral;
        @SerializedName("generalIntegral")
        public double generalIntegral;
        @SerializedName("id")
        public int id;
        @SerializedName("investmentFund")
        public int investmentFund;
        @SerializedName("mobile")
        public String mobile;
        @SerializedName("nickName")
        public String nickName;
        @SerializedName("originalIntegral")
        public double originalIntegral;
        @SerializedName("password")
        public String password;
        @SerializedName("referrerId")
        public int referrerId;
        @SerializedName("sellFrozenIntegral")
        public double sellFrozenIntegral;
        @SerializedName("sellIntegral")
        public double sellIntegral;
        @SerializedName("spreadTotal")
        public double spreadTotal;
        @SerializedName("tier")
        public int tier;
        @SerializedName("totalLand")
        public int totalLand;
    }
}
