package com.yskj.shop.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.pulltorefresh.library.PullToRefreshBase;
import com.pulltorefresh.library.PullToRefreshScrollView;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Config;
import com.yskj.shop.config.Urls;
import com.yskj.shop.deposit.DepositToBankActivity;
import com.yskj.shop.entity.ApproveEntity;
import com.yskj.shop.entity.UserInfo;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.MessgeUtil;
import com.yskj.shop.utils.MobileEncryption;
import com.yskj.shop.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by YSKJ-02 on 2017/1/13.
 * 转账
 */

public class AccountsActivity extends BaseActivity implements View.OnClickListener{
    ImageView imgBack;
    TextView txtTitle,imageRight;
    TextView verifyTv;
    EditText accountsAmount;
    EditText accountsPhone;
    EditText accountsPassword;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private boolean accounts = true,amount = false,Phone = false,Password = false;//判断实名认证是否存在该用户
    private String availableIntegral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        iniview();

        accountsAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        accountsAmount.setText(s);
                        accountsAmount.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    accountsAmount.setText(s);
                    accountsAmount.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        accountsAmount.setText(s.subSequence(0, 1));
                        accountsAmount.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(accountsAmount.length() == 0){
                    showToast("请填写转账金额");
                    amount = false;
                }else {
                    amount = true;
                }
                if(amount&&Phone&&Password){
                    verifyTv.setBackgroundResource(R.drawable.login_btn_true);
                }else {
                    verifyTv.setBackgroundResource(R.drawable.login_btn);
                }
            }
        });
        accountsPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (MobileEncryption.isMobileNO(accountsPhone.getText().toString())) {
                        Phone = true;
                }else {
                    Phone = false;
                }
                if(amount&&Phone&&Password){
                    verifyTv.setBackgroundResource(R.drawable.login_btn_true);
                }else {
                    verifyTv.setBackgroundResource(R.drawable.login_btn);
                }
            }
        });
        accountsPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(accountsPassword.length() < 6){
                    Password = false;
                }else {
                    Password = true;
                }
                if(amount&&Phone&&Password){
                    verifyTv.setBackgroundResource(R.drawable.login_btn_true);
                }else {
                    verifyTv.setBackgroundResource(R.drawable.login_btn);
                }
            }
        });
    }

    private void iniview() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        accountsAmount = (EditText) findViewById(R.id.my_accounts_amount);
        accountsPhone = (EditText) findViewById(R.id.accounts_phone);
        accountsPassword = (EditText) findViewById(R.id.accounts_password);
        verifyTv = (TextView) findViewById(R.id.verify_tv);
        imageRight = (TextView) findViewById(R.id.image_right);
        txtTitle.setText("转账");
        imageRight.setText("转账记录");
        imageRight.setVisibility(View.VISIBLE);
        imageRight.setOnClickListener(this);
        verifyTv.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        availableIntegral = getIntent().getStringExtra("availableIntegral");
        accountsAmount.setHint("最大金额"+availableIntegral);
    }


    private void setaccount() {
        OkHttpUtils.post().url(Urls.ACCOUNTSAVE).addHeader("Authorization", caches.get("access_token"))
                .addParams("mobile", accountsPhone.getText().toString())
                .addParams("money", accountsAmount.getText().toString())
                .addParams("pwd", accountsPassword.getText().toString())
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
                ApproveEntity approveEntity = new Gson().fromJson(response,ApproveEntity.class);
                if (approveEntity.code == 200) {
                    new AlertDialog.Builder(AccountsActivity.this).setTitle("系统提示")
                            .setMessage("转账成功")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    startActivity(new Intent(context,AccountListActivity.class));
                                }
                            })
                            .setCancelable(false)
                            .show();//在按键响应事件中显示此对话框
                } else {
                    new AlertDialog.Builder(AccountsActivity.this).setTitle("系统提示")
                            .setMessage(approveEntity.message)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                }
                            }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                        public void onClick(DialogInterface dialog, int which) {//响应事件
                        }
                    }).show();//在按键响应事件中显示此对话框
                }

            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                AppManager.getInstance().killActivity(AccountsActivity.class);
                break;
            case R.id.verify_tv:
                //提现
                if (accountsAmount.length() == 0) {
                    showToast("请入转账金额");
                }else if (Double.parseDouble(availableIntegral)-Double.parseDouble(accountsAmount.getText().toString())<0){
                    showToast("转账金额过大");
                }else if (accountsPhone.length() == 0) {
                    showToast("请输入对方的手机号");
                } else if(!MobileEncryption.isMobileNO(accountsPhone.getText().toString())) {
                    showToast("请输入正确的手机号");
                }else if (accountsPassword.length() == 0) {
                    showToast("请输入支付密码");
                } else  if(!Password){
                    showToast("支付密码为6位数字");
                }else {
                    setaccount();
                }

                break;
            case R.id.image_right:
                //转账记录
                startActivity(new Intent(context,AccountListActivity.class));
                break;
        }
    }


}
