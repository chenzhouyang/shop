package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 创建日期 2017/7/4on 14:50.
 * 描述：
 * 作者：姜贺YSKJ-JH
 */

public class LoverEntity {

    /**
     * code : 0
     * message : 成功
     * data : [{"mobile":"13526787585","amount":1000,"nikName":"13526787585","ampassIs":null,"tier":3,"landNum":5,"landLevel":3,"fenhongLevel":0,"fenhongLevelj":0,"shifei":0,"doglevel":0},{"mobile":"13512345678","amount":0,"nikName":"13512345678","ampassIs":null,"tier":3,"landNum":7,"landLevel":3,"fenhongLevel":0,"fenhongLevelj":0,"shifei":0,"doglevel":0},{"mobile":"13526787586","amount":0,"nikName":"13526787586","ampassIs":null,"tier":3,"landNum":5,"landLevel":3,"fenhongLevel":0,"fenhongLevelj":0,"shifei":0,"doglevel":0},{"mobile":"13526787587","amount":30400,"nikName":"13526787587","ampassIs":null,"tier":3,"landNum":9,"landLevel":3,"fenhongLevel":0,"fenhongLevelj":0,"shifei":0,"doglevel":0},{"mobile":"13526787588","amount":99900,"nikName":"13526787588","ampassIs":null,"tier":3,"landNum":5,"landLevel":3,"fenhongLevel":0,"fenhongLevelj":0,"shifei":0,"doglevel":0},{"mobile":"13526787589","amount":95300,"nikName":"13526787589","ampassIs":null,"tier":3,"landNum":6,"landLevel":3,"fenhongLevel":0,"fenhongLevelj":0,"shifei":0,"doglevel":0},{"mobile":"15617248633","amount":0,"nikName":"15617248633","ampassIs":null,"tier":3,"landNum":5,"landLevel":3,"fenhongLevel":0,"fenhongLevelj":0,"shifei":0,"doglevel":0},{"mobile":"18211671510","amount":0,"nikName":"18211671510","ampassIs":null,"tier":3,"landNum":5,"landLevel":0,"fenhongLevel":0,"fenhongLevelj":0,"shifei":0,"doglevel":0}]
     */

    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public List<DataBean> data;

    public static class DataBean {
        /**
         * mobile : 13526787585
         * amount : 1000
         * nikName : 13526787585
         * ampassIs : null
         * tier : 3
         * landNum : 5
         * landLevel : 3
         * fenhongLevel : 0
         * fenhongLevelj : 0
         * shifei : 0
         * doglevel : 0
         */

        @SerializedName("mobile")
        public String mobile;
        @SerializedName("amount")
        public double amount;
        @SerializedName("nikName")
        public String nikName;
        @SerializedName("tier")
        public int tier;
        @SerializedName("landNum")
        public int landNum;
        @SerializedName("landLevel")
        public int landLevel;
        @SerializedName("fenhongLevel")
        public int fenhongLevel;
        @SerializedName("fenhongLevelj")
        public int fenhongLevelj;
        @SerializedName("shifei")
        public int shifei;
        @SerializedName("doglevel")
        public int doglevel;
    }
}
