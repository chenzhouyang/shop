package com.yskj.shop.register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.yskj.shop.entity.RegisterEntitiy;
import com.yskj.shop.home.HomeActivity;
import com.yskj.shop.login.LoginActivity;
import com.yskj.shop.utils.JSONFormat;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.MessgeUtil;
import com.yskj.shop.utils.MobileEncryption;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by YSKJ-02 on 2017/1/13.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    ImageView imgBack;
    TextView txtTitle;
    EditText registerPhone;
    TextView registerGain;
    TextView loginForgetPassword;
    TextView loginRegister;
    Button registerBtn;
    EditText registerSpreadMobile;
    EditText registerFen;
    private boolean ismobile;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private boolean register = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        iniview();
        registerSpreadMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (MobileEncryption.isMobileNO(registerSpreadMobile.getText().toString())) {
                    registerBtn.setBackgroundResource(R.drawable.login_btn_true);
                }
            }
        });

        registerPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (MobileEncryption.isMobileNO(registerPhone.getText().toString())) {
                    registerBtn.setBackgroundResource(R.drawable.login_btn_true);
                }
            }
        });

    }
    private void iniview() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        registerPhone = (EditText) findViewById(R.id.register_phone);
        registerGain = (TextView) findViewById(R.id.register_gain);
        loginForgetPassword= (TextView) findViewById(R.id.login_forget_password);
        loginRegister = (TextView) findViewById(R.id.login_register);
        registerBtn = (Button) findViewById(R.id.register_btn);
        registerSpreadMobile = (EditText) findViewById(R.id.register_spreadMobile);
        registerFen = (EditText) findViewById(R.id.register_fen);
        loginRegister.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        txtTitle.setText("注册");
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                AppManager.getInstance().killActivity(RegisterActivity.class);
                break;
            case R.id.login_register:
                startActivity(new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.register_btn:
                if (registerPhone.length() == 0) {
                    showToast("请输入您的手机号");
                } else if (!MobileEncryption.isMobileNO(registerPhone.getText().toString())) {
                    showToast("请输入正确的手机号");
                    registerPhone.setText("");
                } else if(registerSpreadMobile.length() == 0){
                    showToast("请输入分享人手机号");
                }else if(!MobileEncryption.isMobileNO(registerSpreadMobile.getText().toString())){
                    showToast("请输入分享人正确的手机号");
                }else if(registerFen.length()!=0&&!MobileEncryption.isMobileNO(registerSpreadMobile.getText().toString())){
                    showToast("请输入正确的手机号");
                }else if(registerFen.length()!=0&&MobileEncryption.isMobileNO(registerSpreadMobile.getText().toString())){
                    SharedPreferences share2 = getSharedPreferences("mobile", 0);
                    SharedPreferences.Editor editor2 = share2.edit();
                    editor2.putString("mobile", registerPhone.getText().toString());
                    editor2.commit();
                    SharedPreferences share = getSharedPreferences("password", 0);
                    SharedPreferences.Editor editor = share.edit();
                    editor.putString("password", registerPhone.getText().toString().substring(5, 11));
                    editor.commit();
                    //有分配人注册接口
                    voidgetregisterfen();

                }else {
                    SharedPreferences share2 = getSharedPreferences("mobile", 0);
                    SharedPreferences.Editor editor2 = share2.edit();
                    editor2.putString("mobile", registerPhone.getText().toString());
                    editor2.commit();
                    SharedPreferences share = getSharedPreferences("password", 0);
                    SharedPreferences.Editor editor = share.edit();
                    editor.putString("password", registerPhone.getText().toString().substring(5, 11));
                    editor.commit();
                    //无分配人注册接口
                    voidgetregister();
                }
                break;
        }
    }

    //无分配人注册接口
    private void voidgetregister() {
        OkHttpUtils.post().url(Urls.REGISTER).addParams("mobile", registerPhone.getText().toString())
                .addParams("referrer",registerSpreadMobile.getText().toString())
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
                RegisterEntitiy registerEntitiy = new Gson().fromJson(response,RegisterEntitiy.class);
                if (registerEntitiy.code == 200) {
                    new AlertDialog.Builder(context).setTitle("系统提示")//设置对话框标题
                            .setMessage("注册成功,登陆密码和支付密码为手机号的后六位，请及时更改")//设置显示的内容
                            .setPositiveButton("知道了", new DialogInterface.OnClickListener() {//添加确定按钮
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    AppManager.getInstance().killActivity(RegisterActivity.class);
                                    AppManager.getInstance().killActivity(LoginActivity.class);
                                    startActivity(new Intent(context,HomeActivity.class));
                                }
                            }).setCancelable(false).show();//在按键响应事件中显示此对话框
                }else {
                    showToast(registerEntitiy.message);
                }
            }
        });
    }
    //有分配人注册接口
    private void voidgetregisterfen() {
        OkHttpUtils.post().url(Urls.REGISTER).addParams("mobile", registerPhone.getText().toString())
                .addParams("referrer",registerSpreadMobile.getText().toString())
                .addParams("fen",registerFen.getText().toString())
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
                RegisterEntitiy registerEntitiy = new Gson().fromJson(response,RegisterEntitiy.class);
                if (registerEntitiy.code == 200) {
                    new AlertDialog.Builder(context).setTitle("系统提示")//设置对话框标题
                            .setMessage("注册成功,登陆密码和支付密码为手机号的后六位，请及时更改")//设置显示的内容
                            .setPositiveButton("知道了", new DialogInterface.OnClickListener() {//添加确定按钮
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    AppManager.getInstance().killActivity(RegisterActivity.class);
                                    AppManager.getInstance().killActivity(LoginActivity.class);
                                    startActivity(new Intent(context,HomeActivity.class));
                                }
                            }).setCancelable(false).show();//在按键响应事件中显示此对话框
                }else {
                    showToast(registerEntitiy.message);
                }
            }
        });
    }

}
