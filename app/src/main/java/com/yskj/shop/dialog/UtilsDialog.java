package com.yskj.shop.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yskj.shop.R;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.ApproveEntity;
import com.yskj.shop.entity.MainEntity;
import com.yskj.shop.login.LoginActivity;
import com.yskj.shop.utils.DoubleUtils;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

import static com.yskj.shop.ui.MainActivity.SHUAXIN;


/**
 * Created by YSKJ-02 on 2017/1/17.
 */

public class UtilsDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private EditText dialog_price,dialog_number,pay_password;
    private TextView dialog_true,dialog_title,refresh_price,integral_display;
    private String currency;
    private ImageView dialog_close;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private MyLoading myloading;
    private Activity activity;
    private Handler handler;
    private String title,message;
    private Double showIntegral;
    private String url;
    private double realTime;//实时价格


    public UtilsDialog(Context context, String currency,String title,double showIntegral,Handler handler) {
        super(context, R.style.ShareDialog);
        this.context = context;
        this.currency = currency;
        this.handler = handler;
        this.title = title;
        this.showIntegral = showIntegral;
        activity = (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_buy);
        initView();
        getPrice();
    }

    private void initView() {


        dialog_price = (EditText) findViewById(R.id.dialog_price);
        dialog_number = (EditText) findViewById(R.id.dialog_number);
        pay_password = (EditText) findViewById(R.id.pay_password);
        dialog_true = (TextView) findViewById(R.id.dialog_true);
        dialog_title = (TextView) findViewById(R.id.dialog_title);
        refresh_price = (TextView) findViewById(R.id.refresh_price);
        dialog_close = (ImageView) findViewById(R.id.dialog_close);
        integral_display = (TextView) findViewById(R.id.integral_display);
        dialog_close.setOnClickListener(this);
        dialog_true.setOnClickListener(this);
        refresh_price.setOnClickListener(this);
        dialog_title.setText(title);
        dialog_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        dialog_price.setText(s);
                        dialog_price.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    dialog_price.setText(s);
                    dialog_price.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        dialog_price.setText(s.subSequence(0, 1));
                        dialog_price.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_close:
                dismiss();
                break;
            case R.id.dialog_true:
                if(dialog_price.length() == 0){
                    Toast.makeText(context,"请输入价格",Toast.LENGTH_SHORT).show();
                }else  if(Double.parseDouble(dialog_price.getText().toString())>2){
                    Toast.makeText(context,"输入价格在（1-2）之间",Toast.LENGTH_SHORT).show();
                }else if(Double.parseDouble(dialog_price.getText().toString())<=0){
                    Toast.makeText(context,"输入价格在（1-2）之间",Toast.LENGTH_SHORT).show();
                }else if(dialog_number.length() == 0){
                    Toast.makeText(context,"请输入购买数量",Toast.LENGTH_SHORT).show();
                }else if(pay_password.length() == 0){
                    Toast.makeText(context,"请输入支付密码",Toast.LENGTH_SHORT).show();
                }else {
                    tradeBuy();
                }
                break;
            case R.id.refresh_price:
                getPrice();
                break;
        }
    }

    /**
     * 网络请求实时价格
     */
    private void getPrice(){
        OkHttpUtils.get().url(Urls.PRICE).build().execute(new StringCallback() {
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

            }

            @Override
            public void onResponse(String response, int id) {
                MainEntity mainEntity = new Gson().fromJson(response,MainEntity.class);
                if(mainEntity.code == 200){
                    realTime  = mainEntity.data;
                    dialog_price.setText(StringUtils.getStringtodouble(mainEntity.data));
                    /**
                     * 1、回购币，2、买入，3卖出
                     * 根据不同的类型请求不同的接口
                     */
                    if(currency.equals("3")){
                        dialog_number.setText(showIntegral.intValue()+"");
                        url = Urls.SELL;
                        message = "售出成功";
                        integral_display.setText("可售积分"+StringUtils.getStringtodouble(showIntegral));
                    }else if(currency.equals("2")){
                        String maxBuy = DoubleUtils.div(showIntegral,realTime,0).intValue()+"";
                        dialog_number.setText(maxBuy);
                        message = "购买成功";
                        integral_display.setText("回购积分"+StringUtils.getStringtodouble(showIntegral));
                        url = Urls.TRADEBUY;
                    }else {
                        String maxBuy = DoubleUtils.div(showIntegral,realTime,0).intValue()+"";
                        dialog_number.setText(maxBuy);
                        message = "购买成功";
                        integral_display.setVisibility(View.GONE);
                        url = Urls.TRADEBUY;
                    }
                }else {
                    Toast.makeText(context,mainEntity.message,Toast.LENGTH_SHORT).show();
                }

            }
        });
}
    /**
     * 购买和卖出的
     */
    private void tradeBuy(){
        OkHttpUtils.post().url(url).addHeader("Authorization",caches.get("access_token"))
                .addParams("price",dialog_price.getText().toString())
                .addParams("number",dialog_number.getText().toString())
                .addParams("pwd",pay_password.getText().toString())
                .addParams("currency",currency)
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
                if(e == null||e.getMessage() == null){
                    return;
                }
                if (e!=null&&e.getMessage()!=null&&e.getMessage().toString().contains("401")){
                    context.startActivity(new Intent(context, LoginActivity.class));
                }else {
                    Toast.makeText(context,"网络链接错误 ",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onResponse(String response, int id) {
                ApproveEntity approveEntity = new Gson().fromJson(response,ApproveEntity.class);
                if(approveEntity.code == 200){
                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                    //计算总金额（单价*数量）
                    Double totalPrice =  DoubleUtils.mul(Double.parseDouble(dialog_price.getText().toString()),Double.parseDouble(dialog_number.getText().toString()));
                    Message message =new Message();
                    message.what=SHUAXIN;
                    message.obj =totalPrice;
                    handler.handleMessage(message);
                }else {
                    Toast.makeText(context,approveEntity.message,Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });
    }
    /**
     * dialog 启动
     */
    public void startMyDialog() {
        if (context==null){
            return;
        }
        if (myloading == null) {
            myloading = MyLoading.createLoadingDialog(context);
        }
        if (!activity.isFinishing()) {
            try {
                myloading.show();
            }catch (IllegalArgumentException e){
                e.getStackTrace();
            }
        }
    }

    /**
     * dialog 销毁
     */
    public void stopMyDialog() {
        if (myloading != null) {
            if (!activity.isFinishing()){
                try {
                    myloading.dismiss();
                }catch (IllegalArgumentException e){
                    e.getStackTrace();
                }
            }
            myloading = null;
        }
    }
}
