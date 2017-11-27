package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 创建日期 2017/7/12on 15:58.
 * 描述：
 * 作者：姜贺YSKJ-JH
 */

public class CanStealEntity {

    /**
     * code : 0
     * message : 成功
     * data : [{"landId":6,"ownerId":6,"landNo":"NO.GGL1000000000000006","nickname":"果果80680","stealType":0,"createTime":"2017-07-06 02:52:12"}]
     */

    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public List<DataBean> data;

    public static class DataBean {
        /**
         * landId : 6       //土地ID
         * ownerId : 6      //土地所有者ID
         * landNo : NO.GGL1000000000000006      //土地编号
         * nickname : 果果80680       //土地所有者昵称
         * stealType : 0                //可偷状态0=可偷   1=不可偷
         * createTime : 2017-07-06 02:52:12     //土地创建时间
         */

        @SerializedName("landId")
        public int landId;
        @SerializedName("ownerId")
        public int ownerId;
        @SerializedName("landNo")
        public String landNo;
        @SerializedName("nickname")
        public String nickname;
        @SerializedName("stealType")
        public int stealType;
        @SerializedName("createTime")
        public String createTime;

        public int getStealType() {
            return stealType;
        }

        public void setStealType(int stealType) {
            this.stealType = stealType;
        }
    }
}
