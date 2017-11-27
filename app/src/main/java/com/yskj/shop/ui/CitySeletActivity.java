package com.yskj.shop.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.yskj.shop.R;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.CityTestEntity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * 城市列表界面
 */
public class CitySeletActivity extends BaseActivity {
    private ListView city_lv;
    private ArrayList<CityTestEntity> list = new ArrayList<>();
    private String Province = "", City = "", Area = "";
    private int ProvinceId, CityId, AreaId;
    private ImageView imag_back;
    private TextView txt_title;
    private int regionid = 0;
    private CityTestEntity cityTestEntity;
    private int time = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orchard_city);
        imag_back = (ImageView) findViewById(R.id.img_back);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText("城市选择");
        imag_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        city_lv = (ListView) findViewById(R.id.city_lv);
        getCity();
    }
    private void getCity(){
        OkHttpUtils.get().url(Urls.CITYLIST).addParams("regionid",regionid+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
              list =   new Gson().fromJson(response, new TypeToken<List<CityTestEntity>>(){}.getType());
                CityAdapter adapter = new CityAdapter(list, CitySeletActivity.this);
                city_lv.setAdapter(adapter);
            }
        });
    }

    /**
     * 省适配器
     */
    public class CityAdapter extends BaseAdapter {
        private ArrayList<CityTestEntity> arrayList;
        private Context context;

        public CityAdapter(ArrayList<CityTestEntity> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @Override
        public int getCount() {
            if (arrayList != null || arrayList.size() != 0) {
                return arrayList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_orchard_city, null);
                holder.tv_city = (TextView) convertView.findViewById(R.id.tv_city);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_city.setText(arrayList.get(position).localName);
            holder.tv_city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(time == 1){
                        Province = arrayList.get(position).localName+"";
                        ProvinceId = arrayList.get(position).regionId;
                    }else if(time == 2){
                        City = arrayList.get(position).localName;
                        CityId = arrayList.get(position).regionId;
                    }else if(time == 3){
                        Area = arrayList.get(position).localName;
                        AreaId = arrayList.get(position).regionId;
                    }
                    regionid = arrayList.get(position).regionId;
                   time++;
                    Logger.d(list.size()+"");
                    if(time <= 3){
                        getCity();
                    } else{
                        Intent in = new Intent();
                        in.putExtra("Province", Province);
                        in.putExtra("City", City);
                        in.putExtra("Area", Area);
                        in.putExtra("ProvinceId", String.valueOf(ProvinceId));
                        in.putExtra("CityId", String.valueOf(CityId));
                        in.putExtra("AreaId", String.valueOf(AreaId));
                        setResult(8,in);
                        finish();
                    }
                }
            });
            return convertView;
        }
    }
    private class ViewHolder {
        private TextView tv_city;
    }
}
