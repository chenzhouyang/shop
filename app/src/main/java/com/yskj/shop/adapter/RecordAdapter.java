package com.yskj.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.yskj.shop.R;
import com.yskj.shop.entity.RecordEntiity;
import com.yskj.shop.utils.StringUtils;

import java.util.ArrayList;

/**
 * Created by YSKJ-02 on 2017/1/20.
 */

public class RecordAdapter extends BaseAdapter {
private Context context;
    private ArrayList<RecordEntiity.DataBean.ListBean> listBeen;
    public RecordAdapter(Context context, ArrayList<RecordEntiity.DataBean.ListBean> listBeen){
        super();
        this.context = context;
        this.listBeen = listBeen;
    }
    @Override
    public int getCount() {
        if(listBeen!=null&&listBeen.size()!=0){
            return listBeen.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return listBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bank_deposit,null);
            holder.item_bank_name = (TextView) convertView.findViewById(R.id.item_bank_name);
            holder.item_year_month = (TextView) convertView.findViewById(R.id.item_year_month);
            holder.item_state = (TextView) convertView.findViewById(R.id.item_state);
            holder.item_hour = (TextView) convertView.findViewById(R.id.item_hour);
            holder.item_number = (TextView) convertView.findViewById(R.id.item_number);
            holder.item_money = (TextView) convertView.findViewById(R.id.item_money);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        RecordEntiity.DataBean.ListBean rcdalist = listBeen.get(position);
        String[] year = StringUtils.timeRecordToDate(rcdalist.createTime+"").split(" ");
        holder.item_year_month.setText(year[0]);
        holder.item_hour.setText(year[1]);
        if (rcdalist.status == 0){
            holder.item_state.setText("审核中...");
            holder.item_state.setTextColor(context.getResources().getColor(R.color.activity_red));
        }else if(rcdalist.status == 1){
            holder.item_state.setText("提现成功");
            holder.item_state.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else {
            holder.item_state.setText("提现失败");
            holder.item_state.setTextColor(context.getResources().getColor(R.color.activity_red));
        }
        holder.item_money.setText(StringUtils.getStringtodouble(rcdalist.money));
        return convertView;
    }
    public class ViewHolder{
        TextView item_year_month,item_bank_name,item_state,item_hour,item_number,item_money;
    }
}
