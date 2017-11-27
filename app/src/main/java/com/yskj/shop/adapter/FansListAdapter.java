package com.yskj.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yskj.shop.R;
import com.yskj.shop.dialog.FansDialog;
import com.yskj.shop.dialog.MyLoading;
import com.yskj.shop.dialog.OrderWebDialog;
import com.yskj.shop.entity.FansEntity;
import com.yskj.shop.entity.OrderListEntity;
import com.yskj.shop.ui.OrderDetailActivity;
import com.yskj.shop.utils.LevelUtil;
import com.yskj.shop.utils.StringUtils;
import com.yskj.shop.widget.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YSKJ-02 on 2017/1/17.
 */

public class FansListAdapter extends BaseAdapter {
    private Context context;
    private List<FansEntity.DataBean.ListBean> list;
    private String  tier = "";

    private MyLoading myloading;
    private Handler handler;
    public FansListAdapter(Context context, List<FansEntity.DataBean.ListBean> list,String tier,Handler handler) {
        this.context = context;
        this.list = list;
        this.tier = tier;
        this.handler=handler;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fans,null);
            holder.itemFansMobile = (TextView) convertView.findViewById(R.id.item_fans_mobile);
            holder.itemFansLevel = (TextView) convertView.findViewById(R.id.item_fans_level);
            holder.itemFansBtn = (TextView) convertView.findViewById(R.id.item_fans_btn);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (list.size()!=0){
            if(tier.equals("1")){
                holder.itemFansBtn.setVisibility(View.VISIBLE);
            }else {
                holder.itemFansBtn.setVisibility(View.GONE);
            }
            holder.itemFansMobile.setText(list.get(position).mobile);
            holder.itemFansLevel.setText(LevelUtil.geterr_code(list.get(position).level));
            holder.itemFansBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FansDialog dialog = new FansDialog(context,list.get(position).mobile,handler);
                    dialog.show();
                }
            });
        }else {
            Toast.makeText(context,"暂无数据",Toast.LENGTH_SHORT).show();
        }
        return convertView;
    }
    public class ViewHolder{
        TextView itemFansMobile,itemFansLevel,itemFansBtn;
    }
    /**
     * dialog 启动
     */
    public void startMyDialog() {
        if (myloading == null) {
            myloading = MyLoading.createLoadingDialog(context);
        }
        myloading.show();
    }

    /**
     * dialog 销毁
     */
    public void stopMyDialog() {
        if (myloading != null) {
            myloading.dismiss();
            myloading = null;
        }
    }
}