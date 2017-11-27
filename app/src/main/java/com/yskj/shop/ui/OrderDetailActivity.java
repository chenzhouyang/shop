package com.yskj.shop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.adapter.OrderGoodsApapter;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Urls;
import com.yskj.shop.dialog.OrderWebDialog;
import com.yskj.shop.entity.ExPressageEntity;
import com.yskj.shop.entity.OrderListEntity;
import com.yskj.shop.login.LoginActivity;
import com.yskj.shop.utils.DateUtilsOrder;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.StringUtils;
import com.yskj.shop.widget.NoScrollListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


/**
 * Created by YSKJ-02 on 2017/1/17.
 */

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener{
    ImageView imgBack;
    TextView txtTitle,shrName,orderDz,orderYundou,orderZje,orderYf,orderZongjixiaofei,
            txtWuliuhao,orderSj,orderReceiveGoods,order_sn;
    NoScrollListView goodsListDetails;
    LinearLayout layoutLogistics;
    LinearLayout linearLayout;
    LinearLayout adressLl;
    private ExPressageEntity exPressageEntity;
    private OrderListEntity.DataBean.OrdersListBean orderListBeen;
    private int ordertype;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private int id;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orchard_orderdetial);
        iniview();
        getorderlist();
    }

    /**
     * 实例化组件
     */
    private void iniview() {
        AppManager.getInstance().killActivity(LoginActivity.class);
        order_sn = (TextView) findViewById(R.id.order_sn);
        imgBack = (ImageView) findViewById(R.id.img_back);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        shrName = (TextView) findViewById(R.id.shr_name);
        orderDz = (TextView) findViewById(R.id.order_dz);
        orderYundou = (TextView) findViewById(R.id.order_yundou);
        orderZje = (TextView) findViewById(R.id.order_zje);
        orderYf = (TextView) findViewById(R.id.order_yf);
        orderZongjixiaofei = (TextView) findViewById(R.id.order_zongjixiaofei);
        txtWuliuhao = (TextView) findViewById(R.id.txt_wuliuhao);
        orderSj = (TextView) findViewById(R.id.order_sj);
        orderReceiveGoods = (TextView) findViewById(R.id.order_receive_goods);
        goodsListDetails = (NoScrollListView) findViewById(R.id.goods_list_details);
        layoutLogistics = (LinearLayout) findViewById(R.id.layout_logistics);
        layoutLogistics.setOnClickListener(this);
        orderReceiveGoods.setOnClickListener(this);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        adressLl = (LinearLayout) findViewById(R.id.adress_ll);
        txtTitle.setText("订单详情");
        imgBack.setOnClickListener(this);
    }

    private void getorderlist() {
        orderListBeen = (OrderListEntity.DataBean.OrdersListBean) getIntent().getSerializableExtra("orderbean");
            adressLl.setVisibility(View.VISIBLE);
            shrName.setText(orderListBeen.shipName + "");
            orderDz.setText(orderListBeen.shippingArea +orderListBeen.shipAddr);
        order_sn.setText(orderListBeen.sn);
        orderYundou.setText((int) orderListBeen.gainedpoint/100+"");
        OrderGoodsApapter apapter = new OrderGoodsApapter(context, orderListBeen.itemList);
        goodsListDetails.setAdapter(apapter);
        goodsListDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(context,CommodityDetailsActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        .putExtra("id",orderListBeen.itemList.get(position).goodsId+""));
            }
        });
        orderZje.setText("￥" + orderListBeen.orderAmount + "");
        orderYf.setText("￥" + StringUtils.getStringtodouble(orderListBeen.orderAmount-orderListBeen.goodsAmount));
        orderZongjixiaofei.setText("￥" + orderListBeen.orderAmount + "");
        if (orderListBeen.payStatus == 0) {
            linearLayout.setVisibility(View.VISIBLE);
            orderReceiveGoods.setText("查看付款账号");
            ordertype = 1;
        }
        orderSj.setText(DateUtilsOrder.timeStampToStr(Long.parseLong(orderListBeen.createTime)));
        if (orderListBeen.shopingOrderStatus == 1) {
            layoutLogistics.setVisibility(View.VISIBLE);
            txtWuliuhao.setText(orderListBeen.shippingType + "");
            linearLayout.setVisibility(View.VISIBLE);
            ordertype = 2;
            orderReceiveGoods.setVisibility(View.GONE);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.order_receive_goods:
                OrderWebDialog dialog = new OrderWebDialog(context);
                dialog.show();
                break;
            case R.id.layout_logistics:
                getexpressage();
                break;

        }
    }
    private void getexpressage(){
        OkHttpUtils.get().url(Urls.RECORDS).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                exPressageEntity = new Gson().fromJson(response,ExPressageEntity.class);
                for (int i = 0; i < exPressageEntity.data.size(); i++) {
                    if(orderListBeen.logiId == exPressageEntity.data.get(i).id){
                        code = exPressageEntity.data.get(i).code;
                    }
                }
                Intent intent1 = new Intent(context, LogisticsActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent1.putExtra("number", orderListBeen.shipNo);
                intent1.putExtra("shipping_code", code);
                intent1.putExtra("shipping_name", orderListBeen.shippingType);
                startActivity(intent1);
            }
        });
    }
}
