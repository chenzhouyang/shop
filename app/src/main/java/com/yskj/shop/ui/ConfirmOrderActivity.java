package com.yskj.shop.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.adapter.ConfirmOrderAdapter;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.OrderTrueEntity;
import com.yskj.shop.entity.SelectedEntity;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 作者：陈宙洋
 * 日期：2017/8/12.
 * 描述：确认订单
 */

public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener{
    private TextView tvPayOrder,tv_name,tv_number,tv_address,tv_price,tv_expense,tv_total_price,tv_all_price,txtTitle,tv_count;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private int addressId;
    private SelectedEntity selectedEntity;
    private ConfirmOrderAdapter confirmOrderAdapter;
    private ListView listView;
    private EditText tv_confirm_note;
    private OrderTrueEntity orderTrueEntity;
    private boolean isSusess = false;
    private String message;
    private LinearLayout confirm_order;
    private ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_orchard_order);
        iniview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocationList();
    }

    private void iniview() {
        imageBack = (ImageView) findViewById(R.id.img_back);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText("确认订单");
        tv_count = (TextView) findViewById(R.id.tv_count);
        confirm_order = (LinearLayout) findViewById(R.id.confirm_order);
        tvPayOrder = (TextView) findViewById(R.id.tv_pay_order);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_number = (TextView) findViewById(R.id.tv_number);
        listView = (ListView) findViewById(R.id.listView);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_expense = (TextView) findViewById(R.id.tv_expense);
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);
        tv_all_price = (TextView) findViewById(R.id.tv_all_price);
        tv_confirm_note = (EditText) findViewById(R.id.tv_confirm_note);
        tvPayOrder.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        confirm_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_pay_order:
                    setOrder();
                break;
            case R.id.confirm_order:
                caches.put("addresscode","0");
                startActivity(new Intent(context,LocationListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                break;
            case R.id.img_back:
                AppManager.getInstance().killActivity(ConfirmOrderActivity.class);
                break;
        }
    }
    /**
     * 获取数据
     */
    private void getLocationList(){
        OkHttpUtils.get().url(Urls.TRUEORSER).addParams("token",caches.get("php_token"))
                .build().execute(new StringCallback() {
            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                stopMyDialog();
            }

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                startMyDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                isLogin(e);
            }

            @Override
            public void onResponse(String response, int id) {
                selectedEntity = new Gson().fromJson(response, SelectedEntity.class);
                if(selectedEntity.result == 1){
                    tv_address.setText(selectedEntity.data.defaultAddress.province+
                            selectedEntity.data.defaultAddress.city+selectedEntity.data.defaultAddress.region
                    +selectedEntity.data.defaultAddress.addr);
                    tv_name.setText(selectedEntity.data.defaultAddress.name);
                    tv_number.setText(selectedEntity.data.defaultAddress.mobile+"");
                    addressId = selectedEntity.data.defaultAddress.addrId;
                    tv_price.setText("¥"+ StringUtils.getStringtodouble(selectedEntity.data.orderPrice.goodsPrice));
                    tv_expense.setText("¥"+StringUtils.getStringtodouble(selectedEntity.data.orderPrice.shippingPrice));
                    tv_total_price.setText("¥"+StringUtils.getStringtodouble(selectedEntity.data.orderPrice.orderPrice));
                    tv_all_price.setText(Html.fromHtml("需支付："+"<font color=\"#fa2f5a\">¥"+StringUtils.getStringtodouble(selectedEntity.data.orderPrice.needPayMoney)+"</font>"));
                    if((int)(selectedEntity.data.orderPrice.point/100)<=0){
                        tv_count.setVisibility(View.GONE);
                    }else {
                        tv_count.setVisibility(View.VISIBLE);
                        tv_count.setText(Html.fromHtml("赠送"+"<font color=\"#fa2f5a\">"+(int)(selectedEntity.data.orderPrice.point/100)+"</font>积分"));
                    }
                    confirmOrderAdapter = new ConfirmOrderAdapter(context,selectedEntity.data.items);
                    listView.setAdapter(confirmOrderAdapter);

                }else {
                    new AlertDialog.Builder(context).setTitle("系统提示")//设置对话框标题
                            .setMessage("您还没有设置收货地址，是否现在设置？")//设置显示的内容
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    startActivity(new Intent(context,PerfectActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra("addresstype","0"));
                                }
                            }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                        public void onClick(DialogInterface dialog, int which) {//响应事件
                            dialog.dismiss();
                        }
                    }).show();//在按键响应事件中显示此对话框
                }
            }
        });
    }
    /**
     * 创建订单
     */
    private void setOrder(){
        if(tv_confirm_note.getText().toString().length()>0){
            OkHttpUtils.post().url(Urls.SETORDER).addParams("token",caches.get("php_token"))
                    .addParams("addressId",addressId+"")
                    .addParams("remark",tv_confirm_note.getText().toString())
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    isLogin(e);
                }

                @Override
                public void onResponse(String response, int id) {
                    orderTrueEntity = new Gson().fromJson(response,OrderTrueEntity.class);
                    if(orderTrueEntity.result == 1){
                        startActivity(new Intent(context, OrderListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra("orderstaut","1"));
                    }else {
                        showToast(orderTrueEntity.message);
                    }

                }
            });
        }else {
            OkHttpUtils.post().url(Urls.SETORDER).addParams("token",caches.get("php_token"))
                    .addParams("addressId",addressId+"")
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    isLogin(e);
                }

                @Override
                public void onResponse(String response, int id) {
                    orderTrueEntity = new Gson().fromJson(response,OrderTrueEntity.class);
                    if(orderTrueEntity.result == 1){
                        startActivity(new Intent(context, OrderListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra("orderstaut","1"));
                    }else {
                        showToast(orderTrueEntity.message);
                    }

                }
            });
        }


    }

}
