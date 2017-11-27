package com.yskj.shop.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Config;
import com.yskj.shop.config.Urls;
import com.yskj.shop.deposit.AddBankActivity;
import com.yskj.shop.deposit.BankListActivity;
import com.yskj.shop.deposit.DepositToBankActivity;
import com.yskj.shop.entity.RealNameEntity;
import com.yskj.shop.entity.UserInfo;
import com.yskj.shop.login.LoginActivity;
import com.yskj.shop.ui.ApproveActivity;
import com.yskj.shop.ui.ChangePasswordActivity;
import com.yskj.shop.ui.FansActivity;
import com.yskj.shop.ui.LocationListActivity;
import com.yskj.shop.ui.OrderListActivity;
import com.yskj.shop.ui.WalletActivity;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.StringUtils;
import com.yskj.shop.widget.CircleImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 作者：陈宙洋
 * 日期：2017/8/11.
 * 描述：个人信息
 */
public class MineActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout shopminLocation,mlin_payment_ll,mlin_shipments_ll,mlin_takegoods_ll,mlin_stoptakegoods_ll,mine_bank,mine_approve
            ,mine_change_loginpassword,mine_change_paypassword,mine_fans,mine_wallet;
    private CircleImageView mlinPortrait;
    private TextView mine_nick_name,logout_mine;
    private ImageView image_service;
    private LoadingCaches caches = LoadingCaches.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orchard_shopmine);
        iniview();
//        initData();
    }

    private void iniview() {
        shopminLocation = (LinearLayout) findViewById(R.id.shopmin_location);
        mlinPortrait = (CircleImageView) findViewById(R.id.mlin_portrait);
        mlin_payment_ll = (LinearLayout) findViewById(R.id.mlin_payment_ll);
        mlin_shipments_ll = (LinearLayout) findViewById(R.id.mlin_shipments_ll);
        mlin_takegoods_ll = (LinearLayout) findViewById(R.id.mlin_takegoods_ll);
        image_service = (ImageView) findViewById(R.id.activity_image_service);
        mlin_stoptakegoods_ll = (LinearLayout) findViewById(R.id.mlin_stoptakegoods_ll);
        mine_nick_name = (TextView) findViewById(R.id.mine_nick_name);
        logout_mine = (TextView) findViewById(R.id.logout_mine);
        mine_change_paypassword = (LinearLayout) findViewById(R.id.mine_change_paypassword);
        mine_change_loginpassword = (LinearLayout) findViewById(R.id.mine_change_loginpassword);
        mine_bank = (LinearLayout) findViewById(R.id.mine_bank);
        mine_approve = (LinearLayout) findViewById(R.id.mine_approve);
        mine_fans = (LinearLayout) findViewById(R.id.mine_fans);
        mine_wallet = (LinearLayout)findViewById(R.id.mine_wallet);
        mine_wallet.setOnClickListener(this);
        mine_fans.setOnClickListener(this);
        mine_change_loginpassword.setOnClickListener(this);
        mine_change_paypassword.setOnClickListener(this);
        mine_bank.setOnClickListener(this);
        mine_approve.setOnClickListener(this);
        image_service.setOnClickListener(this);
        logout_mine.setOnClickListener(this);
        mlinPortrait.setOnClickListener(this);
        mlin_stoptakegoods_ll.setOnClickListener(this);
        mlin_takegoods_ll.setOnClickListener(this);
        mlin_shipments_ll.setOnClickListener(this);
        mlin_payment_ll.setOnClickListener(this);
        shopminLocation.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        SharedPreferences share2    = getSharedPreferences("mobile", 0);
        String mobile  = share2.getString("mobile", "null");
        if(!mobile.equals("null")){
            mine_nick_name.setText(StringUtils.phoneNumber(mobile));
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shopmin_location:
                startActivity(new Intent(context, LocationListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                break;
            case R.id.mlin_payment_ll:
                //待付款
                startActivity(new Intent(context, OrderListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra("orderstaut","1"));
                break;
            case R.id.mlin_shipments_ll:
                //待发货
                startActivity(new Intent(context, OrderListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra("orderstaut","2").putExtra("type","1"));
                break;
            case R.id.mlin_takegoods_ll:
                //待收货
                startActivity(new Intent(context, OrderListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra("orderstaut","3"));
                break;
            case R.id.mlin_stoptakegoods_ll:
                //已收货
                startActivity(new Intent(context, OrderListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra("orderstaut","4"));
                break;
            case R.id.logout_mine:
                //注销
                caches.remover();
                SharedPreferences share2 = getSharedPreferences("mobile", 0);
                SharedPreferences.Editor editor2 = share2.edit();
                editor2.putString("mobile", "null");
                editor2.commit();
                SharedPreferences share = getSharedPreferences("password", 0);
                SharedPreferences.Editor editor = share.edit();
                editor.putString("password", "null");
                editor.commit();
                context.startActivity(new Intent(context, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                break;
            case R.id.mine_approve:
                //实名认证
                getRealNameDate(0);
                break;
            case R.id.mine_bank:
                //绑定银行卡
                context.startActivity(new Intent(context, BankListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                break;
            case R.id.mine_change_loginpassword:
                //修改登陆密码
                context.startActivity(new Intent(context, ChangePasswordActivity.class).putExtra("type","1").setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                break;
            case R.id.mine_change_paypassword:
                //修改支付密码
                context.startActivity(new Intent(context, ChangePasswordActivity.class).putExtra("type","2").setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                break;
            case R.id.activity_image_service:
                if (checkApkExist(this, "com.tencent.mobileqq")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + Config.QQSERVICE + "&version=1")));
                } else {
                    Toast.makeText(this, "本机未安装QQ应用", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mine_fans:
                //粉丝
                startActivity(new Intent(context,FansActivity.class));
                break;
            case R.id.mine_wallet:
                //我的钱包
                startActivity(new Intent(context, WalletActivity.class));
                break;

        }
    }
    /**
     * 打开qq
     *
     * @param context
     * @param packageName
     * @return
     */
    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    /**
     * 获取实名认证信息
     */
    public void getRealNameDate(final int type){
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
                    if(type == 0){
                        showdialog("您已经实名认证过了，暂不支持修改认证信息");
                    }else {
                        context.startActivity(new Intent(context, DepositToBankActivity.class));
                    }

                }else  {
                    if(type == 0){
                        context.startActivity(new Intent(context, ApproveActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    }else {
                        showdialog("您还未进行实名认证，请及时进行实名认证");
                    }

                }
            }
        });
    }

}
