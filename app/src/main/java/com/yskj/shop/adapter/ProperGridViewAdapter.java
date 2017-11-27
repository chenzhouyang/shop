package com.yskj.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.yskj.shop.R;
import com.yskj.shop.entity.CommodityEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YSKJ-02 on 2017/4/14.
 */

public class ProperGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<CommodityEntity.DataBean.SpecListBean.ValueListBean> gridviewlist;
    private int clickTemp = -1;
    //标识选择的Item
    public void setSeclection(int position) {
        clickTemp = position;
    }
    public String setdate(int position){
        if(gridviewlist!=null){
            return gridviewlist.get(position).specValueId;
        }return null;

    }
    public String setdatename(int position){
        if(gridviewlist!=null){
            return gridviewlist.get(position).specValueId;
        }return null;

    }
    public ProperGridViewAdapter(Context context, List<CommodityEntity.DataBean.SpecListBean.ValueListBean> gridviewlist){
        this.context = context;
        this.gridviewlist = gridviewlist;
    }
    @Override
    public int getCount() {
        return gridviewlist.size();
    }

    @Override
    public Object getItem(int position) {
        return gridviewlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_orchard_propergridview,null);
            holder.item_property_gridview = (TextView) convertView.findViewById(R.id.item_property_gridview);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_property_gridview.setText(gridviewlist.get(position).specValue);
        // 点击改变选中listItem
        if (clickTemp == position) {
            holder.item_property_gridview.setTextColor(context.getResources().getColor(R.color.white));
            holder.item_property_gridview.setBackgroundResource(R.drawable.bg_corner_red);
        } else {
            holder.item_property_gridview.setTextColor(context.getResources().getColor(R.color.grey));
            holder.item_property_gridview.setBackgroundResource(R.drawable.bg_corner_d2d2d2);
        }
        return convertView;
    }
    public class ViewHolder{
        TextView item_property_gridview;
    }
}
