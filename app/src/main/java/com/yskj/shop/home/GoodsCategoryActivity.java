package com.yskj.shop.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.adapter.CatograyAdapter;
import com.yskj.shop.adapter.CatograyReclasspyAdapter;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.GoodsCategoryListEntity;
import com.yskj.shop.login.LoginActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by YSKJ-JH on 2017/1/12.
 * 商品分类
 */

public class GoodsCategoryActivity extends BaseActivity implements View.OnClickListener{
    private GoodsCategoryListEntity registerEntity;
    private EditText edKeyword;
    private ListView lv_catogary,lv_good;
    private CatograyAdapter catograyAdapter;
    private List<GoodsCategoryListEntity.DataBean> goodscatelist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        AppManager.getInstance().addActivity(this);
        setContentView(R.layout.activity_goods_orchard_category);
        initView();
        initData();
    }


    private void initView() {
        AppManager.getInstance().killActivity(LoginActivity.class);
        edKeyword = (EditText) findViewById(R.id.ed_keyword);
        lv_catogary = (ListView) findViewById(R.id.lv_catogary);
        lv_good = (ListView) findViewById(R.id.lv_good);
        edKeyword.setOnClickListener(this);
        catograyAdapter = new CatograyAdapter(context,goodscatelist);
        lv_catogary.setAdapter(catograyAdapter);
        lv_catogary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                catograyAdapter.clearSelection(position);
                catograyAdapter.notifyDataSetChanged();
                CatograyReclasspyAdapter catograyReclasspyAdapter = new CatograyReclasspyAdapter(context,goodscatelist.get(position).tmenu);
                lv_good.setAdapter(catograyReclasspyAdapter);
            }
        });
    }

    //初始化数据
    private void initData() {
        OkHttpUtils.get().url(Urls.GOODSCATEGORYLIST).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                isLogin(e);
            }

            @Override
            public void onResponse(String response, int id) {
                registerEntity = new Gson().fromJson(response, new TypeToken<GoodsCategoryListEntity>(){}.getType());
                if(registerEntity.result == 1){
                    goodscatelist.clear();
                    goodscatelist.addAll(registerEntity.data);
                    catograyAdapter.notifyDataSetChanged();
                    //加载二级分类
                    if(goodscatelist!=null&&goodscatelist.size()>0){
                        CatograyReclasspyAdapter catograyReclasspyAdapter = new CatograyReclasspyAdapter(context,goodscatelist.get(0).tmenu);
                        lv_good.setAdapter(catograyReclasspyAdapter);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ed_keyword:
                goToSearch();
                break;
        }
    }

    //跳转到搜索页面
    private void goToSearch() {
        Intent intent = new Intent(GoodsCategoryActivity.this, SearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("type","GoodsCategoty");
        startActivity(intent);
    }

}
