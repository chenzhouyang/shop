package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YSKJ-JH on 2017/1/20.
 */

public class NewsBoxEntity implements Serializable{
    /**
     * code : 0
     * message : 成功
     * data : [{"id":3,"createDate":"2017-07-01 12:14:27","modifyDate":null,"enable":true,"deleted":false,"content":"       中国，是以华夏文明为源泉、中华文化为基础，并以汉族为主体民族的多民族国家，通用汉语、汉字，汉族与少数民族被统称为\u201c中华民族\u201d，又自称为炎黄子孙、龙的传人。\r\n中国是世界四大文明古国之一，有着悠久的历史，距今约5000年前，以中原地区为中心开始出现聚落组织进而形成国家，后历经多次民族交融和朝代更迭，直至形成多民族国家的大一统局面。20世纪初辛亥革命后，君主政体退出历史舞台，共和政体建立。1949年中华人民共和国成立后，在中国大陆建立了人民代表大会制度的政体。\r\n中国疆域辽阔、民族众多，先秦时期的华夏族在中原地区繁衍生息，到了汉代通过文化交融使汉族正式成型，奠定了中国主体民族的基础。后又通过与周边民族的交融，逐步形成统一多民族国家的局面，而人口也不断攀升，宋代中国人口突破一亿，清朝时期人口突破四亿，到2005年中国人口已突破十三亿。\r\n中国文化渊远流长、博大精深、绚烂多彩，是东亚文化圈的文化宗主国，在世界文化体系内占有重要地位，由于各地的地理位置、自然条件的差异，人文、经济方面也各有特点。传统文化艺术形式有诗词、戏曲、书法、国画等，而春节、元宵、清明、端午、中秋、重阳等则是中国重要的传统节日"},{"id":2,"createDate":"2017-07-01 12:12:00","modifyDate":null,"enable":true,"deleted":false,"content":"\r\n                               我们是什么什么 \r\n"},{"id":1,"createDate":"2017-07-01 12:10:35","modifyDate":null,"enable":true,"deleted":false,"content":"\r\n                                dsadsadsadsadsadsadsadsadsadsadsad"}]
     */

    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public List<DataBean> data;

    public static class DataBean implements Serializable{
        /**
         * id : 3
         * createDate : 2017-07-01 12:14:27
         * modifyDate : null
         * enable : true
         * deleted : false
         * content :        中国，是以华夏文明为源泉、中华文化为基础，并以汉族为主体民族的多民族国家，通用汉语、汉字，汉族与少数民族被统称为“中华民族”，又自称为炎黄子孙、龙的传人。
         中国是世界四大文明古国之一，有着悠久的历史，距今约5000年前，以中原地区为中心开始出现聚落组织进而形成国家，后历经多次民族交融和朝代更迭，直至形成多民族国家的大一统局面。20世纪初辛亥革命后，君主政体退出历史舞台，共和政体建立。1949年中华人民共和国成立后，在中国大陆建立了人民代表大会制度的政体。
         中国疆域辽阔、民族众多，先秦时期的华夏族在中原地区繁衍生息，到了汉代通过文化交融使汉族正式成型，奠定了中国主体民族的基础。后又通过与周边民族的交融，逐步形成统一多民族国家的局面，而人口也不断攀升，宋代中国人口突破一亿，清朝时期人口突破四亿，到2005年中国人口已突破十三亿。
         中国文化渊远流长、博大精深、绚烂多彩，是东亚文化圈的文化宗主国，在世界文化体系内占有重要地位，由于各地的地理位置、自然条件的差异，人文、经济方面也各有特点。传统文化艺术形式有诗词、戏曲、书法、国画等，而春节、元宵、清明、端午、中秋、重阳等则是中国重要的传统节日
         */

        @SerializedName("id")
        public int id;
        @SerializedName("createDate")
        public String createDate;
        @SerializedName("modifyDate")
        public Object modifyDate;
        @SerializedName("enable")
        public boolean enable;
        @SerializedName("deleted")
        public boolean deleted;
        @SerializedName("content")
        public String content;
    }

//    /**
//     * code : 0
//     * message : 成功
//     * data : {"cursor":0,"count":10,"list":[{"id":16,"type":0,"content":"提现申请成功，提现金额200.0，预计48小时内(节假日顺延)到账，扣除手续费3%","hasRead":true,"createTime":"2017-01-19 14:49:34"}]}
//     */
//
//    @SerializedName("code")
//    public int code;
//    @SerializedName("message")
//    public String message;
//    @SerializedName("data")
//    public DataBean data;
//
//    public static class DataBean {
//        /**
//         * cursor : 0
//         * count : 10
//         * list : [{"id":16,"type":0,"content":"提现申请成功，提现金额200.0，预计48小时内(节假日顺延)到账，扣除手续费3%","hasRead":true,"createTime":"2017-01-19 14:49:34"}]
//         */
//
//        @SerializedName("cursor")
//        public int cursor;
//        @SerializedName("count")
//        public int count;
//        @SerializedName("list")
//        public ArrayList<ListBean> list;
//    }
//    public static class ListBean {
//        /**
//         * id : 16
//         * type : 0
//         * content : 提现申请成功，提现金额200.0，预计48小时内(节假日顺延)到账，扣除手续费3%
//         * hasRead : true
//         * createTime : 2017-01-19 14:49:34
//         */
//
//        @SerializedName("id")
//        public String id;
//        @SerializedName("type")
//        public int type;
//        @SerializedName("content")
//        public String content;
//        @SerializedName("hasRead")
//        public boolean hasRead;
//        @SerializedName("createTime")
//        public String createTime;
//    }
}
