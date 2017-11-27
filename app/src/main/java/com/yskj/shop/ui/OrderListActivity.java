package com.yskj.shop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pulltorefresh.library.PullToRefreshBase;
import com.pulltorefresh.library.PullToRefreshListView;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.adapter.OrderListAdapter;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.OrderListEntity;
import com.yskj.shop.home.HomeActivity;
import com.yskj.shop.home.ShoppingCartActivity;
import com.yskj.shop.login.LoginActivity;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.MethodUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 作者：陈宙洋
 * 日期：2017/8/12.
 * 描述：订单列表
 */

public class OrderListActivity extends BaseActivity implements View.OnClickListener{
    private RadioGroup group;
    private RadioButton deliverGoods,sendGoods,rbSend,rbGot;
    private PullToRefreshListView orderListLv;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private OrderListEntity orderListEntity;
    private OrderListAdapter orderListAdapter;
    private int p = 1;
    private ArrayList<OrderListEntity.DataBean.OrdersListBean> listBeen = new ArrayList<>();
    private int status = 1;
    private ImageView img_back;
    private TextView txtTitle;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        iniview();
        finview();
    }

    private void iniview() {
        AppManager.getInstance().killActivity(LoginActivity.class);
        group = (RadioGroup) findViewById(R.id.group);
        deliverGoods = (RadioButton) findViewById(R.id.deliver_goods);
        sendGoods = (RadioButton) findViewById(R.id.send_goods);
        rbSend = (RadioButton) findViewById(R.id.rb_send);
        orderListLv = (PullToRefreshListView) findViewById(R.id.order_list_lv);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText("订单");
        orderListAdapter = new OrderListAdapter(context, listBeen);
        orderListLv.setAdapter(orderListAdapter);
        String orderstaut = getIntent().getStringExtra("orderstaut");
        if(orderstaut.equals("1")){
            status = 1;
            group.check(R.id.deliver_goods);
        }else if(orderstaut.equals("2")){
            type = getIntent().getStringExtra("type");
            status = 2;
            group.check(R.id.send_goods);
        }else if(orderstaut.equals("3")){
            status = 3;
            group.check(R.id.rb_send);
        }else if(orderstaut.equals("4")){
            status = 4;
            group.check(R.id.rb_got);
        }
        if(type!=null){
            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(type.equals("0")){
                        startActivity(new Intent(context, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    }else {
                        AppManager.getInstance().killActivity(OrderListActivity.class);
                        AppManager.getInstance().killActivity(LoginActivity.class);
                    }

                }
            });
        }

        getOrderListDate(status);
    }


    private void finview() {

        orderListLv.setMode(PullToRefreshBase.Mode.BOTH);
        orderListLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                p = 1;
                getOrderListDate(status);
                MethodUtils.stopRefresh(orderListLv);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                p++;
                p =  p*10;
                getOrderListDate(status);
                MethodUtils.stopRefresh(orderListLv);
            }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    //待付款
                    case R.id.deliver_goods:
                        p = 1;
                        status = 1;
                        listBeen.clear();
                        getOrderListDate(status);
                        orderListAdapter = new OrderListAdapter(context, listBeen);
                        orderListLv.setAdapter(orderListAdapter);
                        break;
                    //待发货
                    case R.id.send_goods:
                        p = 1;
                        status = 2;
                        listBeen.clear();
                        getOrderListDate(status);
                        orderListAdapter = new OrderListAdapter(context, listBeen);
                        orderListLv.setAdapter(orderListAdapter);
                        break;
                    //待收货
                    case R.id.rb_send:
                        p = 1;
                        status = 3;
                        listBeen.clear();
                        getOrderListDate(status);
                        orderListAdapter = new OrderListAdapter(context, listBeen);
                        orderListLv.setAdapter(orderListAdapter);
                        break;
                    //已收货
                    case R.id.rb_got:
                        p = 1;
                        status = 4;
                        listBeen.clear();
                        getOrderListDate(status);
                        orderListAdapter = new OrderListAdapter(context, listBeen);
                        orderListLv.setAdapter(orderListAdapter);
                        break;
                }
            }
        });
    }

    /**
     * 获取订单数据
     * @param status  订单类型
     */
    private void getOrderListDate(int status){
        OkHttpUtils.get().url(Urls.ORDERLIST)
                .addParams("token",caches.get("php_token"))
                .addParams("page",p+"")
                .addParams("pageSize","10")
                .addParams("status",status+"")
                .build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                startMyDialog();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                stopMyDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                isLogin(e);
            }

            @Override
            public void onResponse(String response, int id) {
                orderListEntity = new Gson().fromJson(response,OrderListEntity.class);
                if(orderListEntity.result == 1){
                    if(p == 1){
                        listBeen.clear();
                    }
                    listBeen.addAll(orderListEntity.data.ordersList);
                    if(orderListEntity.data.ordersList!=null&&orderListEntity.data.ordersList.size()>0){
                        orderListAdapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(context,"暂无订单",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                AppManager.getInstance().killActivity(ConfirmOrderActivity.class);
                AppManager.getInstance().killActivity(CommodityDetailsActivity.class);
                AppManager.getInstance().killActivity(PerfectActivity.class);
                AppManager.getInstance().killActivity(OrderDetailActivity.class);
                AppManager.getInstance().killActivity(OrderListActivity.class);
                break;
        }
    }

    /**
     * 监听返回键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AppManager.getInstance().killActivity(ConfirmOrderActivity.class);
            AppManager.getInstance().killActivity(CommodityDetailsActivity.class);
            AppManager.getInstance().killActivity(PerfectActivity.class);
            AppManager.getInstance().killActivity(OrderDetailActivity.class);
            AppManager.getInstance().killActivity(OrderListActivity.class);
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

}
