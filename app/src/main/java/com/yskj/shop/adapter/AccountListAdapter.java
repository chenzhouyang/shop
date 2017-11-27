package com.yskj.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.orhanobut.logger.Logger;
import com.yskj.shop.R;
import com.yskj.shop.entity.AccountListEntity;
import com.yskj.shop.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YSKJ-02 on 2017/1/20.
 */

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.MyViewHolder> {
    private Context context;
    private List<AccountListEntity.DataBean.ListBean> list;
    private OnItemClickListener mOnItemClickListener;
    public AccountListAdapter(Context context, List<AccountListEntity.DataBean.ListBean> list){
            super();
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_accounts,null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AccountListEntity.DataBean.ListBean recordBean = list.get(position);
        Logger.d(recordBean.toMobile);
        holder.item_mobile.setText(recordBean.toMobile);
        holder.item_money.setText("-"+StringUtils.getStringtodouble(recordBean.money));
        holder.item_time.setText(StringUtils.timeRecordToDateYear(recordBean.createTime));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0)
            return 0;
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_mobile,item_money,item_time;
        public MyViewHolder(View view) {
            super(view);
            item_mobile = (TextView) view.findViewById(R.id.item_mobile);
            item_money = (TextView) view.findViewById(R.id.item_money);
            item_time = (TextView) view.findViewById(R.id.item_time);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}
