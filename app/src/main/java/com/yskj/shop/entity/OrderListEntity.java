package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：陈宙洋
 * 日期：2017/8/17.
 * 描述：订单列表实体
 */

public class OrderListEntity implements Serializable{
    @SerializedName("result")
    public int result;
    @SerializedName("data")
    public DataBean data;

    public static class DataBean implements Serializable{
        @SerializedName("PAY_YES")
        public int PAYYES;
        @SerializedName("SHIP_ROG")
        public int SHIPROG;
        @SerializedName("ORDER_PAY")
        public int ORDERPAY;
        @SerializedName("SHIP_YES")
        public int SHIPYES;
        @SerializedName("SHIP_NO")
        public int SHIPNO;
        @SerializedName("pageSize")
        public int pageSize;
        @SerializedName("totalCount")
        public int totalCount;
        @SerializedName("ORDER_ROG")
        public int ORDERROG;
        @SerializedName("ORDER_SHIP")
        public int ORDERSHIP;
        @SerializedName("ORDER_NOT_PAY")
        public int ORDERNOTPAY;
        @SerializedName("ORDER_CONFIRM")
        public int ORDERCONFIRM;
        @SerializedName("ORDER_COMPLETE")
        public int ORDERCOMPLETE;
        @SerializedName("ORDER_MAINTENANCE")
        public int ORDERMAINTENANCE;
        @SerializedName("PAY_NO")
        public int PAYNO;
        @SerializedName("PAY_PARTIAL_PAYED")
        public int PAYPARTIALPAYED;
        @SerializedName("page")
        public int page;
        @SerializedName("ORDER_CANCELLATION")
        public int ORDERCANCELLATION;
        @SerializedName("SHIP_PARTIAL_CANCEL")
        public int SHIPPARTIALCANCEL;
        @SerializedName("status")
        public int status;
        @SerializedName("ordersList")
        public ArrayList<OrdersListBean> ordersList;

        public static class OrdersListBean implements Serializable {
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
            public String createTime;
            @SerializedName("depotid")
            public int depotid;
            @SerializedName("disabled")
            public int disabled;
            @SerializedName("discount")
            public int discount;
            @SerializedName("fields")
            public FieldsBean fields;
            @SerializedName("gainedpoint")
            public double gainedpoint;
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
            public double needPayMoney;
            @SerializedName("need_pay_money")
            public double needPayMoneys;
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
            public String payOrderStatus;
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
            public int protectPrice;
            @SerializedName("regionid")
            public int regionid;
            @SerializedName("remark")
            public String remark;
            @SerializedName("sale_cmpl")
            public int saleCmpl;
            @SerializedName("sale_cmpl_time")
            public int saleCmplTime;
            @SerializedName("shipStatus")
            public String shoppingStatus;
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
            public int shopingOrderStatus;
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
            @SerializedName("itemList")
            public ArrayList<ItemListBean> itemList;
            @SerializedName("orderItemList")
            public List<?> orderItemList;

            public static class FieldsBean implements Serializable{
            }

            public static class MemberAddressBean implements Serializable{
                /**
                 * addr :
                 * addr_id : 0
                 * addressToBeEdit :
                 * city :
                 * city_id : 0
                 * country :
                 * def_addr : 0
                 * isDel : 0
                 * member_id : 0
                 * mobile :
                 * name :
                 * province :
                 * province_id : 0
                 * region :
                 * region_id : 0
                 * remark :
                 * shipAddressName :
                 * tel :
                 * town :
                 * town_id : 0
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
                /**
                 * actDiscount : 0
                 * act_free_ship : 0
                 * activity_point : 0
                 * bonus_id : 0
                 * credit_pay : 0
                 * discountItem : {}
                 * discountPrice : 0
                 * exchange_point : 0
                 * gift_id : 0
                 * goodsPrice : 0
                 * is_free_ship : 0
                 * needPayMoney : 0
                 * orderPrice : 0
                 * point : 0
                 * shippingPrice : 0
                 * weight : 0
                 */

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
                public int discountPrice;
                @SerializedName("exchange_point")
                public int exchangePoint;
                @SerializedName("gift_id")
                public int giftId;
                @SerializedName("goodsPrice")
                public int goodsPrice;
                @SerializedName("is_free_ship")
                public int isFreeShip;
                @SerializedName("needPayMoney")
                public int needPayMoneyes;
                @SerializedName("orderPrice")
                public int orderPrice;
                @SerializedName("point")
                public int point;
                @SerializedName("shippingPrice")
                public int shippingPrice;
                @SerializedName("weight")
                public int weight;

                public static class DiscountItemBean implements Serializable{
                }
            }

            public static class ItemListBean implements Serializable{
                /**
                 * addon :
                 * cat_id : 6
                 * change_goods_id : 0
                 * change_goods_name :
                 * depotId :
                 * exchange :
                 * exchange_point : 0
                 * fields : {}
                 * gainedpoint : 0
                 * goods_id : 9
                 * goods_type : 0
                 * image : http://static.v4.javamall.com.cn/attachment/goods/201202221444355358_thumbnail.jpg
                 * item_id : 7
                 * name : 旺旺挑豆随手包海苔花生45g/袋
                 * num : 3
                 * order_id : 7
                 * other :
                 * price : 2.7
                 * product_id : 9
                 * ship_num : 0
                 * sn : 0000567840
                 * snapshot_id : 3
                 * snapshot_switch : 0
                 * state : 0
                 * store : 0
                 * unit :
                 */

                @SerializedName("addon")
                public String addon;
                @SerializedName("cat_id")
                public int catId;
                @SerializedName("change_goods_id")
                public int changeGoodsId;
                @SerializedName("change_goods_name")
                public String changeGoodsName;
                @SerializedName("depotId")
                public String depotId;
                @SerializedName("exchange")
                public String exchange;
                @SerializedName("exchange_point")
                public int exchangePoint;
                @SerializedName("fields")
                public FieldsBeanX fields;
                @SerializedName("gainedpoint")
                public int gainedpoint;
                @SerializedName("goods_id")
                public int goodsId;
                @SerializedName("goods_type")
                public int goodsType;
                @SerializedName("image")
                public String image;
                @SerializedName("item_id")
                public int itemId;
                @SerializedName("name")
                public String name;
                @SerializedName("num")
                public int num;
                @SerializedName("order_id")
                public int orderId;
                @SerializedName("other")
                public String other;
                @SerializedName("price")
                public double price;
                @SerializedName("product_id")
                public int productId;
                @SerializedName("ship_num")
                public int shipNum;
                @SerializedName("sn")
                public String sn;
                @SerializedName("snapshot_id")
                public int snapshotId;
                @SerializedName("snapshot_switch")
                public int snapshotSwitch;
                @SerializedName("state")
                public int state;
                @SerializedName("store")
                public int store;
                @SerializedName("unit")
                public String unit;

                public static class FieldsBeanX implements Serializable{
                }
            }
        }
    }
}
