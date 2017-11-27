package com.yskj.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.yskj.shop.R;
import com.yskj.shop.entity.SearchEntity;
import com.yskj.shop.utils.GlideImage;
import com.yskj.shop.utils.StringUtils;

import java.util.List;

/**
 * 作者：陈宙洋
 * 日期：2017/8/11.
 * 描述：商品adapter
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private Context context;
    private List<SearchEntity.DataBean> list;
    private OnItemClickListener mOnItemClickListener;
    public SearchAdapter(Context context, List<SearchEntity.DataBean> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_orchard_goods,null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        SearchEntity.DataBean putdate = list.get(position);
        holder.goods_price.setText(StringUtils.getStringtodouble(putdate.price));
        GlideImage.loadImage(context,holder.img_goods,putdate.thumbnail,R.mipmap.img_error);
        if((int)(putdate.point/100)<=0){
            holder.goods_integral.setVisibility(View.GONE);
        }else {
            holder.goods_integral.setVisibility(View.VISIBLE);
            holder.goods_integral.setText("赠送"+(int)(putdate.point/100)+"积分");
        }
        holder.goods_title.setText(putdate.name);
        if( mOnItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_goods;
        TextView goods_title, goods_price,goods_integral;
        public MyViewHolder(View view) {
            super(view);
            goods_integral= (TextView) view.findViewById(R.id.goods_integral);
            img_goods = (ImageView) view.findViewById(R.id.img_goods);
            goods_title = (TextView) view.findViewById(R.id.goods_title);
            goods_price = (TextView) view.findViewById(R.id.goods_price);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}
