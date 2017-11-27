package com.yskj.shop.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yskj.shop.R;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.ApproveEntity;
import com.yskj.shop.login.LoginActivity;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.MobileEncryption;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

import static com.yskj.shop.ui.FansActivity.SHUAXIN;


/**
 * Created by YSKJ-02 on 2017/1/17.
 */

public class FansDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private ImageView dialog_close;
    private TextView dialogFromMobile,sureBtn;
    private EditText dialogToMobile;
    private MyLoading myloading;
    private Activity activity;
    private String fromMoble;
    private Handler handler;

    private LoadingCaches caches = LoadingCaches.getInstance();
    public FansDialog(Context context,String fromMoble,Handler handler) {
        super(context, R.style.ShareDialog);
        this.context = context;
        this.fromMoble = fromMoble;
        this.handler=handler;
        activity = (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_fans);
        initView();
    }

    private void initView() {
        dialog_close = (ImageView) findViewById(R.id.dialog_close);
        dialogFromMobile = (TextView) findViewById(R.id.dialog_frommobile);
        dialogToMobile = (EditText) findViewById(R.id.dialog_tomobile);
        sureBtn = (TextView) findViewById(R.id.sure_btn);
        dialogFromMobile.setText("您确定要分配您的粉丝\n"+fromMoble+"到");
        sureBtn.setOnClickListener(this);
        dialog_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_close:
                dismiss();
                break;
            case R.id.sure_btn:
                //确认
                if(dialogToMobile.length() == 0){
                    Toast.makeText(context,"请填写要分配到的手机号",Toast.LENGTH_SHORT).show();
                }else if(!MobileEncryption.isMobileNO(dialogToMobile.getText().toString())){
                    Toast.makeText(context,"请填写正确的手机号",Toast.LENGTH_SHORT).show();
                }else if(fromMoble.equals(dialogToMobile.getText().toString())){
                    Toast.makeText(context,"不能分配相同的手机号",Toast.LENGTH_SHORT).show();
                }else {
                    setDate();
                }
                break;
        }
    }

    private void setDate(){
        OkHttpUtils.post().url(Urls.ALLOTFANS).addHeader("Authorization", caches.get("access_token"))
                .addParams("fromMobile",fromMoble)
                .addParams("toMobile",dialogToMobile.getText().toString())
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
                    Message message =new Message();
                    message.what=SHUAXIN;
                    handler.handleMessage(message);
                    dismiss();
                    Toast.makeText(context,"分配成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,approveEntity.message,Toast.LENGTH_SHORT).show();
                }
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
