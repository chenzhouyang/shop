package com.yskj.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.entity.PutAwayEntity;
import com.yskj.shop.login.LoginActivity;
import com.yskj.shop.utils.GlideImage;
import com.yskj.shop.utils.StringUtils;

import java.util.List;


public class HeadGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<PutAwayEntity.DataBean> arrayList;

    public HeadGridViewAdapter(Context context, List<PutAwayEntity.DataBean> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        if (arrayList == null || arrayList.size() == 0)
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_orchard_goods,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        PutAwayEntity.DataBean putdate = arrayList.get(position);
        holder.goods_price.setText("¥"+ StringUtils.getStringtodouble(putdate.price));
        GlideImage.loadImage(context,holder.img_goods,putdate.thumbnail, R.mipmap.img_error);
        holder.goods_title.setText(putdate.name);
        if((int)(putdate.point/100)<=0){
            holder.goods_integral.setVisibility(View.GONE);
        }else {
            holder.goods_integral.setVisibility(View.VISIBLE);
            holder.goods_integral.setText("赠送"+(int)(putdate.point/100)+"积分");
        }
        holder.goods_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance().killActivity(LoginActivity.class);
//                context.startActivity(new Intent(context, CommodityDetailsActivity.class)
//                        .putExtra("id",arrayList.get(position).goodsId+""));
            }
        });

        return convertView;
    }
    class ViewHolder {
        ImageView img_goods;
        TextView goods_title, goods_price,goods_integral;
        LinearLayout goods_item;
        public ViewHolder(View view) {
            goods_integral = (TextView) view.findViewById(R.id.goods_integral);
            img_goods = (ImageView) view.findViewById(R.id.img_goods);
            goods_title = (TextView) view.findViewById(R.id.goods_title);
            goods_price = (TextView) view.findViewById(R.id.goods_price);
            goods_item = (LinearLayout) view.findViewById(R.id.goods_item);
        }
    }
}
