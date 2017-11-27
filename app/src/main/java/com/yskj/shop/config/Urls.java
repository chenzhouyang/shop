package com.yskj.shop.config;

/**
 * 创建日期 2017/6/12on 10:19.
 * 描述：
 * 作者：姜贺YSKJ-JH
 */

public class Urls {
    //登陆
    public static final String LOGIN = Ips.API_URL+"/user/login";
    //注册
    public static final String REGISTER = Ips.API_URL + "/user/registry";
    //查询用户信息
    public static final String GETUSERINFO = Ips.API_URL + "/user/info";
    //首页商品分类列表测试数据
    public static final String GOODSCATEGORYLIST = Ips.API_SHOPURL +"/api/shop/category/get-all-by-parentid-json.do";
    //查询商品
    public static final String MAINPUTAWAY = Ips.API_SHOPURL + "/api/shop/goods/tag-list.do";
    //搜索
    public static final String SEARCH = Ips.API_SHOPURL + "/api/mobile/goods/search.do";
    //商品详情
    public static final String COMMODITY = Ips.API_SHOPURL + "/api/mobile/goods/detail.do";
    //购物车数据
    public static final String SHOPPINGCART = Ips.API_SHOPURL + "/api/mobile/cart/get-cart-data.do";
    //购物车全选
    public static final String SHOPPINGCARTALLCART = Ips.API_SHOPURL + "/api/mobile/cart/check-all.do";
    //删除购物车某个商品
    public static final String DELETECART = Ips.API_SHOPURL + "/api/mobile/cart/delete.do";
    //购物车商品修改数量
    public static final String UPDATENUM = Ips.API_SHOPURL + "/api/mobile/cart/update-num.do";
    //添加购物车有规格
    public static final String ADDSHOPPINGCART = Ips.API_SHOPURL + "/api/mobile/cart/add-product.do";
    //添加购物车无规格
    public static final String ADDSHOPPGOODS = Ips.API_SHOPURL + "/api/mobile/cart/add-goods.do";
    //购物车资金请求
    public static final String CAPITAL = Ips.API_SHOPURL + "/api/mobile/cart/get-total.do";
    //购物车单选
    public static final String CHECKCART = Ips.API_SHOPURL + "/api/mobile/cart/check-product.do";
    //订单列表
    public static final String ORDERLIST = Ips.API_SHOPURL + "/api/mobile/member/order-list.do";
    //查询快递列表
    public static final String RECORDS = Ips.API_SHOPURL + "/api/mobile/order/list-logi.do";
    //查询物流信息
    public static final String LOGISTICS = "http://m.kuaidi100.com/query?";
    //查询收货地址列表
    public static final String LOCATIONLIST = Ips.API_SHOPURL +"/api/mobile/member-address/list.do";
    //更改默认地址
    public static final String MORENADDRES = Ips.API_SHOPURL +"/api/mobile/member-address/isdefaddr.do";
    //删除地址
    public static final String DELETEADDRESS = Ips.API_SHOPURL +"/api/mobile/member-address/delete.do";
    //获取城市列表
    public static final String CITYLIST = Ips.API_SHOPURL +"/api/base/region/get-children.do";
    //新增收货地址
    public static final String ADDRESSCITYLIST = Ips.API_SHOPURL +"/api/mobile/member-address/add.do";
    //修改收货地址
    public static final String ALTER = Ips.API_SHOPURL +"/api/mobile/member-address/edit.do";
    //确认订单
    public static final String TRUEORSER = Ips.API_SHOPURL +"/api/mobile/cart/checkout-total.do";
    //创建订单
    public static final String SETORDER = Ips.API_SHOPURL +"/api/mobile/order/create.do";
    //刷新token值
    public static final String REFRE = Ips.API_URL+"api/v1/auth/oauth/token";
    //删除银行卡
    public static final String DELBANK = Ips.API_URL + "/bank/delete";
    //查询银行卡
    public static final String DELBANKLIST = Ips.API_URL + "/bank/list";
    //提现记录
    public static final String RECORDLIST = Ips.API_URL + "/withdraw/list";
    //添加银行卡
    public static final String BANKLIST = Ips.API_URL + "/bank/add";
    //更改银行卡信息
    public static final String UPDATEBANKLIST = Ips.API_URL + "/bank/update";
    //查询银行卡信息
    public static final String BANKLISTDE = Ips.API_URL + "/bank/detail";
    //提现
    public static final String REEORD = Ips.API_URL + "/withdraw/add";
    //实名认证
    public static final String IDENRITY = Ips.API_URL + "/realName/add";
    //查询实名认证
    public static final String IDENRITYDETAIL = Ips.API_URL + "/realName/detail";
    //获取折线数据
    public static final String HISTORY = Ips.API_URL + "/trade/history";
    //我的挂单
    public static final String PUT = Ips.API_URL + "/trade/guadan";
       //撤销挂单
    public static final String CANCEL = Ips.API_URL + "/trade/cancel";
//我的成交记录
public static final String DEALS = Ips.API_URL + "/trade/deals";
    //购买挂单
    public static final String TRADEBUY = Ips.API_URL + "/trade/buy";
    //出售挂单
    public static final String SELL = Ips.API_URL + "/trade/sell";
    //修改密码
    public static final String CHANDPWD = Ips.API_URL + "/user/pwd_reset";
    //查看汇款单
    public static final String ORDERPCFG = Ips.API_SHOPURL + "/member/order-pcfg.html";
    //实时价格
    public static final String PRICE = Ips.API_URL + "/trade/price";
    //粉丝查询
    public static final String FANS = Ips.API_URL+"/user/fun";
    //分配粉丝
    public static final String ALLOTFANS = Ips.API_URL+"/user/fen";
    //转账
    public static final String ACCOUNTSAVE = Ips.API_URL+"/transfer/save";
    //购买土地
    public static final String SOILPAY = Ips.API_URL+"/shop/pay";
    //转账记录
    public static final String ACCOUNTLIST = Ips.API_URL+"/transfer/list";
    //清空购物车
    public static final String CLENCART = Ips.API_SHOPURL+"/api/mobile/cart/clean.do";
}
