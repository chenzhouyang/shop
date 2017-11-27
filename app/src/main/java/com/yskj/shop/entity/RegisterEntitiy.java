package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：  陈宙洋
 * 描述：注册实体
 * 日期： 2017/10/25.
 */

public class RegisterEntitiy {

    /**
     * code : 200
     * data : {"amountPass":"c3f4d325a81999dd0691aa010bad81be","availableFrozenIntegral":0,"availableIntegral":0,"buybackFrozenIntegral":0,"buybackIntegral":0,"consumeIntegral":0,"createTime":1508908779754,"deleted":false,"depth":",5","ecologyFrozenIntegral":0,"ecologyIntegral":0,"enable":true,"generalFrozenIntegral":0,"generalIntegral":0,"id":5,"investmentFund":0,"mobile":"15936365923","nickName":"15936365923","originalIntegral":0,"password":"c3f4d325a81999dd0691aa010bad81be","referrerId":0,"sellFrozenIntegral":0,"sellIntegral":0,"spreadTotal":0,"tier":0}
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
         * amountPass : c3f4d325a81999dd0691aa010bad81be
         * availableFrozenIntegral : 0
         * availableIntegral : 0
         * buybackFrozenIntegral : 0
         * buybackIntegral : 0
         * consumeIntegral : 0
         * createTime : 1508908779754
         * deleted : false
         * depth : ,5
         * ecologyFrozenIntegral : 0
         * ecologyIntegral : 0
         * enable : true
         * generalFrozenIntegral : 0
         * generalIntegral : 0
         * id : 5
         * investmentFund : 0
         * mobile : 15936365923
         * nickName : 15936365923
         * originalIntegral : 0
         * password : c3f4d325a81999dd0691aa010bad81be
         * referrerId : 0
         * sellFrozenIntegral : 0
         * sellIntegral : 0
         * spreadTotal : 0
         * tier : 0
         */

        @SerializedName("amountPass")
        public String amountPass;
        @SerializedName("availableFrozenIntegral")
        public int availableFrozenIntegral;
        @SerializedName("availableIntegral")
        public int availableIntegral;
        @SerializedName("buybackFrozenIntegral")
        public int buybackFrozenIntegral;
        @SerializedName("buybackIntegral")
        public int buybackIntegral;
        @SerializedName("consumeIntegral")
        public int consumeIntegral;
        @SerializedName("createTime")
        public long createTime;
        @SerializedName("deleted")
        public boolean deleted;
        @SerializedName("depth")
        public String depth;
        @SerializedName("ecologyFrozenIntegral")
        public int ecologyFrozenIntegral;
        @SerializedName("ecologyIntegral")
        public int ecologyIntegral;
        @SerializedName("enable")
        public boolean enable;
        @SerializedName("generalFrozenIntegral")
        public int generalFrozenIntegral;
        @SerializedName("generalIntegral")
        public int generalIntegral;
        @SerializedName("id")
        public int id;
        @SerializedName("investmentFund")
        public int investmentFund;
        @SerializedName("mobile")
        public String mobile;
        @SerializedName("nickName")
        public String nickName;
        @SerializedName("originalIntegral")
        public int originalIntegral;
        @SerializedName("password")
        public String password;
        @SerializedName("referrerId")
        public int referrerId;
        @SerializedName("sellFrozenIntegral")
        public int sellFrozenIntegral;
        @SerializedName("sellIntegral")
        public int sellIntegral;
        @SerializedName("spreadTotal")
        public int spreadTotal;
        @SerializedName("tier")
        public int tier;
    }
}
