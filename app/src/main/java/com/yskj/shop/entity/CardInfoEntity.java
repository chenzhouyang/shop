package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 创建日期 2017/6/18on 9:58.
 * 描述：
 * 作者：姜贺YSKJ-JH
 */

public class CardInfoEntity {


        /**
         * code : 0
         * message : 成功
         * data : {"id":1,"createDate":"2017-06-18 09:37:15","modifyDate":null,"enable":false,"deleted":false,"userId":5,"bank":"zogngna","cardNo":"6222300461704960","subbranch":"asads","name":"jh"}
         */

        @SerializedName("code")
        public int code;
        @SerializedName("message")
        public String message;
        @SerializedName("data")
        public DataBean data;

        public static class DataBean {
                /**
                 * id : 1
                 * createDate : 2017-06-18 09:37:15
                 * modifyDate : null
                 * enable : false
                 * deleted : false
                 * userId : 5
                 * bank : zogngna
                 * cardNo : 6222300461704960
                 * subbranch : asads
                 * name : jh
                 */

                @SerializedName("id")
                public int id;
                @SerializedName("createDate")
                public String createDate;
                @SerializedName("modifyDate")
                public String modifyDate;
                @SerializedName("enable")
                public boolean enable;
                @SerializedName("deleted")
                public boolean deleted;
                @SerializedName("userId")
                public int userId;
                @SerializedName("bank")
                public String bank;
                @SerializedName("cardNo")
                public String cardNo;
                @SerializedName("subbranch")
                public String subbranch;
                @SerializedName("name")
                public String name;
                @SerializedName("alipayNo")
                public String alipayNo;
        }
}
