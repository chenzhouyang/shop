package com.yskj.shop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Ips;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.ApproveEntity;
import com.yskj.shop.entity.OrderTrueEntity;
import com.yskj.shop.entity.ResultEntity;
import com.yskj.shop.home.ShoppingCartActivity;
import com.yskj.shop.utils.LoadingCaches;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：  陈宙洋
 * 描述：购买土地
 * 日期： 2017/11/22.
 */

public class BuySoilActivity extends BaseActivity implements View.OnClickListener{
    private Spinner spBuysoil;
    private TextView tvMoney,txtTitle,canBuySoil;
    private EditText etPwd;
    private ImageView imaBack;
    private Button trueBtn;
    private ArrayList<String> list = new ArrayList<>();
    private LoadingCaches caches = LoadingCaches.getInstance();
    private String number = "1";
    private ResultEntity resultEntity;
    private String addressId;
    private OrderTrueEntity orderTrueEntity;
    private String orderid = null;
    private String availableIntegral;
    private String totalLand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buysoil);
        iniview();
        setSpinerList();
    }
    private void setSpinerList(){
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("10");
        list.add("20");
        list.add("30");
        //这里的R.Layout.spinner_item为spinner的样式
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.spinner_item);
        //这里的R.layout.spinner_item_item为下拉框textview的样式
        adapter.setDropDownViewResource(R.layout.spinner_item_item);
        adapter.addAll(list);
        spBuysoil.setAdapter(adapter);
        spBuysoil.setSelection(0,true);
        spBuysoil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                number = list.get(position);
                tvMoney.setText(Integer.parseInt(list.get(position))*2000+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }
    private void iniview() {
        addressId = getIntent().getStringExtra("addressid");
        availableIntegral = getIntent().getStringExtra("availableIntegral");
        totalLand = getIntent().getStringExtra("totalLand");
        spBuysoil = (Spinner) findViewById(R.id.sp_buysoil);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        imaBack = (ImageView) findViewById(R.id.img_back);
        trueBtn = (Button) findViewById(R.id.true_btn);
        canBuySoil = (TextView) findViewById(R.id.can_buy_soil);
        canBuySoil.setText("当前租赁土地数量："+totalLand);
        trueBtn.setOnClickListener(this);
        imaBack.setOnClickListener(this);
        tvMoney.setText("2000");
        txtTitle.setText("租赁土地");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                AppManager.getInstance().killActivity(BuySoilActivity.class);
                break;
            case R.id.true_btn:
                //确定
                if(etPwd.length()==0){
                    showToast("请输入支付密码");
                }else if(Double.parseDouble(availableIntegral)-Double.parseDouble(tvMoney.getText().toString())<0){
                    showToast("可用资金不足");
                }else {
                    //清空购物车
                    clear();
                    //提交数据
                    setShopCart();
                }


                break;
        }
    }
    /**
     * 提交数据
     */
    private void setSoil(){
        OkHttpUtils.post().url(Urls.SOILPAY).addHeader("Authorization",caches.get("access_token"))
                .addParams("money",tvMoney.getText().toString())
                .addParams("pwd",etPwd.getText().toString())
                .addParams("orderid",orderid+"")
                .build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                startMyDialog();
            }

            @Override
            public void onAfter(int id) {
                stopMyDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                isLogin(e);
            }

            @Override
            public void onResponse(String response, int id) {
                ApproveEntity approveEntity = new Gson().fromJson(response,ApproveEntity.class);
                if(approveEntity.code == 200){
                    AppManager.getInstance().killActivity(BuySoilActivity.class);
                }else {
                    showToast(approveEntity.message);
                }
            }
        });
    }
    /**
     * 加入购物车
     */
    private void setShopCart(){
        OkHttpUtils.post().url(Urls.ADDSHOPPGOODS).addHeader("Authorization",caches.get("access_token"))
                .addParams("token",caches.get("php_token"))
                .addParams("goodsid",Ips.SOILGOODSIS)
                .addParams("num",number)
                .build().execute(new AddShoppingCartCallBack());
    }
    /**
     * 获取数据实例化
     */
    private class AddShoppingCartCallBack extends Callback<ResultEntity> {
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
        public ResultEntity parseNetworkResponse(Response response, int id) throws Exception {
            String s = response.body().string();
            resultEntity = new Gson().fromJson(s,ResultEntity.class);
            return resultEntity;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            isLogin(e);
        }

        @Override
        public void onResponse(ResultEntity response, int id) {
            if(response.result == 1){
                OrderConfirm();
            }else {
                showToast(response.message);
            }

        }
    }

    /**
     * 获取order
     */
    private void OrderConfirm(){
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
                    orderid = orderTrueEntity.data.order.orderId+"";
                    setSoil();
                }else {
                    showToast(orderTrueEntity.message);
                }

            }
        });
    }

    /**
     * 清空购物车
     */
    private void clear(){
        OkHttpUtils.post().url(Urls.CLENCART).addParams("token",caches.get("php_token")).build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        com.orhanobut.logger.Logger.d(response);
                    }
                });

    }
}
