package com.yskj.shop.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.adapter.SearchAdapter;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.SearchEntity;
import com.yskj.shop.login.LoginActivity;
import com.yskj.shop.ui.CommodityDetailsActivity;
import com.yskj.shop.widget.DividerGridItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 作者：陈宙洋
 * 日期：2017/8/14.
 * 描述：搜索界面
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener{
    private ImageView imageBack;
    private EditText edKeyWord;
    private TextView tvRightText;
    private XRecyclerView searchListview;
    private int start = 0;
    private SearchEntity searchentity;
    private List<SearchEntity.DataBean> searchlistpage = new ArrayList<>();
    private List<SearchEntity.DataBean> searchlistAll = new ArrayList<>();
    private SearchAdapter searchAdapter;
    private GridLayoutManager layoutManager;
    private String type, cat = null,name;
    private boolean isSearch = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orchard_search);
        iniview();
        initListen();
    }
    //初始组件
    private void iniview() {
        imageBack = (ImageView) findViewById(R.id.img_back);
        edKeyWord = (EditText) findViewById(R.id.ed_keyword);
        tvRightText = (TextView) findViewById(R.id.tv_right_text);
        searchListview = (XRecyclerView) findViewById(R.id.search_listview);
        tvRightText.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        type = getIntent().getStringExtra("type");
        if(type.equals("adapter")){
            isSearch = false;
            cat = getIntent().getStringExtra("cat");
            name = getIntent().getStringExtra("name");
            edKeyWord.setText(name);
            searchlistAll.clear();
            getSearchData();
        }
        searchAdapter = new SearchAdapter(context,searchlistAll);
        layoutManager = new GridLayoutManager(context, 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchListview.setLayoutManager(layoutManager);
        searchListview.setHasFixedSize(true);
        searchListview.addItemDecoration(new DividerGridItemDecoration(context, 15, R.drawable.divider));
        searchListview.setAdapter(searchAdapter);
        searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(context, CommodityDetailsActivity.class).putExtra("id",searchlistAll.get(position).goodsId+""));
            }
        });

    }
    private void initListen() {
        searchListview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        searchListview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                start = 0;
                getSearchData();
                searchListview.refreshComplete();
            }
            @Override
            public void onLoadMore() {
                start++;
                getSearchData();
                searchListview.refreshComplete();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                //返回
                finish();
                AppManager.getInstance().killActivity(LoginActivity.class);
                break;
            case R.id.tv_right_text:
                //点击搜索触发事件
                if(edKeyWord.length()!=0){
                    start = 0;
                    isSearch = true;
                    searchlistAll.clear();
                    searchlistpage.clear();
                    InputMethodManager imm = (InputMethodManager)
                     getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    getSearchData();
                }else {
                    showToast("请输入要查找的商品名称");
                }
                break;
        }
    }

    private void getSearchData() {
        if (cat != null&& !isSearch) {
            OkHttpUtils.get().url(Urls.SEARCH)
                    .addParams("cat", cat)
                    .addParams("start", start*10 + "")
                    .addParams("length", "10")
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
                    searchentity = new Gson().fromJson(response, SearchEntity.class);
                    if (searchentity.result == 1) {
                        searchlistpage.clear();
                        if (searchentity.data.size() > 0) {
                            searchlistpage.addAll(searchentity.data);
                        } else {
                            searchListview.setNoMore(true);
                            showToast("暂无数据");
                        }
                        if(start == 0){
                            searchlistAll.clear();
                        }
                        searchlistAll.addAll(searchlistpage);
                        searchAdapter.notifyDataSetChanged();
                    }

                }
            });
        } else{
            OkHttpUtils.get().url(Urls.SEARCH)
                    .addParams("keyword", edKeyWord.getText().toString())
                    .addParams("start", start*10 + "")
                    .addParams("length", "10")
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    isLogin(e);
                }

                @Override
                public void onResponse(String response, int id) {
                    searchentity = new Gson().fromJson(response, SearchEntity.class);
                    if (searchentity.result == 1) {
                        searchlistpage.clear();
                        if (searchentity.data.size() > 0) {
                            searchlistpage.addAll(searchentity.data);
                        } else {
                            showToast("暂无数据");
                        }
                        if(start == 0){
                            searchlistAll.clear();
                        }
                        searchlistAll.addAll(searchlistpage);
                        searchAdapter.notifyDataSetChanged();
                    }

                }
            });
        }
    }
}
