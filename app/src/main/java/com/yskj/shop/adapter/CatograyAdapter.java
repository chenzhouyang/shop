package com.yskj.shop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.yskj.shop.R;
import com.yskj.shop.entity.CatograyBean;
import com.yskj.shop.entity.GoodsCategoryListEntity;

import java.util.List;

/**
 * Created by chenzhouyang on 2016/5/24 0024.
 */

public class CatograyAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsCategoryListEntity.DataBean> list;
    private int selectedPosition = 0;

    public CatograyAdapter(Context context, List<GoodsCategoryListEntity.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    //这句是把listview的点击position,传递过来
    public void clearSelection(int position) {
        selectedPosition = position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final Viewholder viewholder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.shopcart_left_orchard_listview, null);
            viewholder = new Viewholder();
            viewholder.tv_catogray = (TextView) view.findViewById(R.id.tv_catogray);
            view.setTag(viewholder);
        } else {
            viewholder = (Viewholder) view.getTag();
        }
        viewholder.tv_catogray.setText(list.get(position).text);
        if(selectedPosition == position){
            viewholder.tv_catogray.setTextColor(Color.parseColor("#ff6501"));
            viewholder.tv_catogray.setBackgroundResource(R.color.white);
        }else {
            viewholder.tv_catogray.setTextColor(Color.parseColor("#404040"));
            viewholder.tv_catogray.setBackgroundResource(R.color.province_line_border);
        }
        return view;
    }
    class Viewholder {
        TextView tv_catogray;
    }

}
