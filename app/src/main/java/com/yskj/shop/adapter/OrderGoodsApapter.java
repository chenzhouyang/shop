package com.yskj.shop.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.yskj.shop.R;
import com.yskj.shop.entity.OrderListEntity;
import com.yskj.shop.utils.GlideImage;

import java.util.ArrayList;

/**
 * Created by YSKJ-02 on 2017/1/17.
 */

public class OrderGoodsApapter extends BaseAdapter {
    private Context context;
    private ArrayList<OrderListEntity.DataBean.OrdersListBean.ItemListBean> orderListBeen;
    public OrderGoodsApapter(Context context, ArrayList<OrderListEntity.DataBean.OrdersListBean.ItemListBean> orderListBeen){
        super();
        this.context = context;
        this.orderListBeen = orderListBeen;
    }
    @Override
    public int getCount() {
        if (orderListBeen!=null&&orderListBeen.size()!=0){
            return orderListBeen.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return orderListBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_googs_orchard_list, null);
            holder.goods_price = (TextView) convertView.findViewById(R.id.goods_price);
            holder.order_name = (TextView) convertView.findViewById(R.id.order_name);
            holder.order_number = (TextView) convertView.findViewById(R.id.order_number);
            holder.order_image = (ImageView) convertView.findViewById(R.id.order_image);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.order_name.setText(orderListBeen.get(position).name);
        holder.order_number.setText("X"+orderListBeen.get(position).num);
        String goodsPrice = "商品单价：<font color=\"#cc0000\">￥"+orderListBeen.get(position).price+"</font>";
        holder.goods_price.setText(Html.fromHtml(goodsPrice));
        GlideImage.loadImage(context,holder.order_image,orderListBeen.get(position).image,R.mipmap.img_error);
        return convertView;
    }
    public class ViewHolder{
        ImageView order_image;
        TextView order_name,order_number,goods_price;

    }
}
