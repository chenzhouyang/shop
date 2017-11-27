package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 作者：陈宙洋
 * 日期：2017/8/17.
 * 描述：收货地址列表实体
 */

public class LocationListEntity implements Serializable {
    /**
     * result : 1
     * data : {"addressList":[{"addr":"阿斯顿权威水淀粉阿斯顿","addr_id":5,"addressToBeEdit":"","city":"市辖区","city_id":843,"country":"","def_addr":1,"isDel":0,"member_id":3,"mobile":"15225700234","name":"Asd萨德","province":"上海市","province_id":842,"region":"黄浦区","region_id":844,"remark":"","shipAddressName":"","tel":"","town":"","town_id":-1,"zip":""}],"defaultAddress":{"addr":"阿斯顿权威水淀粉阿斯顿","addr_id":5,"addressToBeEdit":"","city":"市辖区","city_id":843,"country":"","def_addr":1,"isDel":0,"member_id":3,"mobile":"15225700234","name":"Asd萨德","province":"上海市","province_id":842,"region":"黄浦区","region_id":844,"remark":"","shipAddressName":"","tel":"","town":"","town_id":-1,"zip":""}}
     */

    @SerializedName("result")
    public int result;
    @SerializedName("data")
    public DataBean data;

    public static class DataBean implements Serializable{
        /**
         * addressList : [{"addr":"阿斯顿权威水淀粉阿斯顿","addr_id":5,"addressToBeEdit":"","city":"市辖区","city_id":843,"country":"","def_addr":1,"isDel":0,"member_id":3,"mobile":"15225700234","name":"Asd萨德","province":"上海市","province_id":842,"region":"黄浦区","region_id":844,"remark":"","shipAddressName":"","tel":"","town":"","town_id":-1,"zip":""}]
         * defaultAddress : {"addr":"阿斯顿权威水淀粉阿斯顿","addr_id":5,"addressToBeEdit":"","city":"市辖区","city_id":843,"country":"","def_addr":1,"isDel":0,"member_id":3,"mobile":"15225700234","name":"Asd萨德","province":"上海市","province_id":842,"region":"黄浦区","region_id":844,"remark":"","shipAddressName":"","tel":"","town":"","town_id":-1,"zip":""}
         */

        @SerializedName("defaultAddress")
        public DefaultAddressBean defaultAddress;
        @SerializedName("addressList")
        public ArrayList<AddressListBean> addressList;

        public static class DefaultAddressBean implements Serializable{
            /**
             * addr : 阿斯顿权威水淀粉阿斯顿
             * addr_id : 5
             * addressToBeEdit :
             * city : 市辖区
             * city_id : 843
             * country :
             * def_addr : 1
             * isDel : 0
             * member_id : 3
             * mobile : 15225700234
             * name : Asd萨德
             * province : 上海市
             * province_id : 842
             * region : 黄浦区
             * region_id : 844
             * remark :
             * shipAddressName :
             * tel :
             * town :
             * town_id : -1
             * zip :
             */

            @SerializedName("addr")
            public String addr;
            @SerializedName("addr_id")
            public int addrId;
            @SerializedName("addressToBeEdit")
            public String addressToBeEdit;
            @SerializedName("city")
            public String city;
            @SerializedName("city_id")
            public int cityId;
            @SerializedName("country")
            public String country;
            @SerializedName("def_addr")
            public int defAddr;
            @SerializedName("isDel")
            public int isDel;
            @SerializedName("member_id")
            public int memberId;
            @SerializedName("mobile")
            public String mobile;
            @SerializedName("name")
            public String name;
            @SerializedName("province")
            public String province;
            @SerializedName("province_id")
            public int provinceId;
            @SerializedName("region")
            public String region;
            @SerializedName("region_id")
            public int regionId;
            @SerializedName("remark")
            public String remark;
            @SerializedName("shipAddressName")
            public String shipAddressName;
            @SerializedName("tel")
            public String tel;
            @SerializedName("town")
            public String town;
            @SerializedName("town_id")
            public int townId;
            @SerializedName("zip")
            public String zip;
        }

        public static class AddressListBean implements Serializable{
            /**
             * addr : 阿斯顿权威水淀粉阿斯顿
             * addr_id : 5
             * addressToBeEdit :
             * city : 市辖区
             * city_id : 843
             * country :
             * def_addr : 1
             * isDel : 0
             * member_id : 3
             * mobile : 15225700234
             * name : Asd萨德
             * province : 上海市
             * province_id : 842
             * region : 黄浦区
             * region_id : 844
             * remark :
             * shipAddressName :
             * tel :
             * town :
             * town_id : -1
             * zip :
             */

            @SerializedName("addr")
            public String addr;
            @SerializedName("addr_id")
            public int addrId;
            @SerializedName("addressToBeEdit")
            public String addressToBeEdit;
            @SerializedName("city")
            public String city;
            @SerializedName("city_id")
            public int cityId;
            @SerializedName("country")
            public String country;
            @SerializedName("def_addr")
            public int defAddr;
            @SerializedName("isDel")
            public int isDel;
            @SerializedName("member_id")
            public int memberId;
            @SerializedName("mobile")
            public String mobile;
            @SerializedName("name")
            public String name;
            @SerializedName("province")
            public String province;
            @SerializedName("province_id")
            public int provinceId;
            @SerializedName("region")
            public String region;
            @SerializedName("region_id")
            public int regionId;
            @SerializedName("remark")
            public String remark;
            @SerializedName("shipAddressName")
            public String shipAddressName;
            @SerializedName("tel")
            public String tel;
            @SerializedName("town")
            public String town;
            @SerializedName("town_id")
            public int townId;
            @SerializedName("zip")
            public String zip;
        }
    }
}
