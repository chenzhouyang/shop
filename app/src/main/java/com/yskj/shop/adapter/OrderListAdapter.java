package com.yskj.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.yskj.shop.R;
import com.yskj.shop.dialog.OrderWebDialog;
import com.yskj.shop.entity.OrderListEntity;
import com.yskj.shop.ui.OrderDetailActivity;
import com.yskj.shop.utils.StringUtils;
import com.yskj.shop.widget.NoScrollListView;

import java.util.ArrayList;

/**
 * Created by YSKJ-02 on 2017/1/17.
 */

public class OrderListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<OrderListEntity.DataBean.OrdersListBean> list;
    private int num = 0;

    public OrderListAdapter(Context context, ArrayList<OrderListEntity.DataBean.OrdersListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list!=null&&list.size()!=0){
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView ==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order_orchard_list,null);
            holder.goods_list_lisetview = (NoScrollListView) convertView.findViewById(R.id.goods_list_lisetview);
            holder.order_list_ll = (LinearLayout) convertView.findViewById(R.id.order_list_ll);
            holder.order_point = (TextView) convertView.findViewById(R.id.order_point);
            holder.order_detail = (TextView) convertView.findViewById(R.id.order_detail);
            holder.order_stutse = (TextView) convertView.findViewById(R.id.order_stutse);
            holder.order_sn = (TextView)convertView.findViewById(R.id.order_sn);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (list.size()!=0){
            ArrayList<OrderListEntity.DataBean.OrdersListBean.ItemListBean> goodslist = list.get(position).itemList;
            holder.order_sn.setText("订单号"+list.get(position).sn);
            num = 0;
            for (int i = 0; i < goodslist.size(); i++) {
                num += goodslist.get(i).num;
            }
            holder.order_detail.setText("共"+num+"件商品  合计：¥"+ StringUtils.getStringtodouble(list.get(position).orderAmount)
                    +"含运费（¥"+list.get(position).shippingAmount+"）");
            if((int)(list.get(position).orderprice.point/100)<=0){
                holder.order_point.setVisibility(View.GONE);
            }else {
                holder.order_point.setText("赠送"+(int)(list.get(position).gainedpoint/100)+"积分");
            }
            if (list.get(position).payStatus == 0) {
                holder.order_stutse.setText("查看付款账号");
                holder.order_stutse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OrderWebDialog dialog = new OrderWebDialog(context);
                        dialog.show();
                    }
                });
            }else {
                holder.order_stutse.setText(list.get(position).shoppingStatus);
            }

            OrderGoodsApapter goodsadapter = new OrderGoodsApapter(context,goodslist);
            holder.goods_list_lisetview.setAdapter(goodsadapter);
            holder.goods_list_lisetview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    Intent intent = new Intent(context, OrderDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    OrderListEntity.DataBean.OrdersListBean orderListBean = list.get(position);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("orderbean",  orderListBean);
                    intent.putExtras(mBundle);
                    context.startActivity(intent);
                }
            });
            holder.order_list_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, OrderDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    OrderListEntity.DataBean.OrdersListBean orderListBean = list.get(position);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("orderbean",  orderListBean);
                    intent.putExtras(mBundle);
                    context.startActivity(intent);
                }
            });

        }else {
            Toast.makeText(context,"暂无数据",Toast.LENGTH_SHORT).show();
        }
        return convertView;
    }
    public class ViewHolder{
        NoScrollListView goods_list_lisetview;
        LinearLayout order_list_ll;
        TextView order_detail,order_point,order_stutse,order_sn;
    }
}