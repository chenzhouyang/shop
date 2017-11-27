package com.yskj.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.yskj.shop.R;
import com.yskj.shop.entity.GoodsEntity;
import com.yskj.shop.utils.GlideImage;
import com.yskj.shop.utils.StringUtils;

import java.util.List;

/**
 * 作者：陈宙洋
 * 日期：2017/8/11.
 * 描述：
 */

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.MyViewHolder> {
    private Context context;
    private List<GoodsEntity> list;
    private OnItemClickListener mOnItemClickListener;
    public GoodsAdapter(Context context, List<GoodsEntity> list){
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
        holder.goods_price.setText(StringUtils.getStringtodouble(list.get(position).getPrice()));
        GlideImage.loadImage(context,holder.img_goods,list.get(position).getImageurl(),R.mipmap.img_error);
        holder.goods_title.setText(list.get(position).getTitle());
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
        TextView goods_title, goods_price;
        public MyViewHolder(View view) {
            super(view);
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
