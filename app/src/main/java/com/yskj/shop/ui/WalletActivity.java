package com.yskj.shop.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yskj.shop.R;
import com.yskj.shop.adapter.ConfirmOrderAdapter;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Config;
import com.yskj.shop.config.Urls;
import com.yskj.shop.deposit.DepositToBankActivity;
import com.yskj.shop.entity.RealNameEntity;
import com.yskj.shop.entity.SelectedEntity;
import com.yskj.shop.entity.UserInfo;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.logging.Logger;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 作者：  陈宙洋
 * 描述：钱包
 * 日期： 2017/11/22.
 */

public class WalletActivity extends BaseActivity implements View.OnClickListener{
    private TextView tvHint,expendableFund,tvGive,tvVendibility,tvOriginal,tvBuyback,txtTitle;
    private LinearLayout withdrawDeposit,accountsLayout,buySoil;
    private ImageView imaBack;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private double availableIntegral;
    private int addressId;
    private int totalLand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        iniview();
    }

    private void iniview() {
        tvHint = (TextView) findViewById(R.id.tv_hint);
        expendableFund = (TextView) findViewById(R.id.expendable_fund);
        tvGive = (TextView) findViewById(R.id.tv_give);
        tvVendibility = (TextView) findViewById(R.id.tv_vendibility);
        tvOriginal = (TextView) findViewById(R.id.tv_original);
        tvBuyback = (TextView) findViewById(R.id.tv_buyback);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        imaBack = (ImageView) findViewById(R.id.img_back);
        withdrawDeposit = (LinearLayout) findViewById(R.id.withdraw_deposit);
        accountsLayout = (LinearLayout) findViewById(R.id.accounts_layout);
        buySoil = (LinearLayout) findViewById(R.id.buy_soil);
        txtTitle.setText("钱包");
        tvHint.setOnClickListener(this);
        buySoil.setOnClickListener(this);
        accountsLayout.setOnClickListener(this);
        withdrawDeposit.setOnClickListener(this);
        imaBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.withdraw_deposit:
                //提现
                getRealNameDate();
                break;
            case R.id.accounts_layout:
                //转账
                startActivity(new Intent(context,AccountsActivity.class).putExtra("availableIntegral",StringUtils.getStringtodouble(availableIntegral)));
                break;
            case R.id.buy_soil:

                //购买土地
                getLocationList();
                break;
            case R.id.tv_hint:
                showdialog("可用资金可以用来来提现，购买土地使用哟！！");
                break;
        }
    }
    /**
     * 获取实名认证信息
     */
    public void getRealNameDate(){
        OkHttpUtils.post().url(Urls.IDENRITYDETAIL).addHeader("Authorization",caches.get("access_token"))
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
                RealNameEntity realNameEntity = new Gson().fromJson(response,RealNameEntity.class);
                if(realNameEntity.code == 200){
                  context.startActivity(new Intent(context, DepositToBankActivity.class).putExtra("availableIntegral",StringUtils.getStringtodouble(availableIntegral)));
                }else  {
                 showdialog("您还未进行实名认证，请及时进行实名认证");

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfro();

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
                SelectedEntity  selectedEntity = new Gson().fromJson(response, SelectedEntity.class);
                if(selectedEntity.result == 1){
                    addressId = selectedEntity.data.defaultAddress.addrId;
                    startActivity(new Intent(context,BuySoilActivity.class).putExtra("addressid",addressId+"")
                            .putExtra("availableIntegral",StringUtils.getStringtodouble(availableIntegral))
                    .putExtra("totalLand",totalLand+""));
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
     * 获取个人信息
     */
    public void getUserInfro() {
        OkHttpUtils.get().url(Urls.GETUSERINFO)
                .addHeader(Config.HEADER, caches.get("access_token"))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                isLogin(e);
            }

            @Override
            public void onResponse(String response, int id) {
                UserInfo userInfo = new Gson().fromJson(response, UserInfo.class);
                if (userInfo.code == 200&&userInfo.data!=null) {
                    availableIntegral = userInfo.data.availableIntegral;
                    totalLand = userInfo.data.totalLand;
                    expendableFund.setText(StringUtils.getStringtodouble(userInfo.data.availableIntegral));//可用资金
                    tvGive.setText(StringUtils.getStringtodouble(userInfo.data.consumeIntegral));//赠送积分
                    tvVendibility.setText(StringUtils.getStringtodouble(userInfo.data.spreadTotal));//可售积分
                    tvOriginal.setText(StringUtils.getStringtodouble(userInfo.data.originalIntegral));//原始积分
                    tvBuyback.setText(StringUtils.getStringtodouble(userInfo.data.buybackIntegral));//回购积分
                } else {
                    showToast(userInfo.message);
                }
            }
        });

    }

}
