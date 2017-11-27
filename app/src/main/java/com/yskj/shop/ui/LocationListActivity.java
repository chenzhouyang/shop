package com.yskj.shop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.adapter.AddressAdapter;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.LocationListEntity;
import com.yskj.shop.login.LoginActivity;
import com.yskj.shop.utils.LoadingCaches;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 作者：陈宙洋
 * 日期：2017/8/12.
 * 描述：收获地址列表
 */

public class LocationListActivity extends BaseActivity implements View.OnClickListener{
    private Button addBtn;
    private ListView address_lv;
    public static final int SHUAXIN = 1;
    public static final int CONFIRM = 2;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private LocationListEntity locationListEntity;
    private ArrayList<LocationListEntity.DataBean.AddressListBean> listBeen = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHUAXIN:
                    getLocationList();
                    break;
                case CONFIRM:
                    AppManager.getInstance().killActivity(LocationListActivity.class);
//                    startActivity(new Intent(context, ConfirmOrderActivity.class).putExtra("addresstype", "1")
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private AddressAdapter addressAdapter;
    private ImageView ImgBack;
    private TextView txtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_orchard_list);
        AppManager.getInstance().killActivity(LoginActivity.class);
        iniview();

    }
    @Override
    protected void onResume() {
        super.onResume();
        getLocationList();
    }
    private void iniview() {
        ImgBack = (ImageView) findViewById(R.id.img_back);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText("地址列表");
        addBtn = (Button) findViewById(R.id.add_btn);
        address_lv = (ListView) findViewById(R.id.address_lv);
        ImgBack.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        addressAdapter = new AddressAdapter(context,listBeen,handler);
        address_lv.setAdapter(addressAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_btn:
                startActivity(new Intent(context,PerfectActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra("addresstype","0"));
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }
    private void getLocationList(){
        OkHttpUtils.get().url(Urls.LOCATIONLIST).addParams("token",caches.get("php_token"))
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
                locationListEntity = new Gson().fromJson(response, LocationListEntity.class);
                if(locationListEntity.result == 1){
                    listBeen.clear();
                    listBeen.addAll(locationListEntity.data.addressList);
                    addressAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
