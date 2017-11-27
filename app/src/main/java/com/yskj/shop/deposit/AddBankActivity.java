package com.yskj.shop.deposit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.ApproveEntity;
import com.yskj.shop.entity.BankDetailEntity;
import com.yskj.shop.entity.RealNameEntity;
import com.yskj.shop.ui.ApproveActivity;
import com.yskj.shop.utils.LoadingCaches;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by YSKJ-02 on 2017/1/13.
 * 添加银行卡
 */

public class AddBankActivity extends BaseActivity implements View.OnClickListener{
    ImageView imgBack;
    TextView txtTitle;
    TextView addbankTruename;
    LinearLayout addbankDiglogLl;
    EditText addbankNumberEt,addbank_address,addbank_branch;
    TextView addbankNext;
    TextView addbankBank;
    private String type,reanlName,cardId;//type 0，添加银行卡，1，修改银行卡
    private LoadingCaches caches = LoadingCaches.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbank);
        type = getIntent().getStringExtra("type");
        if(type.equals("1")){
            cardId = getIntent().getStringExtra("cardid");
            getBankDetail();
        }
        iniview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取实名信息
        getRealNameDate();
    }

    private void iniview() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        addbankTruename = (TextView) findViewById(R.id.addbank_truename);
        addbankNumberEt = (EditText) findViewById(R.id.addbank_number_et);
        addbank_address = (EditText) findViewById(R.id.addbank_address);
        addbank_branch = (EditText) findViewById(R.id.addbank_branch);
        addbankNext = (TextView) findViewById(R.id.addbank_next);
        addbankDiglogLl = (LinearLayout) findViewById(R.id.addbank_diglog_ll);
        addbankNumberEt = (EditText) findViewById(R.id.addbank_number_et);
        addbankBank = (TextView) findViewById(R.id.addbank_bank);
        addbankDiglogLl.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        addbankNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                AppManager.getInstance().killActivity(AddBankActivity.class);
                break;
            case R.id.addbank_next:
                //确认
                if(addbankTruename.length() == 0){
                    showToast("请输入用户名");
                }else if(addbank_address.length() == 0){
                    showToast("请输入开卡地址");
                }else if(addbankNumberEt.length() == 0){
                    showToast("请输入银行卡号");
                }else if(addbank_branch.length()==0){
                    showToast("请输入开卡支行");
                }else if(addbankBank.getText().toString().equals("...")){
                    showToast("请选择要添加的银行卡");
                }else {
                    if(type.equals("0")){
                        presentBankDetail();
                    }else {
                        revampBankDetail();
                    }

                }

                break;
            case R.id.addbank_diglog_ll:
                //银行卡选择弹出框
                BankDialog dialog = new BankDialog(context);
                dialog.show();
                break;
        }
    }

    /**
     * 查询银行卡信息
     */
    public void getBankDetail(){
        OkHttpUtils.post().url(Urls.BANKLISTDE).addHeader("Authorization",caches.get("access_token"))
                .addParams("id",cardId)
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
                BankDetailEntity detailEntity = new Gson().fromJson(response,BankDetailEntity.class);
                if(detailEntity.code == 200){
                    addbankTruename.setText(detailEntity.data.cardName);
                    addbank_address.setText(detailEntity.data.address);
                    addbankNumberEt.setText(detailEntity.data.cardNo);
                    addbank_branch.setText(detailEntity.data.bankBranch);
                    addbankBank.setText(detailEntity.data.name);
                }else {
                    showToast(detailEntity.message);
                }
            }
        });
    }

    /**
     * 修改银行卡信息
     */
    public void revampBankDetail(){
        OkHttpUtils.post().url(Urls.UPDATEBANKLIST)
                .addHeader("Authorization",caches.get("access_token"))
                .addParams("name",addbankBank.getText().toString())
                .addParams("address",addbank_address.getText().toString())
                .addParams("cardNo",addbankNumberEt.getText().toString())
                .addParams("bankBranch",addbank_branch.getText().toString())
                .addParams("realNameId",reanlName)
                .addParams("id",cardId)
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
                    showToast("修改成功");
                    AppManager.getInstance().killActivity(AddBankActivity.class);
                }else {
                    showToast(approveEntity.message);
                }

            }
        });
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
                    addbankTruename.setText(realNameEntity.data.name);
                    reanlName = realNameEntity.data.id+"";
                }else if(realNameEntity.message.contains("实名认证")){
                    new AlertDialog.Builder(context).setTitle("系统提示")//设置对话框标题
                            .setMessage("您还未进行实名认证，请前往进行实名认证")//设置显示的内容
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    startActivity(new Intent(context, ApproveActivity.class));
                                }
                            }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                        public void onClick(DialogInterface dialog, int which) {//响应事件
                            AppManager.getInstance().killActivity(AddBankActivity.class);
                        }
                    }).show();//在按键响应事件中显示此对话框
                }else {
                    showToast(realNameEntity.message);
                }
            }
        });
}
    /**
     * 提交银行卡信息
     */
    public void presentBankDetail(){
        OkHttpUtils.post().url(Urls.BANKLIST).addHeader("Authorization",caches.get("access_token"))
                .addParams("name",addbankBank.getText().toString())
                .addParams("address",addbank_address.getText().toString())
                .addParams("cardNo",addbankNumberEt.getText().toString())
                .addParams("bankBranch",addbank_branch.getText().toString())
                .addParams("realNameId",reanlName)
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
                    showToast("添加成功");
                    AppManager.getInstance().killActivity(AddBankActivity.class);
                }else {
                    showToast(approveEntity.message);
                }

            }
        });

}
    /**
     * 银行卡选择弹出框
     */
    public class BankDialog extends Dialog {
        private Context context;
        private ListView dialog_lv;

        public BankDialog(Context context) {
            super(context, R.style.ShareDialog);
            this.context = context;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_bank);
            initView();
            setbank();
        }

        private void setbank() {
            //准备要添加的数据条目
            final List<Map<String, Object>> items = new ArrayList<>();
            Map<String, Object> item = new HashMap<>();
            item.put("bankimage", R.mipmap.china_pay_ico);//中国银行
            item.put("banktext", "中国银行");
            items.add(item);
            item = new HashMap<>();
            item.put("bankimage", R.mipmap.buess_pay_ico);//中国工商银行
            item.put("banktext", "工商银行");
            items.add(item);
            item = new HashMap<>();
            item.put("bankimage", R.mipmap.jian_pay_ico);//中国建设银行
            item.put("banktext", "建设银行");
            items.add(item);
            item = new HashMap<>();
            item.put("bankimage", R.mipmap.long_pay_ico);//中国农业银行
            item.put("banktext", "农业银行");
            items.add(item);
            item = new HashMap<>();
            item.put("bankimage", R.mipmap.zhao_pay_ico);//招商银行
            item.put("banktext", "招商银行");
            items.add(item);
            item = new HashMap<>();
            item.put("bankimage", R.mipmap.jiao_pay_ico);//交通银行
            item.put("banktext", "交通银行");
            items.add(item);
//            item = new HashMap<>();
//            item.put("bankimage", R.mipmap.zhifu_pay_ico);//支付宝
//            item.put("banktext", "支付宝");
//            items.add(item);
           /* item = new HashMap<String, Object>();
            item.put("bankimage", R.mipmap.wei_pay_ico);//微信
            item.put("banktext", "微信");
            items.add(item);*/
            //实例化一个适配器
            SimpleAdapter adapter = new SimpleAdapter(context,
                    items,
                    R.layout.item_dialog_bank,
                    new String[]{"bankimage", "banktext"},
                    new int[]{R.id.item_dialog_bank_image, R.id.item_dialog_bank_tv});
            dialog_lv.setAdapter(adapter);
            dialog_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dismiss();
                    addbankBank.setText(items.get(position).get("banktext")+"");
                }
            });
        }

        private void initView() {
            dialog_lv = (ListView) findViewById(R.id.dialog_lv);
        }

    }




}
