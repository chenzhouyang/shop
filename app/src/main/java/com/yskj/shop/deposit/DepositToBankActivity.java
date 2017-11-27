package com.yskj.shop.deposit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Config;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.ApproveEntity;
import com.yskj.shop.entity.BankListEntity;
import com.yskj.shop.entity.RecordEntiity;
import com.yskj.shop.entity.UserInfo;
import com.yskj.shop.utils.JSONFormat;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.MessgeUtil;
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
 * 提现到银行卡
 */

public class DepositToBankActivity extends BaseActivity implements View.OnClickListener{
    ImageView imgBack,depositImage;
    private LinearLayout depositLl;
    TextView txtTitle,selectBank,depositCardname,depositCardnumber;
    EditText moneyEv;
    EditText passwordEv;
    TextView deposittobankTrue;
    private RecordEntiity recordlbean;
    private String cardid = null;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private TextView image_right,money_tv;
    private LinearLayout bank_select;
    private String availableIntegral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposittobank);
        iniveiw();
        txtTitle.setText("提现");
        moneyEv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        moneyEv.setText(s);
                        moneyEv.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    moneyEv.setText(s);
                    moneyEv.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        moneyEv.setText(s.subSequence(0, 1));
                        moneyEv.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void iniveiw() {
        availableIntegral = getIntent().getStringExtra("availableIntegral");
        imgBack = (ImageView) findViewById(R.id.img_back);
        image_right = (TextView) findViewById(R.id.image_right);
        image_right.setVisibility(View.VISIBLE);
        image_right.setText("提现记录");
        depositImage = (ImageView) findViewById(R.id.deposit_image);
        depositLl = (LinearLayout) findViewById(R.id.deposit_ll);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        selectBank = (TextView) findViewById(R.id.select_bank);
        depositCardname = (TextView) findViewById(R.id.deposit_cardname);
        depositCardnumber = (TextView) findViewById(R.id.deposit_cardnumber);
        moneyEv = (EditText) findViewById(R.id.money_ev);
        passwordEv = (EditText) findViewById(R.id.password_ev);
        money_tv = (TextView) findViewById(R.id.money_tv);
        deposittobankTrue = (TextView) findViewById(R.id.deposittobank_true);
        bank_select = (LinearLayout) findViewById(R.id.bank_select);
        bank_select.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        deposittobankTrue.setOnClickListener(this);
        image_right.setOnClickListener(this);
        depositLl.setOnClickListener(this);
        money_tv.setText(availableIntegral);
    }

    private void setbanktocard() {
        OkHttpUtils.post().url(Urls.REEORD)
                .addHeader("Authorization", caches.get("access_token"))
                .addParams("bankId",cardid).addParams("money",moneyEv.getText().toString())
                .addParams("pwd",passwordEv.getText().toString())
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
                    showToast("提现成功");
                    startActivity(new Intent(context,DepositActivity.class));
                }else {
                    showToast(approveEntity.message);
                }
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                AppManager.getInstance().killActivity(DepositToBankActivity.class);
                break;
            case R.id.deposittobank_true:
                if (moneyEv.length() == 0) {
                    showToast("请输入提现金额");
                } else if (Double.parseDouble(moneyEv.getText().toString()) <= 0) {
                    showToast("输入提现金额必须大于0");
                } else if (passwordEv.length() == 0) {
                    showToast("请输入支付密码");
                } else if (cardid == null) {
                    showToast("请选择银行卡");
                } else {
                    setbanktocard();
                }

                break;
            case R.id.bank_select:
                BankListDialog dialog = new BankListDialog(context);
                dialog.show();
                break;
            case R.id.image_right:
                startActivity(new Intent(context,DepositActivity.class));
                break;
        }
    }
    public class BankListDialog extends Dialog {
        private ListView dialogBankLv;
        public BankListDialog( Context context) {
            super(context, R.style.ShareDialog);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_banklist);
            dialogBankLv = (ListView) findViewById(R.id.dialog_bank_lv);
            getbanklist();
        }

        /**
         * 获取银行卡列表
         */
        private void getbanklist() {
            OkHttpUtils.post().url(Urls.DELBANKLIST).addHeader("Authorization",caches.get("access_token"))
                    .build().execute(new BankListCallBalk());

        }
        private class BankListCallBalk extends Callback<BankListEntity> {
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
            public BankListEntity parseNetworkResponse(Response response, int id) throws Exception {
                String s = response.body().string();
                BankListEntity bankListEntity = new Gson().fromJson(s, new TypeToken<BankListEntity>() {
                }.getType());
                return bankListEntity;
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                isLogin(e);
            }

            @Override
            public void onResponse(BankListEntity response, int id) {
                if(response.code == 200){
                    if(response.data.list.size()>0){
                        BankDepositListAdapter adapter = new BankDepositListAdapter(context,response.data.list);
                        dialogBankLv.setAdapter(adapter);
                    }else {
                        new AlertDialog.Builder(context).setTitle("系统提示")//设置对话框标题
                                .setMessage("您还未添加银行卡，请先添加银行卡")//设置显示的内容
                                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {//添加确定按钮
                                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                        dismiss();
                                    }
                                }).show();//在按键响应事件中显示此对话框
                    }
                }else {
                    showToast(response.message);
                }
            }
        }
        public class BankDepositListAdapter extends BaseAdapter {
            private Context context;
            private ArrayList<BankListEntity.DataBean.ListBean> list;
            private  BankListEntity.DataBean  databean;
            public BankDepositListAdapter(Context context, ArrayList<BankListEntity.DataBean.ListBean> list){
                super();
                this.context = context;
                this.list = list;
            }
            @Override
            public int getCount() {
                if(list!= null&&list.size()!=0){
                    return list.size();
                }
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                ViewBankHolder holder = null;
                if(convertView == null){
                    holder = new ViewBankHolder();
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_bank,null);
                    holder.item_bank_image = (ImageView) convertView.findViewById(R.id.item_bank_image);
                    holder.item_bank_name = (TextView) convertView.findViewById(R.id.item_bank_name);
                    holder.item_bank_number = (TextView) convertView.findViewById(R.id.item_bank_number);
                    holder.ll_layout = (LinearLayout) convertView.findViewById(R.id.ll_layout);
                    convertView.setTag(holder);
                }else {
                    holder = (ViewBankHolder) convertView.getTag();
                }
                holder.item_bank_name.setText(list.get(position).cardName);
                holder.item_bank_number.setText(list.get(position).cardNo);
                StringUtils.bank(list.get(position).name,holder.item_bank_image);
                holder.ll_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                         * 银行卡信息显示，提示语隐藏
                         */
                        depositImage.setVisibility(View.VISIBLE);
                        depositLl.setVisibility(View.VISIBLE);
                        selectBank.setVisibility(View.GONE);
                        Logger.d(list.get(position).cardName);
                        StringUtils.bank(list.get(position).name,depositImage);
                        depositCardname.setText(list.get(position).cardName);
                        depositCardnumber.setText(list.get(position).cardNo+"");
                        cardid = list.get(position).id+"";
                        dismiss();
                    }
                });
                return convertView;
            }
            class ViewBankHolder{
                ImageView item_bank_image;
                TextView item_bank_name,item_bank_number;
                LinearLayout ll_layout;

            }
        }
    }

}
