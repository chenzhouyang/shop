package com.yskj.shop.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yskj.shop.R;
import com.yskj.shop.adapter.ShoppingCartAdapter;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.CapitalEntity;
import com.yskj.shop.entity.ResultEntity;
import com.yskj.shop.entity.ShoppingCartEntity;
import com.yskj.shop.entity.UpdateNumEntity;
import com.yskj.shop.ui.CommodityDetailsActivity;
import com.yskj.shop.ui.ConfirmOrderActivity;
import com.yskj.shop.utils.JSONFormat;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.NumberFormat;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;


/**
 * 作者：陈宙洋
 * 日期：2017/8/11.
 * 描述：购物车
 */
public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener, ShoppingCartAdapter.CheckInterface, ShoppingCartAdapter.ModifyCountInterface{
    ImageView imgBack;
    TextView txtTitle;
    ListView listShoppingCart;
    CheckBox ckAll;
    TextView tvSettlement;
    TextView tvShowPrice;
    TextView shoppingClearAway;
    LinearLayout cartClearAway;
    LinearLayout rlBottom;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0, id;// 购买的商品总数量
    private ArrayList<ShoppingCartEntity.DataBean.ItemsBean> shoppingCartBeanList = new ArrayList<>();
    private ShoppingCartAdapter shoppingCartAdapter;
    private ShoppingCartEntity shoppingCartEntity;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private List<ShoppingCartEntity.DataBean.ItemsBean> listischeck = new ArrayList<>();
    private CapitalEntity capitalEntity;//购物车资金实体
    private ResultEntity resultEntity;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orchard_shopcart);
        iniview();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getShopCart();
    }

    private void iniview() {
        type = getIntent().getStringExtra("type");
        imgBack = (ImageView) findViewById(R.id.img_back);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        listShoppingCart = (ListView) findViewById(R.id.list_shopping_cart);
        ckAll = (CheckBox) findViewById(R.id.ck_all);
        tvSettlement = (TextView) findViewById(R.id.tv_settlement);
        tvShowPrice = (TextView) findViewById(R.id.tv_show_price);
        shoppingClearAway = (TextView) findViewById(R.id.shopping_clear_away);
        cartClearAway = (LinearLayout) findViewById(R.id.cart_clear_away);
        rlBottom = (LinearLayout) findViewById(R.id.rl_bottom);
        if(type.equals("0")){
            imgBack.setVisibility(View.INVISIBLE);
        }
        txtTitle.setText("购物车");
        tvSettlement.setOnClickListener(this);
        ckAll.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        shoppingCartAdapter = new ShoppingCartAdapter(context);
        shoppingCartAdapter.setCheckInterface(this);
        shoppingCartAdapter.setModifyCountInterface(this);
        shoppingCartAdapter.setShoppingCartBeanList(listischeck);
        listShoppingCart.setAdapter(shoppingCartAdapter);
    }
    /**
     * 获取购物车数据
     */
    private void getShopCart(){
        OkHttpUtils.get().url(Urls.SHOPPINGCART).addParams("token",caches.get("php_token"))
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
                 shoppingCartEntity = new Gson().fromJson(response,ShoppingCartEntity.class);
                if(shoppingCartEntity.result == 1){
                    listischeck.clear();
                    listischeck.addAll(shoppingCartEntity.data.items);
                   if(isAllCheck()){
                       ckAll.setChecked(true);
                   }else {
                       ckAll.setChecked(false);
                   }
                    statistics();
                    shoppingCartAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 统计操作
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
     * 3.给底部的textView进行数据填充
     */
    public void statistics() {
        if(isAllCheck()){
            OkHttpUtils.get().url(Urls.CAPITAL).addParams("token",caches.get("php_token"))
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
                    capitalEntity = new Gson().fromJson(response,CapitalEntity.class);
                    if(capitalEntity.result == 1){
                        totalCount = 0;
                        totalPrice = capitalEntity.data.orderPrice;
                        for (int i = 0; i < listischeck.size(); i++) {
                            ShoppingCartEntity.DataBean.ItemsBean shoppingCartBean = listischeck.get(i);
                            if (shoppingCartBean.isCheck == 1) {
                                totalCount++;
                            }
                        }
                        String str = "总金额：<font color=\"#e42020\">￥" + String.format("%.2f", NumberFormat.convertToDouble(totalPrice, 0.00)) + "</font>";
                        tvShowPrice.setText(Html.fromHtml(str));
                        tvSettlement.setText("结算(" + totalCount + ")");
                    }
                }
            });
        }else {
            totalCount = 0;
            totalPrice = 0.00;
            for (int i = 0; i < listischeck.size(); i++) {
                ShoppingCartEntity.DataBean.ItemsBean shoppingCartBean = listischeck.get(i);
                if (shoppingCartBean.isCheck == 1) {
                    totalCount++;
                    totalPrice += shoppingCartBean.price * shoppingCartBean.num;
                }
            }
            String str = "总金额：<font color=\"#e42020\">￥" + String.format("%.2f", NumberFormat.convertToDouble(totalPrice, 0.00)) + "</font>";
            tvShowPrice.setText(Html.fromHtml(str));
            tvSettlement.setText("结算(" + totalCount + ")");
        }



    }

    /**
     * 单选
     *
     * @param position  组元素位置
     * @param isChecked 组元素选中与否
     */
    @Override
    public void checkGroup(int position, boolean isChecked) {
        int check = 0;
        if(isChecked){
            check = 1;
        }else {
            check = 0;
        }

        listischeck.get(position).setIsCheck(check);
        if (isAllCheck()) {
            ckAll.setChecked(true);
            statistics();
        } else {
            ckAll.setChecked(false);
        }
        setGroupcheck(check,position);
    }
    /**
     * 单选改变数据
     */
    private void setGroupcheck(int check,int position){
        OkHttpUtils.post().url(Urls.CHECKCART).addParams("token",caches.get("php_token"))
                .addParams("checked",check+"")
                .addParams("product_id",listischeck.get(position).productId+"")
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
                resultEntity = new Gson().fromJson(response,ResultEntity.class);
                if(resultEntity.result == 1){
                    getShopCart();
                }
            }
        });
    }

    /**
     * 遍历list集合
     *
     * @return
     */
    private boolean isAllCheck() {
        //0 false 1 true
        for (ShoppingCartEntity.DataBean.ItemsBean group : listischeck) {
            if (group.isCheck!=1)
                return false;
        }
        return true;
    }

    /**
     * 增加
     *
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        ShoppingCartEntity.DataBean.ItemsBean shoppingCart= listischeck.get(position);
        int currentCount = shoppingCart.num;
        currentCount++;
        shoppingCart.num= currentCount;
        ((EditText) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        modifyCartNum(position,showCountView, currentCount);
        statistics();
    }

    /**
     * 删减
     *
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {
        ShoppingCartEntity.DataBean.ItemsBean shoppingCart= listischeck.get(position);
        int currentCount = shoppingCart.num;
          if (currentCount == 1) {
            return;
        }
        currentCount--;
        ((EditText) showCountView).setText(currentCount + "");
        modifyCartNum(position, showCountView,currentCount);
        statistics();

    }

    /**
     * 修改购物车数量
     */
    private void modifyCartNum(int position, final View showCountView, final int currentCount) {
        OkHttpUtils.post().url(Urls.UPDATENUM).addParams("token",caches.get("php_token"))
                .addParams("cartid",listischeck.get(position).id+"")
                .addParams("productid",listischeck.get(position).productId+"")
                .addParams("num",currentCount+"")
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
                UpdateNumEntity updateNumEntity = new Gson().fromJson(response,UpdateNumEntity.class);
                if(updateNumEntity.result == 1){
                    if(currentCount > updateNumEntity.data.store){
                        ((EditText) showCountView).setText(updateNumEntity.data.store + "");
                        showToast("超出库存了，最多购买"+updateNumEntity.data.store);
                    }else {
                        getShopCart();
                    }
                }
            }
        });
    }

    /**
     * 删除
     *
     * @param position
     */
    @Override
    public void childDelete(int position) {
        //通知后台修改数据库
        OkHttpUtils.post().url(Urls.DELETECART)
                .addParams("token",caches.get("php_token"))
                .addParams("cartid",listischeck.get(position).id+"")
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
                Map<String,Object> map = JSONFormat.jsonToMap(response);
                if(map!=null){
                    int code= (int) map.get("result");
                    if(code == 1){
                        getShopCart();
                        showToast("商品删除成功");
                        statistics();
                    }
                }
            }
        });
    }

    /**
     * 跳转商品详情
     * @param postion
     * @param id
     */

    @Override
    public void skip(int postion, int id) {
//        startActivity(new Intent(context,CommodityDetailsActivity.class)
//                .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//                .putExtra("id",id+""));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //全选按钮
            case R.id.ck_all:
                int ckall = 0;
                if(isAllCheck()){
                    ckall = 0;
                }else {
                    ckall = 1;
                }
                allchecked(ckall);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_settlement:
                //向后台提交数据，让其修改数据库
                //postToPhp();
                if(totalCount == 0){
                    showToast("您还没有选择购买的商品哦");
                }else {
                    startActivity(new Intent(context, ConfirmOrderActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                }
                break;
            case R.id.shopping_clear_away:
                //clearaway();
                break;
        }
    }

    /**
     * 全选和全不选
     */
    private void allchecked(int checked) {
        OkHttpUtils.get().url(Urls.SHOPPINGCARTALLCART)
                .addParams("token",caches.get("php_token"))
                .addParams("checked",checked+"")
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
                Map<String,Object> map = JSONFormat.jsonToMap(response);
                if(map!=null){
                    int code = (int) map.get("result");
                    if(code == 1){
                        getShopCart();
                        statistics();
                    }
                }

            }
        });
    }
}
