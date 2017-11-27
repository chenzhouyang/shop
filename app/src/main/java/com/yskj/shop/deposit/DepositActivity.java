package com.yskj.shop.deposit;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pulltorefresh.library.PullToRefreshBase;
import com.pulltorefresh.library.PullToRefreshScrollView;
import com.yskj.shop.R;
import com.yskj.shop.adapter.RecordAdapter;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.BankListEntity;
import com.yskj.shop.entity.RecordEntiity;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.MessgeUtil;
import com.yskj.shop.widget.NoScrollListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by YSKJ-02 on 2017/1/13.
 * 提现界面
 */

public class DepositActivity extends BaseActivity implements View.OnClickListener{
    ImageView imgBack;
    TextView txtTitle;
    NoScrollListView depositLv;
    PullToRefreshScrollView depositPullScroll;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private int count = 10, cursor = 0;
    private ArrayList<RecordEntiity.DataBean.ListBean> dataBeenAll = new ArrayList<>();
    private ArrayList<RecordEntiity.DataBean.ListBean> dataPage = new ArrayList<>();
    private RecordAdapter recordAdapte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        iniview();
        txtTitle.setText("提现记录");
        //提现列表、
        getbanlist();
        iniscrollview();
    }

    private void iniview() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        depositLv = (NoScrollListView) findViewById(R.id.deposit_lv);
        depositPullScroll = (PullToRefreshScrollView) findViewById(R.id.deposit_pull_scroll);
    }

    //刷新提现列表
    private void iniscrollview() {
        recordAdapte = new RecordAdapter(context,dataBeenAll);
        depositLv.setAdapter(recordAdapte);
        depositPullScroll.setMode(PullToRefreshBase.Mode.BOTH);
        depositPullScroll.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                cursor = 0;
                getbanlist();
                depositPullScroll.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                cursor++;
                getbanlist();
                depositPullScroll.onRefreshComplete();
            }
        });
    }

    private void getbanlist() {
        OkHttpUtils.post().url(Urls.RECORDLIST)
                .addHeader("Authorization", caches.get("access_token"))
                .addParams("page", count + "").addParams("size", cursor * 10 + "").build().
                execute(new RecordCallBack(context, depositLv));
    }

    public class RecordCallBack extends Callback<RecordEntiity> {
        private Context context;
        private NoScrollListView depositLv;
        private ArrayList<RecordEntiity.DataBean.ListBean> listBeen;

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

        public RecordCallBack(Context context, NoScrollListView depositLv){
            super();
            this.context = context;
            this.depositLv = depositLv;
        }
        @Override
        public RecordEntiity parseNetworkResponse(Response response, int id) throws Exception {
            String s = response.body().string();
            RecordEntiity recordEntiity = new Gson().fromJson(s,new TypeToken<RecordEntiity>(){}.getType());
            return recordEntiity;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            isLogin(e);

        }

        @Override
        public void onResponse(RecordEntiity response, int id) {
            if(response.code == 200){
                //每次清空上页数据
                dataPage.clear();
                dataPage = response.data.list;
                if(dataPage.size() != 0){
                    if (cursor==0){
                        dataBeenAll.clear();
                    }
                    dataBeenAll.addAll(dataPage);
                    recordAdapte.notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "没有数据",Toast.LENGTH_SHORT).show();
                }
            }else {
                showToast(response.message);
            }

        }
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
