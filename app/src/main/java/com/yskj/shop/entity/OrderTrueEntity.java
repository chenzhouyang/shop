package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 作者：陈宙洋
 * 日期：2017/8/18.
 * 描述：
 */

public class OrderTrueEntity implements Serializable {


    @SerializedName("data")
    public DataBean data;
    @SerializedName("message")
    public String message;
    @SerializedName("result")
    public int result;

    public static class DataBean implements Serializable{
        @SerializedName("order")
        public OrderBean order;

        public static class OrderBean implements Serializable{
            @SerializedName("act_discount")
            public int actDiscount;
            @SerializedName("activity_point")
            public int activityPoint;
            @SerializedName("address_id")
            public int addressId;
            @SerializedName("admin_remark")
            public String adminRemark;
            @SerializedName("allocation_time")
            public int allocationTime;
            @SerializedName("bonus_id")
            public int bonusId;
            @SerializedName("cancel_reason")
            public String cancelReason;
            @SerializedName("consumepoint")
            public int consumepoint;
            @SerializedName("create_time")
            public int createTime;
            @SerializedName("depotid")
            public int depotid;
            @SerializedName("disabled")
            public int disabled;
            @SerializedName("discount")
            public int discount;
            @SerializedName("fields")
            public FieldsBean fields;
            @SerializedName("gainedpoint")
            public int gainedpoint;
            @SerializedName("gift_id")
            public int giftId;
            @SerializedName("goods")
            public String goods;
            @SerializedName("goods_amount")
            public double goodsAmount;
            @SerializedName("goods_num")
            public int goodsNum;
            @SerializedName("isCod")
            public boolean isCod;
            @SerializedName("isOnlinePay")
            public boolean isOnlinePay;
            @SerializedName("is_cancel")
            public int isCancel;
            @SerializedName("is_comment")
            public int isComment;
            @SerializedName("is_online")
            public int isOnline;
            @SerializedName("is_protect")
            public int isProtect;
            @SerializedName("itemList")
            public Object itemList;
            @SerializedName("items_json")
            public String itemsJson;
            @SerializedName("logi_id")
            public int logiId;
            @SerializedName("logi_name")
            public String logiName;
            @SerializedName("memberAddress")
            public MemberAddressBean memberAddress;
            @SerializedName("member_id")
            public int memberId;
            @SerializedName("needPayMoney")
            public double needPayMoneyOrder;
            @SerializedName("need_pay_money")
            public double needPayMoney;
            @SerializedName("orderItemList")
            public Object orderItemList;
            @SerializedName("orderStatus")
            public String orderStatus;
            @SerializedName("orderType")
            public String orderType;
            @SerializedName("order_amount")
            public double orderAmount;
            @SerializedName("order_exchange_point")
            public int orderExchangePoint;
            @SerializedName("order_id")
            public int orderId;
            @SerializedName("orderprice")
            public OrderpriceBean orderprice;
            @SerializedName("payStatus")
            public String payStatusOrder;
            @SerializedName("pay_status")
            public int payStatus;
            @SerializedName("payment_account")
            public String paymentAccount;
            @SerializedName("payment_id")
            public int paymentId;
            @SerializedName("payment_name")
            public String paymentName;
            @SerializedName("payment_type")
            public String paymentType;
            @SerializedName("paymoney")
            public double paymoney;
            @SerializedName("protect_price")
            public double protectPrice;
            @SerializedName("regionid")
            public int regionid;
            @SerializedName("remark")
            public String remark;
            @SerializedName("sale_cmpl")
            public int saleCmpl;
            @SerializedName("sale_cmpl_time")
            public int saleCmplTime;
            @SerializedName("shipStatus")
            public String shipStatusOrder;
            @SerializedName("ship_addr")
            public String shipAddr;
            @SerializedName("ship_cityid")
            public int shipCityid;
            @SerializedName("ship_day")
            public String shipDay;
            @SerializedName("ship_email")
            public String shipEmail;
            @SerializedName("ship_mobile")
            public String shipMobile;
            @SerializedName("ship_name")
            public String shipName;
            @SerializedName("ship_no")
            public String shipNo;
            @SerializedName("ship_provinceid")
            public int shipProvinceid;
            @SerializedName("ship_regionid")
            public int shipRegionid;
            @SerializedName("ship_status")
            public int shipStatus;
            @SerializedName("ship_tel")
            public String shipTel;
            @SerializedName("ship_time")
            public String shipTime;
            @SerializedName("ship_townid")
            public int shipTownid;
            @SerializedName("ship_zip")
            public String shipZip;
            @SerializedName("shipping_amount")
            public int shippingAmount;
            @SerializedName("shipping_area")
            public String shippingArea;
            @SerializedName("shipping_id")
            public int shippingId;
            @SerializedName("shipping_type")
            public String shippingType;
            @SerializedName("signing_time")
            public int signingTime;
            @SerializedName("sn")
            public String sn;
            @SerializedName("status")
            public int status;
            @SerializedName("the_sign")
            public String theSign;
            @SerializedName("uname")
            public String uname;
            @SerializedName("weight")
            public int weight;

            public static class FieldsBean implements Serializable{
            }

            public static class MemberAddressBean implements Serializable{
                /**
                 * addr : vhh
                 * addr_id : 9
                 * addressToBeEdit :
                 * city : 淮安市
                 * city_id : 944
                 * country :
                 * def_addr : 1
                 * isDel : 0
                 * member_id : 5
                 * mobile : 15936365921
                 * name : ghkkkkk
                 * province : 江苏省
                 * province_id : 864
                 * region : 涟水县
                 * region_id : 950
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

            public static class OrderpriceBean implements Serializable{
                @SerializedName("actDiscount")
                public int actDiscount;
                @SerializedName("act_free_ship")
                public int actFreeShip;
                @SerializedName("activity_point")
                public int activityPoint;
                @SerializedName("bonus_id")
                public int bonusId;
                @SerializedName("credit_pay")
                public int creditPay;
                @SerializedName("discountItem")
                public DiscountItemBean discountItem;
                @SerializedName("discountPrice")
                public double discountPrice;
                @SerializedName("exchange_point")
                public int exchangePoint;
                @SerializedName("gift_id")
                public int giftId;
                @SerializedName("goodsPrice")
                public double goodsPrice;
                @SerializedName("is_free_ship")
                public int isFreeShip;
                @SerializedName("needPayMoney")
                public double needPayMoney;
                @SerializedName("orderPrice")
                public double orderPrice;
                @SerializedName("point")
                public int point;
                @SerializedName("shippingPrice")
                public double shippingPrice;
                @SerializedName("weight")
                public int weight;

                public static class DiscountItemBean implements Serializable{
                }
            }
        }
    }
}
