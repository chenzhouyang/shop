package com.yskj.shop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.LocationListEntity;
import com.yskj.shop.entity.ResultEntity;
import com.yskj.shop.utils.CommonUtils;
import com.yskj.shop.utils.LoadingCaches;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.yskj.shop.utils.StringUtils.stringFilter;


/**
 * 作者：陈宙洋
 * 日期：2017/8/12.
 * 描述：收获地址编辑
 */

public class PerfectActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout perfect_address_ll;
    private TextView perfect_address_tv,verify_tv;
    private String Province, citys, District,Provinceid, citysid, Districtid,type,url;
    private EditText perfect_consignee_name,perfect_phone,perfect_address_detial;
    private boolean isphoneNum = false;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private String addresstype;
    private LocationListEntity.DataBean.AddressListBean addressListBean;
    private String confirm;
    private ImageView ImgBack;
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orchard_perfect);
        iniview();
    }

    private void iniview() {
        ImgBack = (ImageView) findViewById(R.id.img_back);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtTitle.setText("地址列表");
        perfect_address_ll = (LinearLayout) findViewById(R.id.perfect_address_ll);
        perfect_address_tv = (TextView) findViewById(R.id.perfect_address_tv);
        verify_tv = (TextView) findViewById(R.id.verify_tv);
        perfect_address_detial = (EditText) findViewById(R.id.perfect_address_detial);
        perfect_consignee_name = (EditText) findViewById(R.id.perfect_consignee_name);
        perfect_phone = (EditText) findViewById(R.id.perfect_phone);
        ImgBack.setOnClickListener(this);
        // 收件人只允许字母、数字和汉字
        perfect_consignee_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = perfect_consignee_name.getText().toString();
                String str = stringFilter(editable.toString());
                if(!editable.equals(str)){
                    perfect_consignee_name.setText(str);
                    //设置新的光标所在位置
                    perfect_consignee_name.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //校验手机号
        perfect_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(CommonUtils.isPhoneNumer(perfect_phone.getText().toString())){
                    isphoneNum = true;
                }else {
                    isphoneNum = false;
                }

            }
        });
        addresstype = getIntent().getStringExtra("addresstype");
        if(addresstype.equals("1")){
            addressListBean = (LocationListEntity.DataBean.AddressListBean) getIntent().getSerializableExtra("address");
            perfect_consignee_name.setText(addressListBean.name);
            perfect_address_tv.setText(addressListBean.province+addressListBean.city+addressListBean.region);
            perfect_phone.setText(addressListBean.mobile);
            perfect_address_detial.setText(addressListBean.addr);
            Provinceid = addressListBean.provinceId+"";
            citysid = addressListBean.cityId+"";
            Districtid = addressListBean.regionId+"";
            Province = addressListBean.province;
            citys= addressListBean.city;
            District = addressListBean.region;
        }/*else {
            confirm = getIntent().getStringExtra("confirm");
        }*/
        perfect_address_ll.setOnClickListener(this);
        verify_tv.setOnClickListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d(requestCode+"requestcode"+requestCode+"requestCode");
        if (resultCode == 8) {
            if (requestCode == 1) {
                Province = data.getStringExtra("Province");
                citys = data.getStringExtra("City");
                District = data.getStringExtra("Area");
                perfect_address_tv.setText(Province + citys + District);
                Provinceid = data.getStringExtra("ProvinceId");
                citysid = data.getStringExtra("CityId");
                Districtid = data.getStringExtra("AreaId");
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.perfect_address_ll:

                Intent in = new Intent(context, CitySeletActivity.class);
                startActivityForResult(in, 1);
                break;
            case R.id.verify_tv:
                if(perfect_consignee_name.getText().toString().isEmpty()){
                    showToast("请输入收件人姓名");
                }else if(!isphoneNum||perfect_phone.getText().toString().isEmpty()){
                    showToast("请输入正确的手机号");
                }else if(perfect_address_tv.getText().toString().equals("选择所在地")){
                    showToast("请选择城市");
                }else if(perfect_address_detial.getText().toString().isEmpty()){
                    showToast("请输入详细地址");
                }else {
                    setAddress();
                }
                break;
            case R.id.img_back:
                finish();
                break;

        }
    }

    /**
     * 新增收货地址
     * @return
     */
private void setAddress(){
    if(addresstype.equals("0")){
        OkHttpUtils.post().url(Urls.ADDRESSCITYLIST)
                .addParams("token",caches.get("php_token"))
                .addParams("name",perfect_consignee_name.getText().toString())
                .addParams("mobile",perfect_phone.getText().toString())
                .addParams("tel","")
                .addParams("city",citys)
                .addParams("city_id",citysid)
                .addParams("def_addr","1")
                .addParams("province",Province)
                .addParams("province_id",Provinceid)
                .addParams("region",District)
                .addParams("region_id",Districtid)
                .addParams("addr",perfect_address_detial.getText().toString())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                ResultEntity resultEntity = new Gson().fromJson(response,ResultEntity.class);
                if(resultEntity.result == 1){
                    finish();
                }
            }
        });
    }else {
        OkHttpUtils.post().url(Urls.ALTER)
                .addParams("token",caches.get("php_token"))
                .addParams("name",perfect_consignee_name.getText().toString())
                .addParams("mobile",perfect_phone.getText().toString())
                .addParams("tel","")
                .addParams("city",citys)
                .addParams("city_id",citysid)
                .addParams("def_addr","1")
                .addParams("province",Province)
                .addParams("province_id",Provinceid)
                .addParams("region",District)
                .addParams("region_id",Districtid)
                .addParams("addr",perfect_address_detial.getText().toString())
                .addParams("addr_id",addressListBean.addrId+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                ResultEntity resultEntity = new Gson().fromJson(response,ResultEntity.class);
                if(resultEntity.result == 1){
                    AppManager.getInstance().killActivity(PerfectActivity.class);
                }
            }
        });
    }

}
}
