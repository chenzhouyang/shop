package com.yskj.shop.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.ApproveEntity;
import com.yskj.shop.utils.JSONFormat;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.MessgeUtil;
import com.yskj.shop.utils.MobileEncryption;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.ParseException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by YSKJ-02 on 2017/1/13.
 * 实名认证
 */

public class ApproveActivity extends BaseActivity implements View.OnClickListener{
    ImageView imgBack;
    TextView txtTitle;
    EditText name;
    EditText informatationNumber;
    EditText againNumber;
    TextView deposittobankTrue;
    LinearLayout approveTrue;
    TextView approveTishi;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);
        iniview();
    }

    private void iniview() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        name = (EditText) findViewById(R.id.name);
        informatationNumber = (EditText) findViewById(R.id.informatation_number);
        againNumber = (EditText) findViewById(R.id.again_number);
        deposittobankTrue = (TextView) findViewById(R.id.deposittobank_true);
        approveTrue = (LinearLayout) findViewById(R.id.approve_true);
        approveTishi = (TextView) findViewById(R.id.approve_tishi);
        imgBack.setOnClickListener(this);
        deposittobankTrue.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                AppManager.getInstance().killActivity(ApproveActivity.class);
                break;
            case R.id.deposittobank_true:
                try {
                    if (name.length() == 0) {
                        showToast("请填写您的真实名字");
                    } else if (informatationNumber.length() == 0) {
                        showToast("请填写您的身份证号");
                    } else if (againNumber.length() == 0) {
                        showToast("请确认您的身份证号");
                    } else if (!MobileEncryption.IDCardValidate(informatationNumber.getText().toString()).equals("合法的身份证号")) {
                        showToast("请正确填写您的身份证号");
                    } else if (!informatationNumber.getText().toString().equals(againNumber.getText().toString())) {
                        showToast("两次填写不一致");
                    } else {
                        getcardNo();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void getcardNo() {
        OkHttpUtils.post().url(Urls.IDENRITY).addHeader("Authorization", caches.get("access_token"))
                .addParams("name", name.getText().toString())
                .addParams("identityCardNo", againNumber.getText().toString()).build()
                .execute(new StringCallback() {
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
                        ApproveEntity approveEntity = new Gson().fromJson(response,ApproveEntity.class);
                        if(approveEntity.code == 200){
                            showToast("认证成功");
                            AppManager.getInstance().killActivity(ApproveActivity.class);
                        }else {
                            showToast(approveEntity.message);
                        }
                    }
                });

    }


}
