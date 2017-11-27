package com.yskj.shop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pulltorefresh.library.PullToRefreshBase;
import com.pulltorefresh.library.PullToRefreshListView;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.adapter.FansListAdapter;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Config;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.FansEntity;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.MethodUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 作者：  陈宙洋
 * 描述： 粉丝列表（一级粉丝有分配功能）
 * 日期： 2017/11/10.
 */

public class FansActivity extends BaseActivity implements View.OnClickListener{
    private ImageView imageBack;
    private TextView txtile;
    private RadioGroup group;
    private RadioButton fansOne,fansTwo,fansThree;
    private PullToRefreshListView fansListLv;
    private FansEntity fansEntity;
    private String tier = "1";//粉丝等级
    public static final int SHUAXIN = 2017;
    private int page = 1,size = 10;
    private List<FansEntity.DataBean.ListBean> listAll = new ArrayList<>();
    private List<FansEntity.DataBean.ListBean> listPage = new ArrayList<>();
    private FansListAdapter fansListAdapter;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHUAXIN:
                    iniDate();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans);
        iniview();
        findview();
        iniDate();
    }

    /**
     * 网络请求粉丝
     */
    private void iniDate() {
        OkHttpUtils.post().url(Urls.FANS)
                .addHeader(Config.HEADER, caches.get("access_token"))
                .addParams("tier",tier)
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
                fansEntity = new Gson().fromJson(response,FansEntity.class);
                if(fansEntity.code == 200){
                    listPage.clear();
                    listPage.addAll(fansEntity.data.list);
                    if(page == 1){
                        listAll.clear();
                    }
                    listAll.addAll(listPage);
                    fansListAdapter = new FansListAdapter(context,listAll,tier,handler);
                    fansListLv.setAdapter(fansListAdapter);
                }else {
                    showToast(fansEntity.message);
                }
            }
        });
    }

    /**
     * radiobutton 点击事件
     */
    private void findview() {
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){

                    case R.id.fans_one:
                        //一级粉丝
                        tier = "1";
                        page = 1;
                        iniDate();
                        break;
                    case R.id.fans_two:
                        //二级粉丝
                        tier = "2";
                        page = 1;
                        iniDate();
                        break;
                    case R.id.fans_three:
                        //三级粉丝
                        tier = "3";
                        page = 1;
                        iniDate();
                        break;

                }
            }
        });
    }

    /**
     * 初始化组件
     */
    private void iniview() {
        imageBack = (ImageView) findViewById(R.id.img_back);
        txtile = (TextView) findViewById(R.id.txt_title);
        group = (RadioGroup) findViewById(R.id.group);
        fansOne = (RadioButton) findViewById(R.id.fans_one);
        fansTwo = (RadioButton) findViewById(R.id.fans_two);
        fansThree = (RadioButton) findViewById(R.id.fans_three);
        fansListLv = (PullToRefreshListView) findViewById(R.id.fans_list_lv);
        txtile.setText("我的粉丝");
        imageBack.setOnClickListener(this);
        fansListLv.setMode(PullToRefreshBase.Mode.BOTH);
        fansListLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                iniDate();
                MethodUtils.stopRefresh(fansListLv);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                iniDate();
                MethodUtils.stopRefresh(fansListLv);
            }
        });
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                AppManager.getInstance().killActivity(FansActivity.class);
                break;
        }
    }
}
