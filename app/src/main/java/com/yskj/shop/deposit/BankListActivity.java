package com.yskj.shop.deposit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pulltorefresh.library.PullToRefreshBase;
import com.pulltorefresh.library.PullToRefreshScrollView;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.adapter.BankListAdapter;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.BankListEntity;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.MessgeUtil;
import com.yskj.shop.widget.SwipeListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by YSKJ-02 on 2017/1/15.
 * 银行卡列表
 */

public class BankListActivity extends BaseActivity implements View.OnClickListener{
    ImageView imgBack;
    TextView txtTitle;
    SwipeListView bankListPulllistview;
    ImageView addBank;
    PullToRefreshScrollView scrollView;
    private LoadingCaches caches = LoadingCaches.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banklist);
        iniveiw();
        txtTitle.setText("银行卡");
        scrollView.setMode(PullToRefreshBase.Mode.DISABLED);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取银行卡列表
        getbanklist();
    }

    private void iniveiw() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        bankListPulllistview = (SwipeListView) findViewById(R.id.bank_list_pulllistview);
        addBank = (ImageView) findViewById(R.id.add_bank);
        scrollView = (PullToRefreshScrollView) findViewById(R.id.scrollView);
        imgBack.setOnClickListener(this);
        addBank.setOnClickListener(this);
    }

    private void getbanklist() {
        OkHttpUtils.post().url(Urls.DELBANKLIST).addHeader("Authorization",caches.get("access_token"))
                .build().execute(new BankListCallBalk(context,bankListPulllistview));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                AppManager.getInstance().killActivity(BankListActivity.class);
                AppManager.getInstance().killActivity(AddBankActivity.class);
                break;
            case R.id.add_bank:
                startActivity(new Intent(context,AddBankActivity.class).putExtra("type","0"));
                break;
        }
    }


    private class BankListCallBalk extends Callback<BankListEntity> {
        private Context context;
        private SwipeListView bankListPulllistview;
        private ArrayList<BankListEntity.DataBean.ListBean> dataBeen;

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

        public BankListCallBalk(Context context, SwipeListView bankListPulllistview) {
            super();
            this.context = context;
            this.bankListPulllistview = bankListPulllistview;
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
                dataBeen = response.data.list;
                if (dataBeen.size() == 0) {
                    bankListPulllistview.setVisibility(View.GONE);
                } else {
                    bankListPulllistview.setVisibility(View.VISIBLE);
                    BankListAdapter adapter = new BankListAdapter(context, dataBeen,bankListPulllistview.getRightViewWidth());
                    bankListPulllistview.setAdapter(adapter);
                    adapter.setOnRightItemClickListener(new BankListAdapter.onRightItemClickListener() {
                        @Override
                        public void onRightItemClick(View v, int position) {
                            bankListPulllistview.hiddenRight(bankListPulllistview.getChildAt(position));

                        }
                    });
                }
            }else {
                showToast(response.message);
            }
        }
    }
}
