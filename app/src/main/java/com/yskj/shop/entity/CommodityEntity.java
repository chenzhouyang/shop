package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：陈宙洋
 * 日期：2017/8/16.
 * 描述：商品详情数据
 */

public class CommodityEntity {


    @SerializedName("result")
    public int result;
    @SerializedName("data")
    public DataBean data;

    public static class DataBean {


        @SerializedName("price")
        public double price;
        @SerializedName("intro")
        public String intro;
        @SerializedName("name")
        public String name;
        @SerializedName("haveSpec")
        public int haveSpec;
        @SerializedName("point")
        public double point;
        @SerializedName("galleryList")
        public List<GalleryListBean> galleryList;
        @SerializedName("specList")
        public List<SpecListBean> specList;
        @SerializedName("productList")
        public List<ProductListBean> productList;

        public static class GalleryListBean {
            /**
             * big : http://localhost:8080/statics/attachment//goods/2017/8/14/17//09018130_big.jpg
             * goods_id : 285
             * img_id : 402
             * isdefault : 1
             * original : http://localhost:8080/statics/attachment//goods/2017/8/14/17//09018130.jpg
             * small : http://localhost:8080/statics/attachment//goods/2017/8/14/17//09018130_small.jpg
             * sort : 1
             * thumbnail : http://localhost:8080/statics/attachment//goods/2017/8/14/17//09018130_thumbnail.jpg
             * tiny : http://localhost:8080/statics/attachment//goods/2017/8/14/17//09018130_tiny.jpg
             */

            @SerializedName("big")
            public String big;
            @SerializedName("goods_id")
            public int goodsId;
            @SerializedName("img_id")
            public int imgId;
            @SerializedName("isdefault")
            public int isdefault;
            @SerializedName("original")
            public String original;
            @SerializedName("small")
            public String small;
            @SerializedName("sort")
            public int sort;
            @SerializedName("thumbnail")
            public String thumbnail;
            @SerializedName("tiny")
            public String tiny;
        }

        public static class SpecListBean {
            /**
             * spec_id : 1
             * spec_memo :
             * spec_name : 颜色
             * spec_show_type : 0
             * spec_type : 1
             * valueList : [{"image":"","inherent_or_add":0,"spec_id":0,"spec_image":"http://static.v4.javamall.com.cn/spec/201003231752553633.gif","spec_order":0,"spec_type":1,"spec_value":"黄色","spec_value_id":41},{"image":"","inherent_or_add":0,"spec_id":0,"spec_image":"http://static.v4.javamall.com.cn/spec/201005191140150035.jpg","spec_order":0,"spec_type":1,"spec_value":"粉黄色","spec_value_id":50},{"image":"","inherent_or_add":0,"spec_id":0,"spec_image":"http://static.v4.javamall.com.cn/spec/201003231759056186.gif","spec_order":0,"spec_type":1,"spec_value":"蓝色","spec_value_id":45}]
             */

            @SerializedName("spec_id")
            public int specId;
            @SerializedName("spec_memo")
            public String specMemo;
            @SerializedName("spec_name")
            public String specName;
            @SerializedName("spec_show_type")
            public int specShowType;
            @SerializedName("spec_type")
            public int specType;
            @SerializedName("valueList")
            public List<ValueListBean> valueList;

            public static class ValueListBean {
                /**
                 * image :
                 * inherent_or_add : 0
                 * spec_id : 0
                 * spec_image : http://static.v4.javamall.com.cn/spec/201003231752553633.gif
                 * spec_order : 0
                 * spec_type : 1
                 * spec_value : 黄色
                 * spec_value_id : 41
                 */

                @SerializedName("image")
                public String image;
                @SerializedName("inherent_or_add")
                public int inherentOrAdd;
                @SerializedName("spec_id")
                public int specId;
                @SerializedName("spec_image")
                public String specImage;
                @SerializedName("spec_order")
                public int specOrder;
                @SerializedName("spec_type")
                public int specType;
                @SerializedName("spec_value")
                public String specValue;
                @SerializedName("spec_value_id")
                public String specValueId;
            }
        }

        public static class ProductListBean {
            /**
             * cost : 300
             * enable_store : 10
             * goodsLvPrices : []
             * goods_id : 285
             * name : THEPANG 韩版大码男装秋季加肥加大针织衫开衫 潮胖子男士毛衣
             * price : 300
             * product_id : 398
             * sn : 201511243123-0
             * specList : [{"image":"","inherent_or_add":0,"spec_id":2,"spec_image":"http://static.v4.javamall.com.cn/spec/spec_def.gif","spec_order":0,"spec_type":0,"spec_value":"均码","spec_value_id":9},{"image":"","inherent_or_add":0,"spec_id":1,"spec_image":"http://static.v4.javamall.com.cn/spec/201003231752553633.gif","spec_order":0,"spec_type":1,"spec_value":"黄色","spec_value_id":41}]
             * specs : 均码、黄色
             * specsvIdJson : [9,41]
             * store : 10
             * weight : 0
             */

            @SerializedName("cost")
            public double cost;
            @SerializedName("enable_store")
            public int enableStore;
            @SerializedName("goods_id")
            public int goodsId;
            @SerializedName("name")
            public String name;
            @SerializedName("price")
            public double price;
            @SerializedName("product_id")
            public int productId;
            @SerializedName("sn")
            public String sn;
            @SerializedName("specs")
            public String specs;
            @SerializedName("specsvIdJson")
            public String specsvIdJson;
            @SerializedName("store")
            public int store;
            @SerializedName("weight")
            public double weight;
            @SerializedName("goodsLvPrices")
            public List<?> goodsLvPrices;
            @SerializedName("specList")
            public List<SpecListBeanX> specList;

            public static class SpecListBeanX {
                /**
                 * image :
                 * inherent_or_add : 0
                 * spec_id : 2
                 * spec_image : http://static.v4.javamall.com.cn/spec/spec_def.gif
                 * spec_order : 0
                 * spec_type : 0
                 * spec_value : 均码
                 * spec_value_id : 9
                 */

                @SerializedName("image")
                public String image;
                @SerializedName("inherent_or_add")
                public int inherentOrAdd;
                @SerializedName("spec_id")
                public int specId;
                @SerializedName("spec_image")
                public String specImage;
                @SerializedName("spec_order")
                public int specOrder;
                @SerializedName("spec_type")
                public int specType;
                @SerializedName("spec_value")
                public String specValue;
                @SerializedName("spec_value_id")
                public int specValueId;
            }
        }
    }
}
