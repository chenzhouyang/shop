package com.yskj.shop.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.orhanobut.logger.Logger;
import com.pulltorefresh.library.PullToRefreshBase;
import com.pulltorefresh.library.PullToRefreshScrollView;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.adapter.AccountListAdapter;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.AccountListEntity;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.widget.DividerGridItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 作者：  陈宙洋
 * 描述： 转账记录
 * 日期： 2017/11/23.
 */

public class AccountListActivity extends BaseActivity implements View.OnClickListener{
    private PullToRefreshScrollView accounts_pull_scrollview;
    private XRecyclerView   accounts_pull_lv;
    private ImageView imaBack;
    private TextView txtTitle;
    private AccountListAdapter accountListAdapter;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private int page = 1;
    private int size = 10;
    private List<AccountListEntity.DataBean.ListBean> listAll = new ArrayList<>();
    private List<AccountListEntity.DataBean.ListBean> listBeans = new ArrayList<>();
    private GridLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountlist);
        iniview();
        setDate();
    }

    private void iniview() {
        accounts_pull_scrollview = (PullToRefreshScrollView) findViewById(R.id.accounts_pull_scrollview);
        accounts_pull_lv = (XRecyclerView) findViewById(R.id.accounts_pull_lv);
        imaBack = (ImageView) findViewById(R.id.img_back);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText("转账记录");
        accountListAdapter = new AccountListAdapter(context, listAll);
        layoutManager = new GridLayoutManager(context, 1);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        accounts_pull_lv.setLayoutManager(layoutManager);
        accounts_pull_lv.setHasFixedSize(true);
        accounts_pull_lv.setPullRefreshEnabled(false);
        accounts_pull_lv.addItemDecoration(new DividerGridItemDecoration(context, 15, R.drawable.divider));
        accounts_pull_lv.setAdapter(accountListAdapter);
        accounts_pull_scrollview.setMode(PullToRefreshBase.Mode.BOTH);
        accounts_pull_scrollview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page=1;
                setDate();
                accounts_pull_scrollview.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page++;
                setDate();
                accounts_pull_scrollview.onRefreshComplete();
            }
        });
        imaBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                AppManager.getInstance().killActivity(AccountListActivity.class);
                break;
        }
    }

    /**
     * 获取列表数据
     */
    private void setDate(){
        OkHttpUtils.post().url(Urls.ACCOUNTLIST)
                .addHeader("Authorization", caches.get("access_token"))
                .addParams("page",page+"")
                .addParams("size",size+"")
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
                AccountListEntity accountListEntity = new Gson().fromJson(response,AccountListEntity.class);
                if(accountListEntity.code == 200){
                    listBeans.clear();
                    listBeans.addAll(accountListEntity.data.list);
                    if(listBeans.size() != 0){
                        if(page == 1){
                            listAll.clear();
                        }
                        listAll.addAll(listBeans);
                        accountListAdapter.notifyDataSetChanged();
                    }else {
                        showToast("暂时没有转账记录");
                    }
                }else {
                    showToast(accountListEntity.message);
                }
            }
        });
    }
}
