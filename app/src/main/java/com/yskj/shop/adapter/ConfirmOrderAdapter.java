package com.yskj.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.yskj.shop.R;
import com.yskj.shop.entity.SelectedEntity;
import com.yskj.shop.utils.GlideImage;
import com.yskj.shop.utils.NumberFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YSKJ-02 on 2017/2/13.
 */

public class ConfirmOrderAdapter extends BaseAdapter {
    private Context context;
    private List<SelectedEntity.DataBean.ItemsBean> arrayList;

    public ConfirmOrderAdapter(Context context, List<SelectedEntity.DataBean.ItemsBean> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        if (arrayList==null||arrayList.size()==0)
            return 0;
        return arrayList.size();
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
        if (convertView==null){
            holder=new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_confirm_orchard_order,null);
            holder.imgDes = (ImageView) convertView.findViewById(R.id.img_des);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_gid = (TextView) convertView.findViewById(R.id.tv_gid);
            holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            holder.shopname_type = (TextView) convertView.findViewById(R.id.shopname_type);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        SelectedEntity.DataBean.ItemsBean cartListBean = arrayList.get(position);
        holder.tv_name.setText(cartListBean.name);
        holder.tv_price.setText("￥"+ String.format("%.2f", NumberFormat.convertToDouble(cartListBean.price,0d)));
        holder.tv_gid.setText("规格:"+cartListBean.specs);
        holder.tv_count.setText("数量:X"+cartListBean.num+"");
        GlideImage.loadImage(context,holder.imgDes,cartListBean.imageDefault,R.mipmap.img_error);
        holder.shopname_type.setVisibility(View.GONE);
        return convertView;
    }

    private class ViewHolder{
        ImageView imgDes;
        TextView tv_name,tv_price,tv_gid,tv_count,shopname_type;

    }
}
