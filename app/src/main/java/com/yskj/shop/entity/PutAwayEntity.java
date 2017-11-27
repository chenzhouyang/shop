package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：陈宙洋
 * 日期：2017/8/15.
 * 描述：最新商品数据
 */

public class PutAwayEntity {
    /**
     * result : 1
     * data : [{"goods_id":276,"name":"花花公子男包商务 单肩包休闲横款大包 潮男皮包公文包手提包男士","price":0,"thumbnail":"http://static.v5.javamall.com.cn/attachment/goods/201511240912160192_thumbnail.jpg","tag_id":100010,"ordernum":0},{"goods_id":277,"name":"千百惠女包2015秋冬新款迷你手提包波士顿枕头包单肩斜挎包女小包 ","price":189,"thumbnail":"http://static.v5.javamall.com.cn/attachment/goods/201511240915306549_thumbnail.jpg","tag_id":100010,"ordernum":0},{"goods_id":278,"name":"GUY LAROCHE/姬龙雪女包2015秋冬新款潮牛皮奢侈品手提包女手柄包 ","price":1600,"thumbnail":"http://static.v5.javamall.com.cn/attachment/goods/201511240918550205_thumbnail.jpg","tag_id":100010,"ordernum":0},{"goods_id":279,"name":"【甄选】七款可选周大福首饰3D福星宝宝足金黄金吊坠R ","price":1800,"thumbnail":"http://static.v5.javamall.com.cn/attachment/goods/201511240921471679_thumbnail.jpg","tag_id":100010,"ordernum":0},{"goods_id":280,"name":"威摩士2015双面呢大衣纯羊毛女士毛呢大衣毛呢外套高端大衣外套女 ","price":1680,"thumbnail":"http://static.v5.javamall.com.cn/attachment/goods/201511241134546958_thumbnail.jpg","tag_id":100010,"ordernum":0},{"goods_id":281,"name":"sofa2015秋冬新款纯色不对称宽松版型九分袖套头针织衫 ","price":430,"thumbnail":"http://static.v5.javamall.com.cn/attachment/goods/201511241157540387_thumbnail.jpg","tag_id":100010,"ordernum":0},{"goods_id":282,"name":"云思木想2015秋冬新款修身女装毛呢外套纯黑时尚中长款外套71355 ","price":300,"thumbnail":"http://static.v5.javamall.com.cn/attachment/goods/201511241204330677_thumbnail.jpg","tag_id":100010,"ordernum":0},{"goods_id":283,"name":"2015秋冬新款女装套装裙长袖打底裙两件套套装熟女冬季连衣裙 冬 ","price":310,"thumbnail":"http://static.v5.javamall.com.cn/attachment/goods/201511241211280361_thumbnail.jpg","tag_id":100010,"ordernum":0},{"goods_id":284,"name":"大码男装加肥加大韩版羽绒衣男士羽绒服外套冬 ","price":270,"thumbnail":"http://static.v5.javamall.com.cn/attachment/goods/201511241219082665_thumbnail.jpg","tag_id":100010,"ordernum":0},{"goods_id":285,"name":"THEPANG 韩版大码男装秋季加肥加大针织衫开衫 潮胖子男士毛衣","price":300,"thumbnail":"http://localhost:8080/statics/attachment//goods/2017/8/14/17//09018130_thumbnail.jpg","tag_id":100010,"ordernum":null}]
     */

    @SerializedName("result")
    public int result;
    @SerializedName("data")
    public List<DataBean> data;

    public static class DataBean {
        /**
         * goods_id : 276
         * name : 花花公子男包商务 单肩包休闲横款大包 潮男皮包公文包手提包男士
         * price : 0
         * thumbnail : http://static.v5.javamall.com.cn/attachment/goods/201511240912160192_thumbnail.jpg
         * tag_id : 100010
         * ordernum : 0
         */

        @SerializedName("goods_id")
        public int goodsId;
        @SerializedName("name")
        public String name;
        @SerializedName("price")
        public double price;
        @SerializedName("thumbnail")
        public String thumbnail;
        @SerializedName("tag_id")
        public int tagId;
        @SerializedName("ordernum")
        public int ordernum;
        @SerializedName("point")
        public int point;
        @SerializedName("small")
        public String small;
        @SerializedName("original")
        public String original;

    }
}
