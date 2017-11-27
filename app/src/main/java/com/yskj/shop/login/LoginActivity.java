package com.yskj.shop.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Config;
import com.yskj.shop.config.Ips;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.LoginEntity;
import com.yskj.shop.home.HomeActivity;
import com.yskj.shop.register.RegisterActivity;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.MobileEncryption;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by YSKJ-02 on 2017/1/13.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    ImageView imgBack;
    TextView txtTitle;
    EditText loginPhone;
    EditText loginPassword;
    TextView loginForgetPassword;
    TextView loginRegister;
    Button loginBtn;
    private LoadingCaches caches = LoadingCaches.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniview();
        loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (loginPassword.length() != 0) {
                    loginBtn.setBackgroundResource(R.drawable.login_btn_true);
                }
            }
        });
    }

    private void iniview() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        loginPhone = (EditText) findViewById(R.id.login_phone);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginForgetPassword = (TextView) findViewById(R.id.login_forget_password);
        loginRegister = (TextView) findViewById(R.id.login_register);
        loginBtn = (Button) findViewById(R.id.login_btn);
        imgBack.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        loginRegister.setOnClickListener(this);
        txtTitle.setText("登陆");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                startActivity(new Intent(context, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.login_register:
                startActivity(new Intent(context, RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.login_btn:

                boolean isPhoneNum = MobileEncryption.isMobileNO(loginPhone.getText().toString());
                if (!isPhoneNum) {
                    showToast("请输入正确的手机号");
                } else if (loginPassword.length() == 0) {
                    showToast("请输入密码");
                } else {
                    login();
                }
                break;
        }
    }

    //登录请求方法
    private void login() {
        OkHttpUtils.post().url(Urls.LOGIN)
                .addParams("mobile", loginPhone.getText().toString())
                .addParams("password", loginPassword.getText().toString())
                .build().execute(new LoginCallBack());
    }

    private class LoginCallBack extends Callback<LoginEntity> {


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
        public LoginEntity parseNetworkResponse(Response response, int id) throws Exception {
            String s = response.body().string();
            LoginEntity loginEntity = new Gson().fromJson(s, new TypeToken<LoginEntity>() {
            }.getType());
            return loginEntity;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            isLogin(e);
        }

        @Override
        public void onResponse(LoginEntity response, int id) {
            if (response.code == 200) {
                caches.put("access_token", Config.TOKENHEADER + response.data);
                caches.put("php_token",response.data);
                SharedPreferences share2 = getSharedPreferences("mobile", 0);
                SharedPreferences.Editor editor2 = share2.edit();
                editor2.putString("mobile", loginPhone.getText().toString());
                editor2.commit();
                SharedPreferences share = getSharedPreferences("password", 0);
                SharedPreferences.Editor editor = share.edit();
                editor.putString("password", loginPassword.getText().toString());
                editor.commit();
                AppManager.getInstance().killActivity(RegisterActivity.class);
                AppManager.getInstance().killActivity(LoginActivity.class);
            } else {
                showToast(response.message);
            }
        }
    }
}
