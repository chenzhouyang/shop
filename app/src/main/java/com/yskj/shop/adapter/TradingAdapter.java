package com.yskj.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yskj.shop.R;
import com.yskj.shop.entity.LineEntity;
import com.yskj.shop.entity.RecordEntiity;
import com.yskj.shop.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YSKJ-02 on 2017/1/20.
 */

public class TradingAdapter extends BaseAdapter {
private Context context;
    private List<LineEntity.DataBean.ListBean> listBeen;
    public TradingAdapter(Context context, List<LineEntity.DataBean.ListBean> listBeen){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_traing,null);
            holder.item_stause = (TextView) convertView.findViewById(R.id.item_stause);
            holder.item_time = (TextView) convertView.findViewById(R.id.item_time);
            holder.item_number = (TextView) convertView.findViewById(R.id.item_number);
            holder.item_money = (TextView) convertView.findViewById(R.id.item_money);
            holder.item_state = (TextView) convertView.findViewById(R.id.item_state);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(listBeen.get(position).type==1){
            holder.item_stause.setText("买入");
        }else {
            holder.item_stause.setText("卖出");
        }
        holder.item_time.setText(StringUtils.timeRecordToDate(listBeen.get(position).createTime+""));
        holder.item_number.setText(listBeen.get(position).num+"个x"+StringUtils.getStringtodouble(listBeen.get(position).price));
        holder.item_money.setText(StringUtils.getStringtodouble(listBeen.get(position).money));
        return convertView;
    }
    public class ViewHolder{
        TextView item_stause,item_time,item_number,item_money,item_state;
    }
}
