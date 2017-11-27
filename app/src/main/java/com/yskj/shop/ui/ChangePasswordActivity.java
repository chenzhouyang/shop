package com.yskj.shop.ui;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Config;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.ApproveEntity;
import com.yskj.shop.utils.LoadingCaches;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 作者：  陈宙洋
 * 描述：修改登陆支付密码
 * 日期： 2017/10/24.
 */

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener{
    private EditText change_new_password,change_old_password;
    private TextView change_attention,txtTitle;
    private Button sure_btn;
    private ImageView imageBack;
    private String type;
    private LoadingCaches caches = LoadingCaches.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        iniview();
    }

    private void iniview() {
        type = getIntent().getStringExtra("type");

        change_new_password = (EditText) findViewById(R.id.change_new_password);
        change_old_password = (EditText) findViewById(R.id.change_old_password);
        change_attention = (TextView) findViewById(R.id.change_attention);
        sure_btn = (Button) findViewById(R.id.sure_btn);
        txtTitle= (TextView) findViewById(R.id.txt_title);
        imageBack = (ImageView) findViewById(R.id.img_back);
        imageBack.setOnClickListener(this);
        if(type.equals("2")){
            change_new_password.setInputType(InputType.TYPE_CLASS_NUMBER);
            change_old_password.setInputType(InputType.TYPE_CLASS_NUMBER);
            change_old_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
            change_new_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
            change_attention.setVisibility(View.VISIBLE);
            txtTitle.setText("修改支付密码");
        }else {
            change_attention.setVisibility(View.GONE);
            txtTitle.setText("修改登陆密码");
        }
        sure_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.img_back:
                AppManager.getInstance().killActivity(ChangePasswordActivity.class);
                break;
            case R.id.sure_btn:
                if(change_old_password.length() == 0){
                    showToast("请输入旧密码");
                }else if(change_new_password.length() == 0){
                    showToast("请输入新密码");
                }else {
                    changePwd();
                }

                break;
        }
    }
    private void changePwd(){
        OkHttpUtils.post().url(Urls.CHANDPWD).addHeader(Config.HEADER,caches.get("access_token"))
                .addParams("orginPwd",change_old_password.getText().toString())
                .addParams("newPwd",change_new_password.getText().toString())
                .addParams("type",type)
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
                if(approveEntity.code == 200){
                    showToast("密码修改成功");
                    AppManager.getInstance().killActivity(ChangePasswordActivity.class);
                }else {
                    showToast(approveEntity.message);
                }
            }
        });


    }
}
